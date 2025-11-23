package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InsrContractReq {
    private Long insrContractId;
    private Long insrProdId;
    private Long custId;
    private Long empId;
    private String insrContractNum;
    private String insrBankCd;
    private String insrAcctNum;
    private LocalDateTime insrContractDt;
    private LocalDate insrPaymentEndDt;
    private LocalDate insrMaturityDt;
    private String insrApprovalCd;
    private String insrActiveCd;
    private String insrRenewableYn;
    private Long insrRefundAmt;
    private Byte insrPaymentDay;
}
