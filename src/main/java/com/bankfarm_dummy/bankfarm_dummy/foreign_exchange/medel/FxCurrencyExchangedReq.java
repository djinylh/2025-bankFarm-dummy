package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.medel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class FxCurrencyExchangedReq {
    private Long fxRtId;
    private Long empId;
    private Long fxFromAcctId;
    private Long fxToAcctId;
    private BigDecimal fxFromAmt;
    private BigDecimal fxToAmt;
    private String fxTrnsTp;
    private String fxExchangePurpose;
    private LocalDate fxTrnsDt;
    private String fxTrnsCd;
}
