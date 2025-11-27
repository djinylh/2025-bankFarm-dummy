package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanInfoRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanRepaymentByTx007;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanRepaymentInsertReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanRepaymentMapper {
    //대출 상환
    int loanRepaymentInsert(LoanRepaymentInsertReq req);
    // 대출 정보 조회
    GetLoanInfoRes GetLoanInfoByLoanId(long loanId);
    // 연체된 애들 조회
    List<GetLoanRepaymentByTx007> getByTx007();
}
