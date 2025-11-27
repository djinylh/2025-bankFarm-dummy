package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class LoanOverDueInsertReq {
    private long loanRpmtId;
    private long loanId;
    private String loanOdDueSts;
    private long loanOdAmt;
    private LocalDate loanOdStDt;
    private LocalDate loanOdEdDt;
    private BigDecimal loanOdIntrst;
    private long loanOdFnAmt;
    private int loanOdMonth;
}
