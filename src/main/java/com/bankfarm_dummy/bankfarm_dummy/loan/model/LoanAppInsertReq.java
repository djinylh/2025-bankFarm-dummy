package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LoanAppInsertReq {
    private long custId;
    private long loanProdId;
    private long empId;
    private long loanReqAmt;
    private int loanReqTrm;
    private String loanAppStsCd;
    private LocalDateTime loanDcsnDt;
    private LocalDateTime loanAppDt;
    private String loanRjctRsn;

}
