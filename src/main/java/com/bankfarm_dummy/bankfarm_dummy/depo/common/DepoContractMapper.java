package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepoContractMapper {
    int depoContractInsert(DepoContractInsertReq depoContractInsertReq);

}
