package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.medel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class FxRtHistoryRes {
    private Long fxRtId;
    private String fxCurrencyId;
    private BigDecimal fxChargeRt;
    private BigDecimal fxCommission;
    private int fxMinLimit;
}
