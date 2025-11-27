package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanInfoRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanRepaymentInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanRepaymentMapperTest extends Dummy {

    final int BATCH_SIZE = 1000;
    int count = 0;

    @Test
    void loanRepaymentMapperTest(){

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        LoanRepaymentMapper mapper = sqlSession.getMapper(LoanRepaymentMapper.class);

        for(int i=30001;i<=40000;i++){
        GetLoanInfoRes loanInfoRes = mapper.GetLoanInfoByLoanId(i);
        for(int k= 1; k<= loanInfoRes.getLoanReqTrm();k++){
            // 상환일
            LocalDate date =  loanInfoRes.getLoanDcsnDt().plusMonths(k);

            // 원금 전체 금액/ 상환일
            long amt = loanInfoRes.getLoanReqAmt() / loanInfoRes.getLoanReqTrm();
            // 이자 포함 상환금
            BigDecimal amtBD = new BigDecimal("1");
            BigDecimal result = amtBD.multiply(BigDecimal.valueOf(amt));
            BigDecimal due = result.multiply(loanInfoRes.getLoanFnIntrst());
            // 소수점 제외
            long orderDue = (int)due.longValue();

            long totalDue = Long.valueOf(orderDue);

            LoanRepaymentInsertReq req = new LoanRepaymentInsertReq();
            req.setLoanId(i);
            req.setLoanRpmtPrncp(amt);
            req.setLoanRpmtIntrst(loanInfoRes.getLoanFnIntrst());
            req.setLoanRpmtDue(totalDue);
            req.setLoanDueDt(date);
            req.setLoanDueSts("TX003");

            mapper.loanRepaymentInsert(req);

            count++;

            // 배치 크기에 도달하면 강제 flush + commit + 초기화
            if (count % BATCH_SIZE == 0) {
                sqlSession.flushStatements();
                sqlSession.commit();
            }

        }

        }

        sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.close();




    }

}