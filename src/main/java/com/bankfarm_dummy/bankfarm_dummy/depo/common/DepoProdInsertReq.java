package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepoProdInsertReq {
    private long depoProdId;
    private String depoProdNm;
    private LocalDate depoStDt;
    private LocalDate depoEdDt;
    private String depoPodDes;
    private String depoIntrstCalcUnit;
    private String depoIntrstPayCycle;
    private String depoIntrstCalcTp;
    private char depoSaleYn;
}
