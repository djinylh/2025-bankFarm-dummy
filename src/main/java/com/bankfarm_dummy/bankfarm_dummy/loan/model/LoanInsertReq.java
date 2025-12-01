package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanInsertReq {
    private long loanAppId;
    private String loanNum;
    private String loanBnkCd;
    private BigDecimal loanFnIntrst;
    private LocalDate loanEdDt;
    private String loanFnRpmtTp;
    private String loanUseAcct;
    private String loanUseBnkCd;
    private String loanDueSts;
}
