package com.bankfarm_dummy.bankfarm_dummy.employees.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeesJoinReq {
    private  long branId;
    private  String empEmail;
    private  String empNm;
    private  String empPhone;
    private  LocalDate empHireDt;
    private  LocalDate empResignationAt;
    private  String empPositionNm;
}
