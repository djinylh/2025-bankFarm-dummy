package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepoProdTermReq {
  private Long depoProdId;
  private int depoTermMonth;
  private int depoMinAmt;
  private Integer depoMaxAmt;
}
