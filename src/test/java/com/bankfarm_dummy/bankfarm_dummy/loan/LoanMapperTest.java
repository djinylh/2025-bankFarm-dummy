package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanGetAppPkRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


class LoanMapperTest extends Dummy {

    @Test
    void loanMapperTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        LoanMapper loanMapper = sqlSession.getMapper(LoanMapper.class);
        LoanAppMapper loanAppMapper = sqlSession.getMapper(LoanAppMapper.class);

        List<LoanGetAppPkRes> loanGetAppPkRes = loanAppMapper.loanAppByCd002();

        //랜덤 은행 코드
        String[] bk ={"BK001","BK002","BK003","BK004","BK005",
                "BK006","BK007","BK008","BK009","BK010","BK011",
                "BK012","BK013","BK014","BK015","BK016","BK017",
                "BK018","BK019","BK020"
        };
        int bkIdx = (int)(Math.random()*bk.length);



        for(LoanGetAppPkRes res : loanGetAppPkRes){
            // 만기일
            LocalDate date =    res.getLoanDcsnDt().plusMonths(res.getLoanReqTrm());


            LoanInsertReq req = new LoanInsertReq();
            req.setLoanAppId(res.getLoanAppId());
            req.setLoanNum(accountNum());
            req.setLoanBnkCd(bk[bkIdx]);
            req.setLoanFnIntrst(BigDecimal.valueOf(1.2));
            req.setLoanEdDt(date);
            req.setLoanFnRpmtTp("LN001");
            req.setLoanUseAcct(accountNum2());
            req.setLoanUseBnkCd(bk[bkIdx]);
            req.setLoanDueSts("TX003");

            loanMapper.loanInsert(req);
            sqlSession.flushStatements();
        }
        sqlSession.close();



    }
    private String accountNum() {
        Random randomAcct = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(randomAcct.nextInt(10));
            if (i == 2 || i == 5) {
                sb.append("-");
            }
        }

        return sb.toString();


    }

    private String accountNum2() {
        Random randomAcct = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(randomAcct.nextInt(10));
            if (i == 3 || i == 5) {
                sb.append("-");
            }
        }

        return sb.toString();


    }


}