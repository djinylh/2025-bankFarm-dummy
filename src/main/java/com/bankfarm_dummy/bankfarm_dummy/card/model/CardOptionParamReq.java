package com.bankfarm_dummy.bankfarm_dummy.card.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardOptionParamReq {

    private long cardOptionDefId;
    private String cardOptionDes;
    private char cardActiveYn;
}