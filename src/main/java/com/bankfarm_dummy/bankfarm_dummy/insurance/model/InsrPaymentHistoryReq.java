package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class InsrPaymentHistoryReq {
    private Integer insrPaymentSeq;
    private Long insrContractId;
    private LocalDate insrPaymentDt;
    private Long insrExpectedAmt;
    private LocalDate insrPaidDt;
    private Long insrPaidAmt;
    private String insrPaidYn;
    private String insrOdYn;
}

