package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InsrContractRes {
    private Long insrContractId;
    private Long insrProdId;
    private LocalDateTime insrContractDt;
    private Byte insrPaymentDay;
    private LocalDate insrPaymentEndDt;
    private String insrActiveCd;
    private LocalDate insrMaturityDt;
}
