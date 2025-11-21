package com.bankfarm_dummy.bankfarm_dummy.depo;

import com.bankfarm_dummy.bankfarm_dummy.depo.model.DepoContractInsertReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepoContractMapper {

    int depoContractInsert(DepoContractInsertReq depoContractInsertReq);

}
