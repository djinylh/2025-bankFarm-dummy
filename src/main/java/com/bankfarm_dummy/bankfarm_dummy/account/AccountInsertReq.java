package com.bankfarm_dummy.bankfarm_dummy.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInsertReq {
    private long acctId;
    private long custId;
    private byte accTp;
    private String acctSavTp;
    private String acctNum;
    private String acctPw;
    private long acctBal;
    private long acctDayLimit;
    private String acctStsCd;
    private char acctIsDedYn;

}
