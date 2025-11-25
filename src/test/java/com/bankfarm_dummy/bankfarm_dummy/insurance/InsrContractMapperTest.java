package com.bankfarm_dummy.bankfarm_dummy.insurance;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrContractReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class InsrContractMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 500;

    @Test
    void insertInsrProd() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InsuranceMapper insrMapper = sqlSession.getMapper(InsuranceMapper.class);

        // 보험 상품 ID 리스트
        List<Long> prodIds = insrMapper.selectInsrProdIds();

        // 직원 ID 리스트
        List<Long> empIds = insrMapper.selectEmployeeIds();

        // 고객 ID 리스트
        List<Long> custIds = insrMapper.selectCustomerIds();

        Random rnd = new Random();

        // 은행 코드 (BK001 비중 높게)
        String[] bankCodes = {
                "BK001", "BK001", "BK001",  // 비중 강화
                "BK002", "BK003", "BK004",
                "BK005", "BK006", "BK007",
                null // 직접 납부용
        };




        for (int i = 0; i < ADD_ROW_COUNT; i++) {

            Long prodId = prodIds.get(rnd.nextInt(prodIds.size()));
            Long empId = empIds.get(rnd.nextInt(empIds.size()));
            Long custId =  custIds.get(rnd.nextInt(custIds.size()));

            // 계약번호
            String contractNum = "IC" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", rnd.nextInt(1_000_000));

            // 계약일 (최근 1~5년 사이)
            LocalDateTime contractDt = LocalDateTime.now()
                    .minusDays(rnd.nextInt(365 * 5));

            // 만기일: 5~40년 뒤 or null
            LocalDate maturityDt = null;
            if (rnd.nextInt(10) < 8) { // 80%는 만기일 존재
                maturityDt = contractDt.toLocalDate().plusYears(5 + rnd.nextInt(36));
            }

            // 납입 종료일 (5~30년 뒤)
            LocalDate paymentEndDt = contractDt.toLocalDate().plusYears(5 + rnd.nextInt(26));

            // 승인상태
            String approvalCd = rnd.nextInt(100) < 90 ? "AP002" : "AP003";

            // 계약상태
            String activeCd;
            if (maturityDt == null) {
                activeCd = "CS001";  // 만기일 없음 → 정상 취급
            } else if (maturityDt.isBefore(LocalDate.now())) {
                activeCd = "CS002";  // 만기 지난 계약
            } else {
                int random = rnd.nextInt(100);
                if (random < 85) activeCd = "CS001";  // 대부분 정상
                else activeCd = "CS003";             // 일부 중도해지
            }

            // 갱신 가능 여부 Y/N
            String renewableYn = rnd.nextBoolean() ? "Y" : "N";

            // 환급금 (계약 시 확정)
            long refundAmt = (long) (500000 + rnd.nextInt(4500000));

            // 은행 코드
            String bankCd = bankCodes[rnd.nextInt(bankCodes.length)];

            // 계좌번호 만들기
            String acctNum = null;
            if (bankCd != null) {
                acctNum =
                        (100 + rnd.nextInt(900)) + "-" +
                                (1000 + rnd.nextInt(9000)) + "-" +
                                (100000 + rnd.nextInt(900000));
            }

            // 납입일(1~28)
            byte paymentDay = (byte) (1 + rnd.nextInt(28));


            InsrContractReq req = new InsrContractReq();

            req.setInsrProdId(prodId);
            req.setCustId(custId);
            req.setEmpId(empId);
            req.setInsrContractNum(contractNum);
            req.setInsrBankCd(bankCd);
            req.setInsrAcctNum(acctNum);
            req.setInsrContractDt(contractDt);
            req.setInsrMaturityDt(maturityDt);
            req.setInsrPaymentEndDt(paymentEndDt);
            req.setInsrApprovalCd(approvalCd);
            req.setInsrActiveCd(activeCd);
            req.setInsrRenewableYn(renewableYn);
            req.setInsrRefundAmt(refundAmt);
            req.setInsrPaymentDay(paymentDay);

            insrMapper.insrContractInsert(req);

            if (i > 0 && i % 200 == 0) {
                sqlSession.flushStatements();
            }
        }

        sqlSession.commit();
        sqlSession.close();

    }
}
