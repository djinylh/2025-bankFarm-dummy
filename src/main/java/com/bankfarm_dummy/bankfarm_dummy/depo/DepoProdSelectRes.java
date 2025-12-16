package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
public class DepoProdSelectRes {
    private Long depoProdId;
    private String depoProdTp;
    private LocalDate depoStartDt;
    private LocalDate depoEndDt;
    private int depoTermMonth;
    private int depoMinAmt;
    private int depoMaxAmt;
}
