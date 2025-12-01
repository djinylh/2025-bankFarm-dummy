package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class LoanProdInsertReq {
    private String loanProdNm;
    private String loanDes;
    private long loanMaxAmt;
    private String loanRpmtTp;
    private int loanTermMo;
    private LocalDate loanStDt;
    private LocalDate loanEdDt;
    private char loanStsYn;
}
