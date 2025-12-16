package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepoContractSavings {
  private Long depoContractId;
  private Byte depoPaymentDay;
  private Long depoMonthlyAmt;
}
