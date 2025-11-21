package com.bankfarm_dummy.bankfarm_dummy.card;


import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionDefReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardOptionDefMapper {

    int cardOptionDef(CardOptionDefReq req);

}
