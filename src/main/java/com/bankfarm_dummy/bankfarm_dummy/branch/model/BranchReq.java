package com.bankfarm_dummy.bankfarm_dummy.branch.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
public class BranchReq {
    private long branId;
    private String branNm;
    private String branTel;
    private String branAddress;
    private Double branLatitude;
    private Double branLongitude;
    private LocalDate branOpenedAt;
    private String branActive;
    private String branRegionCd;
}


