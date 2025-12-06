package com.bankfarm_dummy.bankfarm_dummy.depo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepoTermMapper {
    int insertTermContract(DepoTermReq req);
    List<DepoTermRes> selectTermContract();
}
