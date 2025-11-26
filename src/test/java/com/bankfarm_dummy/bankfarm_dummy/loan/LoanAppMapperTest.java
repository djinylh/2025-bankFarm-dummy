package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanAppInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanProdGetAmtRes;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class LoanAppMapperTest extends Dummy {

    @Test
    void loanAppInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        LoanAppMapper loanAppMapper = sqlSession.getMapper(LoanAppMapper.class);
        LoanProdMapper loanProdMapper = sqlSession.getMapper(LoanProdMapper.class);


        for(int i = 0 ; i <100000 ; i++){

        // 고객 아이디 랜덤
        long custId = (int)(Math.random()*100000)+1;
        // 대출 상품 아이디 랜덤
        long loanProdId = (int)(Math.random()*100000)+1;
        // 직원 아이디 랜덤
        long empId = (int)(Math.random()*51023)+1;
        // 대출 금액 가져오기
        LoanProdGetAmtRes prodRes = loanProdMapper.loanProdGetAmt(loanProdId);
        // 거부 사유 랜덤
        String[] rjct = {  "신용점수 부족",
                "연체 기록 다수",
                "소득 불충분",
                "고정수입 없음",
                "채무 과다",
                "보증인 미제공",
                "재직 기간 짧음",
                "담보 미제공",
                "부채 상환능력 부족",
                "기타 내부 기준 미충족"};
        int rjctIdx = (int)(Math.random()*rjct.length);

        // 랜덤 년월일

            LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0, 0);
            LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

            // 초 단위로 변환
            long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
            long endEpoch = end.toEpochSecond(ZoneOffset.UTC);

            // 랜덤 초 생성
            long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);

            // LocalDateTime으로 변환
            LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC);


        LoanAppInsertReq req = new LoanAppInsertReq();
        req.setCustId(custId);
        req.setLoanProdId(loanProdId);
        req.setEmpId(empId);
        req.setLoanReqAmt(prodRes.getLoanMaxAmt());
        req.setLoanReqTrm(prodRes.getLoanTermMo());
        req.setLoanAppStsCd("AP002");
        req.setLoanDcsnDt(randomDateTime);
        req.setLoanAppDt(randomDateTime);
        req.setLoanRjctRsn(null);

        loanAppMapper.loanAppInsert(req);
        sqlSession.flushStatements();
        }
        sqlSession.close();



    }


}