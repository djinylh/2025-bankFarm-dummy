package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanRepaymentByTx007;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanOverDueInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanOverdueMapperTest extends Dummy {

    final int BATCH_SIZE = 1000;
    int count = 0;


    @Test
    void loanOverdueMapperTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        LoanOverdueMapper overMapper = sqlSession.getMapper(LoanOverdueMapper.class);
        LoanRepaymentMapper repaymentMapper = sqlSession.getMapper(LoanRepaymentMapper.class);

        List<GetLoanRepaymentByTx007> byTx007 = repaymentMapper.getByTx007();

        for(GetLoanRepaymentByTx007 oneTx :  byTx007){
            BigDecimal rtPct;

            if(oneTx.getRtPct() == null){
                rtPct = BigDecimal.valueOf(1.2);
            }else {
                rtPct = oneTx.getRtPct();
            }

            LoanOverDueInsertReq odReq = new LoanOverDueInsertReq();
            odReq.setLoanRpmtId(oneTx.getLoanRpmtId());
            odReq.setLoanId(oneTx.getLoanId());
            odReq.setLoanOdDueSts("TX004");
            odReq.setLoanOdAmt(oneTx.getLoanRpmtDue());
            odReq.setLoanOdStDt(oneTx.getLoanDueDt());
            // 날짜 하루 추가
            LocalDate today = oneTx.getLoanDueDt().plusDays(1);
            odReq.setLoanOdEdDt(today);
            // 총 이자 원래 이자 + 가산금리 이자
            BigDecimal totalIntrst = oneTx.getLoanRpmtIntrst().add(rtPct);
            odReq.setLoanOdIntrst(totalIntrst.setScale(1, BigDecimal.ROUND_UNNECESSARY));
            int intIntrst = totalIntrst.multiply(BigDecimal.TEN).intValue();
            // 총 금액
            long amt = (oneTx.getLoanRpmtDue()* intIntrst) / 10 ;
            long totalAmt = ((amt + 5) / 10) * 10;
            odReq.setLoanOdFnAmt(totalAmt);
            odReq.setLoanOdMonth(0);
            overMapper.loanOverdueInsert(odReq);

            count++;

            // 배치 크기에 도달하면 강제 flush + commit + 초기화
            if (count % BATCH_SIZE == 0) {
                sqlSession.flushStatements();
                sqlSession.commit();
            }


        }
        sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.close();

    }
}