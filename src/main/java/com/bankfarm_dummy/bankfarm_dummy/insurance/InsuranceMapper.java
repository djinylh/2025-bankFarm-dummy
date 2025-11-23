package com.bankfarm_dummy.bankfarm_dummy.insurance;

import com.bankfarm_dummy.bankfarm_dummy.insurance.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InsuranceMapper {
    int insrProdInsert(InsrProdReq insrProdReq);
    int insrContractInsert(InsrContractReq insrContractReq);
    int insrPaymentHistoryInsert(InsrPaymentHistoryReq insrPaymentHistoryReq);
    int insrTermInsert(InsrTermReq insrTermReq);

    List<Long> selectActivePartnerIds();
    List<Long> selectInsrProdIds();
    List<Long> selectEmployeeIds();
    List<Long> selectCustomerIds();

    List<InsrContractRes> selectAllContracts();

    List<InsrContractRes> selectExpiredContracts();
}
