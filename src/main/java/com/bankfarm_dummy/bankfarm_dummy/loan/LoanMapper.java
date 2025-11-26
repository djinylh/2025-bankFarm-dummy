package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanMapper {
    // 대출
    int loanInsert(LoanInsertReq req);

}
