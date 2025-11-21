package com.bankfarm_dummy.bankfarm_dummy.card.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserCardReq {

    private long cardUserId;
    private long cardId;
    private long custId;
    private long empId;
    private String cardNum;
    private String cardSts;
    private int cardDayLimit;
    private int cardMonthLimit;
    private LocalDateTime cardCrtAt;
    private LocalDate cardEdAt;
    private LocalDateTime cardDeacAt;



}
