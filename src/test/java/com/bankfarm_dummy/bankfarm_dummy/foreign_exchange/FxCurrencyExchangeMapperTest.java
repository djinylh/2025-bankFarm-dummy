package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractMapper;
import com.bankfarm_dummy.bankfarm_dummy.deposit.DepoContractMapperTest;
import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxCurrencyExchangeReq;
import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxRtHistoryRes;
import lombok.Builder;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class FxCurrencyExchangeMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 100_000;
    final int CHUNK_SIZE    = 1_000;
    @Test
    void insertCurrencyExchange() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        try {
            FxMapper fxMapper = sqlSession.getMapper(FxMapper.class);

            ThreadLocalRandom r = ThreadLocalRandom.current();

            // 1. FK 실제 ID들 전부 미리 조회
            List<Long> empIds  = fxMapper.selectEmployeeIds();
            List<FxAcctCustRes> acctCustList = fxMapper.selectAcctCustList();

            // 2. 환율 기록 전부 조회 후 통화별로 그룹핑 + 정렬
            List<FxRtHistoryRes> rtList = fxMapper.selectAllFxRateHistory();

            HashMap<String, List<FxRtHistoryRes>> rtMap = new HashMap<>();

            for (FxRtHistoryRes row : rtList) {
                rtMap
                        .computeIfAbsent(row.getFxCurrencyId(), k -> new ArrayList<>())
                        .add(row);
            }

            // 통화별로 fx_crt_at 오름차순 정렬
            for (List<FxRtHistoryRes> list : rtMap.values()) {
                list.sort(Comparator.comparing(FxRtHistoryRes::getFxCrtAt));
            }

            // 환전 대상 통화 배열
            String[] currencies = {"USD","JPY","CNY","EUR","GBP","AUD","CAD","HKD","SGD","CHF","THB","TWD","PHP"};

            String[] purposeCodes = {"FX006", "FX007", "FX008", "FX009", "FX010", "FX011", "FX012", "FX013"};

            // 10월 24일 90:00:00 ~ 12월 5일 23:59:59 사이에서만 신청일 생성
            LocalDateTime globalStart = LocalDateTime.of(2025, 10, 24, 9, 0, 0);
            LocalDateTime globalEnd   = LocalDateTime.of(2025, 12, 5, 23, 59, 59);
            Long totalSeconds = java.time.Duration.between(globalStart, globalEnd).getSeconds();

            for (int i = 0; i < ADD_ROW_COUNT; i++) {

                if (i > 0 && i % CHUNK_SIZE == 0) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }

                // FK 뽑기
                Long empId  = empIds.get(r.nextInt(empIds.size()));
                Long custId = custIds.get(r.nextInt(custIds.size()));

                // 3. 통화 / 신청일 랜덤
                String currency = currencies[r.nextInt(currencies.length)];
                List<FxRtHistoryRes> listByCur = rtMap.get(currency);
                if (listByCur == null || listByCur.isEmpty()) {
                    // 해당 통화 환율 기록이 없다면 그냥 스킵하거나 다른 통화로
                    continue;
                }

                // 전역 범위 안에서 신청일 랜덤
                long addSec = (long) (r.nextDouble() * totalSeconds);
                LocalDateTime fxReqDt = globalStart.plusSeconds(addSec);

                // 4. fx_req_dt 기준으로 "그 시점 최신 환율" 찾기
                FxRtHistoryRes appliedRt = null;
                // 리스트가 정렬되어 있으니까 뒤에서부터 첫 번째 <= req_dt 찾기
                for (int idx = listByCur.size() - 1; idx >= 0; idx--) {
                    FxRtHistoryRes row = listByCur.get(idx);
                    if (!row.getFxCrtAt().isAfter(fxReqDt)) {
                        appliedRt = row;
                        break;
                    }
                }
                // 혹시 전부 크면 그냥 가장 오래된 걸로
                if (appliedRt == null) {
                    appliedRt = listByCur.get(0);
                }

                Long fxRtId = appliedRt.getFxRtId();
                BigDecimal baseRate = appliedRt.getFxChargeRt();
                BigDecimal commission = appliedRt.getFxCommission();

                // 실제 적용 환율 = 매매기준율 * (1 + 수수료)
                BigDecimal customerRate = baseRate.multiply(
                        BigDecimal.ONE.add(commission)
                ).setScale(4, RoundingMode.HALF_UP);

                // 5. 환전 타입/금액/계좌 결정
                int type = r.nextInt(3); // 0: 계좌→외화, 1: 외화→계좌, 2: 현금→외화

                Long fromAcctId = null;
                Long toAcctId   = null;
                BigDecimal fromAmt;
                BigDecimal toAmt;
                String fxTrnsTp;

                if (type == 0) {
                    fxTrnsTp = "FX001"; // 계좌 -> 외화

                    FxAcctCustRes pair = acctCustList.get(r.nextInt(acctCustList.size()));
                    custId     = pair.getCustId();
                    fromAcctId = pair.getAcctId();

                    long raw = 100_000 + (long) (r.nextDouble() * (3_000_000 - 100_000)); // 원화
                    fromAmt = BigDecimal.valueOf(raw);
                    toAmt   = fromAmt.divide(customerRate, 4, RoundingMode.HALF_UP);

                } else if (type == 1) {
                    fxTrnsTp = "FX002"; // 외화 -> 계좌

                    FxAcctCustRes pair = acctCustList.get(r.nextInt(acctCustList.size()));
                    custId   = pair.getCustId();
                    toAcctId = pair.getAcctId();

                    long raw = 100 + (long) (r.nextDouble() * (3_000 - 100)); // 외화
                    fromAmt = BigDecimal.valueOf(raw);
                    toAmt   = fromAmt.multiply(customerRate)
                            .setScale(4, RoundingMode.HALF_UP);

                } else {
                    fxTrnsTp = "EX005"; // 현금 -> 외화

                    // 계좌 없는 타입이지만 고객은 실제 존재해야 함
                    FxAcctCustRes pair = acctCustList.get(r.nextInt(acctCustList.size()));
                    custId = pair.getCustId();

                    long raw = 100_000 + (long) (r.nextDouble() * (2_000_000 - 100_000)); // 원화
                    fromAmt = BigDecimal.valueOf(raw);
                    toAmt   = fromAmt.divide(customerRate, 4, RoundingMode.HALF_UP);
                }

                String purpose = purposeCodes[r.nextInt(purposeCodes.length)];

                // 처리일은 대충 신청일 + 0~2일 랜덤
                long plusDays = (long) (r.nextDouble() * 3);
                LocalDateTime fxTrnsDt = fxReqDt.plusDays(plusDays);

                String fxTrnsCd = "ST001";

                FxCurrencyExchangeReq req = FxCurrencyExchangeReq.builder()
                        .fxRtId(fxRtId)
                        .empId(empId)
                        .custId(custId)
                        .fxFromAcctId(fromAcctId)
                        .fxToAcctId(toAcctId)
                        .fxFromAmt(fromAmt)
                        .fxToAmt(toAmt)
                        .fxTrnsTp(fxTrnsTp)
                        .fxExchangePurpose(purpose)
                        .fxReqDt(fxReqDt)
                        .fxTrnsDt(fxTrnsDt)
                        .fxTrnsCd(fxTrnsCd)
                        .build();

                fxMapper.insertCurrencyExchange(req);
            }

            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
    }
}
