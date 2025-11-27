package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanOverDueInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanOverdueMapper {
    int loanOverdueInsert(LoanOverDueInsertReq req);

}
