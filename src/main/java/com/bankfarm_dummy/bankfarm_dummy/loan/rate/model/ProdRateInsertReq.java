package com.bankfarm_dummy.bankfarm_dummy.loan.rate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdRateInsertReq {
    private String prodTp;
    private long prodId;
    private long prodRtId;
}
