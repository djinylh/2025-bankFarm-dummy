package com.bankfarm_dummy.bankfarm_dummy.account;

import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumReq;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumRes;

public interface AccountMapper {

    int accountInsert(AccountInsertReq accountReq);

    AccountFindAcctNumRes countByAccountNum(AccountFindAcctNumReq  accountFindAcctNumReq);
}
