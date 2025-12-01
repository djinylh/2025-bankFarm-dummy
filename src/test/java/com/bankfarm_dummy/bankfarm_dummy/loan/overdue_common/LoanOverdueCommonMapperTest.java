package com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.GetBranchByEmpIdRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.GetLoanOverdueRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.LoanOverdueCommonInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.ProdDocumentMapper;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.model.ProdDocumentReq;
import com.bankfarm_dummy.bankfarm_dummy.product_enum.ProductEnum;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanOverdueCommonMapperTest extends Dummy {


    final int BATCH_SIZE = 1000;
    int count = 0;

    @Test
    void getBranchByEmpIdRes() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        LoanOverdueCommonMapper mapper = sqlSession.getMapper(LoanOverdueCommonMapper.class);
        List<GetLoanOverdueRes> overRes = mapper.getLoanOverdueList();

        for(GetLoanOverdueRes res : overRes){
            LoanOverdueCommonInsertReq req = new LoanOverdueCommonInsertReq();
            req.setCustId(res.getCustId());
            req.setOdSourceId(res.getLoanRpmtId());
            req.setOdTp("PD007");
            req.setOdStDt(res.getLoanOdStDt());
            req.setOdAmt(res.getLoanOdFnAmt());

            mapper.overdueCommonInsert(req);

            count++;

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