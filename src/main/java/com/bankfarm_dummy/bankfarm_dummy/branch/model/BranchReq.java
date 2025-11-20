package com.bankfarm_dummy.bankfarm_dummy.branch.model;

import lombok.Data;

import java.util.Date;

@Data
public class BranchReq {
    private long branId;
    private String branNm;
    private String branTel;
    private String branAddress;
    private Double branLatitude;
    private Double branLongitude;
    private Date branOpenedAt;
    private String branActive;
    private String branRegionCd;
    private Date branClosedAt;
}


