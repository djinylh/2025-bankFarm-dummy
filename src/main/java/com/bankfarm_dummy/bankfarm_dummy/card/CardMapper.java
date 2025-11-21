package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.card.model.CardReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardMapper {

    int cardProduct(CardReq req);


}
