package com.bankfarm_dummy.bankfarm_dummy.transaction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionInsertGeq {
    private long acctId;
    private long trnsFeeId;
    private long trnsAmt;
    private String trnsAcctNum;
    private long trnsBal;
    private byte trnsTp;
    private String trnsDes;
}
