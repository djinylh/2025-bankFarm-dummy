package com.bankfarm_dummy.bankfarm_dummy.insurance;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrContractRes;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrTermReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class InsrTermMapperTest extends Dummy {
    @Test
    void insertExpiredContracts() {

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InsuranceMapper insrMapper = sqlSession.getMapper(InsuranceMapper.class);

        // CS002, CS003인 계약 가져오기
        List<InsrContractRes> expired = insrMapper.selectExpiredContracts();

        Random rnd = new Random();

        for (InsrContractRes c : expired) {

            String activeCd = c.getInsrActiveCd();  // CS002/CS003
            LocalDate maturity = c.getInsrMaturityDt();
            LocalDate contractDt = c.getInsrContractDt().toLocalDate();

            String termTp;
            String reason = null;
            LocalDateTime termDt;

            if (activeCd.equals("CS002")) {

                termTp = "IN001";
                reason = null;

                // 만기일을 기준으로 약간 랜덤 시간 붙여서 생성
                termDt = maturity.atTime(9 + rnd.nextInt(8), rnd.nextInt(60));

            } else { // CS003 중도해지

                termTp = "IN002";

                // 사유 랜덤 생성
                String[] reasons = {
                        "보험료 부담 증가로 인한 해지",
                        "다른 보험사 상품으로 갈아탐",
                        "혜택 대비 보험료가 비싸다고 판단",
                        "개인 사정으로 인한 중도 해지 요청",
                        "보장 범위가 기대에 미치지 못해 해지"
                };
                reason = reasons[rnd.nextInt(reasons.length)];

                // 계약일 ~ 만기일 사이 랜덤한 날짜에 해지한 것으로 처리
                int daysBetween = contractDt.until(maturity).getDays();
                LocalDate randomCloseDate = contractDt.plusDays(rnd.nextInt(Math.max(1, daysBetween)));
                termDt = randomCloseDate.atTime(10 + rnd.nextInt(7), rnd.nextInt(60));
            }

            InsrTermReq req = new InsrTermReq();
            req.setInsrContractId(c.getInsrContractId());
            req.setInsrTermTp(termTp);
            req.setInsrTermDt(termDt);
            req.setInsrTermReason(reason);

            insrMapper.insrTermInsert(req);
            sqlSession.flushStatements();
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
