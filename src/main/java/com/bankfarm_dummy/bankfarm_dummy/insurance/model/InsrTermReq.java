package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InsrTermReq {
    private Long insrTermId;
    private Long insrContractId;
    private String insrTermTp;
    private LocalDateTime insrTermDt;
    private String insrTermReason;
}
