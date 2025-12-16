package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter@Setter@ToString
public class DepoTermReq {
    private Long contractId;
    private Date termDt;
    private String termTp;
}
