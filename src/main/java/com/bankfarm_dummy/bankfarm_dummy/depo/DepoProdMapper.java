package com.bankfarm_dummy.bankfarm_dummy.depo;

import com.bankfarm_dummy.bankfarm_dummy.depo.model.DepoProdInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepoProdMapper {

    int depoProdInsert(DepoProdInsertReq depoProdReq);

}
