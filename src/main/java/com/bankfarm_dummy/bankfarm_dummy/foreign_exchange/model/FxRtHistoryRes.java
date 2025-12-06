package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FxRtHistoryRes {
    private Long fxRtId;
    private String fxCurrencyId;
    private BigDecimal fxChargeRt;
    private BigDecimal fxCommission;
    private LocalDateTime fxCrtAt;
}
