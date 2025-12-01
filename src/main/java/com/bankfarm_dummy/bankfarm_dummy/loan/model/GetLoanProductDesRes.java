package com.bankfarm_dummy.bankfarm_dummy.loan.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class GetLoanProductDesRes {
    private  long empId;
    private LocalDate loanDcsnDt;
    private long loanAppId;

}
