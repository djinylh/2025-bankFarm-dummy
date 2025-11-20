package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionParamReq;

import java.util.List;

public interface CardOptionParamMapper {

    int cardOptionParamDetail(CardOptionParamReq req);
    List<CardOptionGetRes> getListByOption();


}
