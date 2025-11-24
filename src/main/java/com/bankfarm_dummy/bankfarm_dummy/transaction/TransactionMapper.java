package com.bankfarm_dummy.bankfarm_dummy.transaction;

import com.bankfarm_dummy.bankfarm_dummy.transaction.model.GetFirstAcctBalInsertRes;
import com.bankfarm_dummy.bankfarm_dummy.transaction.model.TransactionInsertGeq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 입출금 공통 맵퍼
@Mapper
public interface TransactionMapper {
    //  입출금 공용 INSERT 메소드
    int transactionInsert(TransactionInsertGeq req);
    // 입출금 기록이 없는 계좌 리스트
    List<GetFirstAcctBalInsertRes> firstTransaction();
}
