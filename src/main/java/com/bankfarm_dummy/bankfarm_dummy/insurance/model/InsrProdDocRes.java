package com.bankfarm_dummy.bankfarm_dummy.insurance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InsrProdDocRes {
    private Long branId;
    private Long contractId;
    private LocalDateTime contractDate;
}
