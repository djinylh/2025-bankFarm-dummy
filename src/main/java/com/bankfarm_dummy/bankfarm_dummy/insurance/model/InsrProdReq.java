package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class InsrProdReq {
    private Long insrProdId;
    private Long partId;
    private String insrCd;
    private String insrNm;
    private String insrProdTp;
    private LocalDate insrOpenDt;
    private LocalDate insrCloseDt;
    private BigDecimal insrCommission;
    private String insrSaleYn;
}
