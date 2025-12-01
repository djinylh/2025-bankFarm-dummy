package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanProductDesRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.LoanOverdueCommonMapper;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.GetBranchByEmpIdRes;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.ProdDocumentMapper;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.model.ProdDocumentReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanCommonProductMapperTest extends Dummy {

    final int BATCH_SIZE = 1000;
    int count = 0;

    @Test
    void getLoanCommonProductMapperTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        ProdDocumentMapper prodDocumentMapper = sqlSession.getMapper(ProdDocumentMapper.class);
        LoanMapper loanMapper = sqlSession.getMapper(LoanMapper.class);
        LoanOverdueCommonMapper  loanCommonMapper = sqlSession.getMapper(LoanOverdueCommonMapper.class);

        List<GetLoanProductDesRes> desRes = loanMapper.getLoanProductDesList();


        for(GetLoanProductDesRes productDesRes : desRes){
            GetBranchByEmpIdRes branchRes = loanCommonMapper.getBranchIdByEmpId(productDesRes.getEmpId());
            long branchId = branchRes.getBranId();


            ProdDocumentReq req = new ProdDocumentReq();
            req.setBranId(branchId);
            req.setDocProdId(productDesRes.getLoanAppId());
            req.setDocProdTp("PD007");
            req.setDocNm("대출 계약 문서 이름");
            req.setDocCrtAt(productDesRes.getLoanDcsnDt());

            prodDocumentMapper.prodDocumentJoin(req);

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