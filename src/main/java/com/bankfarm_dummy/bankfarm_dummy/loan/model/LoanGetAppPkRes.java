package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoanGetAppPkRes {
    private long loanAppId;
    private long loanReqAmt;
    private int loanReqTrm;
    private LocalDate loanDcsnDt;
}
