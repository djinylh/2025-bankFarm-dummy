package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.medel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FxRtHistoryReq {
    private String FxCurrencyId;
    private BigDecimal fxChargeRt;
    private BigDecimal fxCommission;
    private LocalDateTime fxCrtAt;
}
