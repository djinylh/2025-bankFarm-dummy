package com.bankfarm_dummy.bankfarm_dummy.loan.rate;

import com.bankfarm_dummy.bankfarm_dummy.loan.rate.model.ProdRateInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProdRateMapper {
    int prodRateInsert(ProdRateInsertReq req);

}
