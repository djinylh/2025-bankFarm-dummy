package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GetLoanRepaymentByTx007 {
    private long loanRpmtId;
    private long loanId;
    private BigDecimal loanRpmtIntrst;
    private long loanRpmtDue;
    private LocalDate loanDueDt;
    private BigDecimal rtPct;
}
