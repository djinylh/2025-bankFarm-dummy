package com.bankfarm_dummy.bankfarm_dummy.card.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CardReq {

    private String cardNm;
    private int cardAnnualFee;
    private int cardTp;
    private int cardMinRequire;
    private char cardSaleYn;
    private LocalDateTime cardEdAt;



}
