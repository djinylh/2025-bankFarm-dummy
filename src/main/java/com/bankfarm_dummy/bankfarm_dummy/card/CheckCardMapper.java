package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.card.model.CheckCardReq;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CheckCardRes;

import java.util.List;

public interface CheckCardMapper {
    // check카드 insert문
    int checkCardJoin(CheckCardReq req);
    // check카드 불러오기

    List<CheckCardRes> checkCardList();

}
