package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DepoContractInsertReq {
    private long depoContractId;
    private long custId;
    private long depoProdId;
    private long acctId;
    private long empId;
    private LocalDate depoContractDt;
    private LocalDate depoMaturityDt;
    private BigDecimal depoAppliedIntrstRt;
    private String depoActiveCd;
    private String depoPayoutBankCd;
    private String depoPayoutAcctNum;
    private String depoPayoutTp;
}