package com.bankfarm_dummy.bankfarm_dummy.depo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepoProdRateReq {
  private String prodTp;
  private Long prodId;
  private Long prodRtId;
}
