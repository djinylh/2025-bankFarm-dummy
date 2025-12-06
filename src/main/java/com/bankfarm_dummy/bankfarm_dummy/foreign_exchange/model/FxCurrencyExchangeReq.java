package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class FxCurrencyExchangeReq {
    private Long fxRtId;
    private Long empId;
    private Long custId;
    private Long fxFromAcctId;
    private Long fxToAcctId;
    private BigDecimal fxFromAmt;
    private BigDecimal fxToAmt;
    private String fxTrnsTp;
    private String fxExchangePurpose;
    private LocalDateTime fxReqDt;
    private LocalDateTime fxTrnsDt;
    private String fxTrnsCd;
}
