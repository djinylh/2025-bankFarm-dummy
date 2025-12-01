package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GetLoanInfoRes {
    private long loanId;
    private LocalDate loanDcsnDt;
    private long loanReqAmt;
    private int loanReqTrm;
    private BigDecimal loanFnIntrst;

}
