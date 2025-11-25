package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import com.bankfarm_dummy.bankfarm_dummy.depo.DepoProdTermReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepoProdMapper {

    int depoProdInsert(DepoProdInsertReq depoProdReq);
    int insertDepoProd(DepoProdInsertReq depoProdReq);

    int insertDepoProdTerm(DepoProdTermReq depoProdTermReq);
}
