package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanGetAppPkRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanMapperTest extends Dummy {

    @Test
    void loanMapperTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        LoanMapper loanMapper = sqlSession.getMapper(LoanMapper.class);
        LoanAppMapper loanAppMapper = sqlSession.getMapper(LoanAppMapper.class);

        List<LoanGetAppPkRes> loanGetAppPkRes = loanAppMapper.loanAppByCd002();

        for(LoanGetAppPkRes res : loanGetAppPkRes){
            System.out.println(res.getLoanAppId());
        }

        LoanInsertReq req = new LoanInsertReq();





    }


}