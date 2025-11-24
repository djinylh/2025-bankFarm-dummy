package com.bankfarm_dummy.bankfarm_dummy.transaction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetFirstAcctBalInsertRes {
    private long acctId;
    private long acctBal;

}
