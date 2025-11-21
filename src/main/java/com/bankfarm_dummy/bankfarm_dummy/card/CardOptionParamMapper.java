package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionParamReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardOptionParamMapper {
    int cardOptionParamDetail(CardOptionParamReq req);
    List<CardOptionGetRes> getListByOption();


}