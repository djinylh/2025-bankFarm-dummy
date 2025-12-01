package com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetLoanOverdueRes {
    private long loanRpmtId;
    private long loanOdFnAmt;
    private LocalDate loanOdStDt;
    private long custId;
    private long empId;
}
