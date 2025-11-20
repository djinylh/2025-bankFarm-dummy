package com.bankfarm_dummy.bankfarm_dummy.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CustomerJoinReq {
    private long custId;
    private String custName;
    private String custPhone;
    private String custEmail;
    private String custbirth;
    private int custCrdPoint;
    private String custSsn;
    private String custCd;
    private String custTp;
    private String custMarketingYn;
}
