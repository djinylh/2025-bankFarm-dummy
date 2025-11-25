package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import com.bankfarm_dummy.bankfarm_dummy.depo.DepoProdTermReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepoContractMapper {
    int depoContractInsert(DepoContractInsertReq depoContractInsertReq);

    List<Long> selectEmployeeIds();
    List<Long> selectCustomerIds();
    List<Long> selectDepoProdIds();

}
