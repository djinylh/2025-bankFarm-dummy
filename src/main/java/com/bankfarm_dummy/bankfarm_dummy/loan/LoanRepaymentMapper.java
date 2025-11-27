package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanInfoRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanRepaymentInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanRepaymentMapper {
    //대출 상환
    int loanRepaymentInsert(LoanRepaymentInsertReq req);
    // 대출 정보 조회
    GetLoanInfoRes GetLoanInfoByLoanId(long loanId);

}
