package com.bankfarm_dummy.bankfarm_dummy.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustBusinessReq {
    private String custBsNm;
    private Long custId;
    private String custCpNm;
    private String custFax;
    private char custBsYn;
}


