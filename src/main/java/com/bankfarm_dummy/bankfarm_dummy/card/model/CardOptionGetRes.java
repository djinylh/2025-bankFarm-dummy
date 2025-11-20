package com.bankfarm_dummy.bankfarm_dummy.card.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class CardOptionGetRes {
    private String cardOptionCode;
    private long cardOptionDefId;
}