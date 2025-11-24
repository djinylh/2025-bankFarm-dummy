package com.bankfarm_dummy.bankfarm_dummy.transaction;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.transaction.model.GetFirstAcctBalInsertRes;
import com.bankfarm_dummy.bankfarm_dummy.transaction.model.TransactionInsertGeq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirstTransactionMapperTest extends Dummy {

    @Test
    void transactionInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        TransactionMapper transactionMapper = sqlSession.getMapper(TransactionMapper.class);
        List<GetFirstAcctBalInsertRes> list = transactionMapper.firstTransaction();

        for (GetFirstAcctBalInsertRes res : list) {
            TransactionInsertGeq trnsReq = new TransactionInsertGeq();
            trnsReq.setAcctId(res.getAcctId());
            trnsReq.setTrnsFeeId(1);
            trnsReq.setTrnsAmt(res.getAcctBal());
            trnsReq.setTrnsBal(res.getAcctBal());
            trnsReq.setTrnsTp((byte)1);
            trnsReq.setTrnsDes("첫 계좌 입금");

            transactionMapper.transactionInsert(trnsReq);
            sqlSession.flushStatements();
        }




    }

}