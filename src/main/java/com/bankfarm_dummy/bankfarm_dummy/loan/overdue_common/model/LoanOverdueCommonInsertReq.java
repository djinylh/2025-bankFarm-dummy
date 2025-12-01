package com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model;

import com.bankfarm_dummy.bankfarm_dummy.product_enum.ProductEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoanOverdueCommonInsertReq {
    private long custId;
    private long odSourceId;
    private String odTp;
    private LocalDate odStDt;
    private long odAmt;
}
