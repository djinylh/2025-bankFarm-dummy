package com.bankfarm_dummy.bankfarm_dummy.account;

import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumReq;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumRes;

public interface AccountMapper {
    // 계좌 테이블 인서트 메소드
    int accountInsert(AccountInsertReq accountReq);
    // 계좌 번호 중복 체크용 메소드
    AccountFindAcctNumRes countByAccountNum(AccountFindAcctNumReq  accountFindAcctNumReq);
}
