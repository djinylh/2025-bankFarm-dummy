package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepoProdMapper {

    int depoProdInsert(DepoProdInsertReq depoProdReq);

}
