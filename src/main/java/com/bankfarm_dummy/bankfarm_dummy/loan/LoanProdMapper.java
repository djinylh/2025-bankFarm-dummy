package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanProdGetAmtRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanProdInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanProdMapper {
    // 대출 상품
    int loanProdInsert(LoanProdInsertReq req);

    // 대출 상품 최대 금액, 기간 가져오기
    LoanProdGetAmtRes loanProdGetAmt(long loanProdId);

}
