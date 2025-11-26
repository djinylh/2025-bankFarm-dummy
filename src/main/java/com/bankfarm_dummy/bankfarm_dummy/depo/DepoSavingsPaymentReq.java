package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DepoSavingsPaymentReq {
  private Long depoContractId;
  private LocalDate depoPaidDt;
  private Long depoPaidAmt;
  private String depoPaymentYn;
}
