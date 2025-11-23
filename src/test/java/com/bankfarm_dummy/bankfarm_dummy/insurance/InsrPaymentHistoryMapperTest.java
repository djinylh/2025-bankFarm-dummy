package com.bankfarm_dummy.bankfarm_dummy.insurance;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrContractRes;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrPaymentHistoryReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class InsrPaymentHistoryMapperTest extends Dummy {
    @Test
    void insertInsrPaymentHistory() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InsuranceMapper insrMapper = sqlSession.getMapper(InsuranceMapper.class);

        // 1. 보험계약 전체 들고오기
        List<InsrContractRes> contracts = insrMapper.selectAllContracts();

        LocalDate today = LocalDate.now();
        Random rnd = new Random();

        for (InsrContractRes con : contracts) {

            Long contractId = con.getInsrContractId();
            LocalDate contractDate = con.getInsrContractDt().toLocalDate();
            LocalDate endDate = con.getInsrPaymentEndDt();
            int paymentDay = con.getInsrPaymentDay();

            // 월 보험료 random
            long monthlyPremium = 30000 + rnd.nextInt(270000);

            // 시퀀스 시작
            int seq = 1;

            // 첫 납입 예정일
            LocalDate firstPayDate = contractDate.withDayOfMonth(paymentDay);

            // 계약일보다 예정일이 이전이면 다음달로 넘김
            if (firstPayDate.isBefore(contractDate)) {
                firstPayDate = firstPayDate.plusMonths(1);
            }

            LocalDate payDate = firstPayDate;

            while (!payDate.isAfter(endDate)) {

                // 예정 금액
                long expectedAmt = monthlyPremium;

                // 기본 값
                LocalDate paidAt = null;
                Long paidAmt = null;
                String paidYn = "N";
                String odYn = "N";

                if (payDate.isBefore(today)) {

                    paidYn = "Y";

                    // 80%는 정상납부, 20%는 연체
                    boolean delayed = rnd.nextInt(10) < 2;
                    if (delayed) {

                        int delayDays = 1 + rnd.nextInt(5);
                        paidAt = payDate.plusDays(delayDays);
                        odYn = "Y";

                    } else {

                        paidAt = payDate;
                        odYn = "N";

                    }

                    paidAmt = expectedAmt;

                }

                // INSERT DTO 채우기
                InsrPaymentHistoryReq req = new InsrPaymentHistoryReq();
                req.setInsrPaymentSeq(seq);
                req.setInsrContractId(contractId);
                req.setInsrPaymentDt(payDate);
                req.setInsrExpectedAmt(expectedAmt);
                req.setInsrPaidDt(paidAt);
                req.setInsrPaidAmt(paidAmt);
                req.setInsrPaidYn(paidYn);
                req.setInsrOdYn(odYn);

                insrMapper.insrPaymentHistoryInsert(req);

                // 회차 증가
                seq++;
                payDate = payDate.plusMonths(1);
            }

            sqlSession.flushStatements();
        }

        sqlSession.commit();
        sqlSession.close();

    }
}
