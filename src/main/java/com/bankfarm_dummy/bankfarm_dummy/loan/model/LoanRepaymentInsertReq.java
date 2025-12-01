package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanRepaymentInsertReq {
    private long loanId;
    private long loanRpmtDue;
    private long loanRpmtPrncp;
    private BigDecimal loanRpmtIntrst;
    private LocalDate loanDueDt;
    private String loanDueSts;
}
