package com.bankfarm_dummy.bankfarm_dummy.depo.common;

import com.bankfarm_dummy.bankfarm_dummy.depo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DepoContractMapper {
    int depoContractInsert(DepoContractInsertReq depoContractInsertReq);

    List<Long> selectEmployeeIds();
    List<Long> selectCustomerIds();
    List<Long> selectDepoProdIds();
    List<DepoProdSelectRes> selectDepoProds();

    int insertDepoProdRate(DepoProdRateReq depoProdRateReq);
    List<Long> selectRateRule();
    BigDecimal selectProdTotalRate(Long prodId);

    int insertDepoContractDeposit(DepoContractDeposit depoContractDeposit);
    int insertDepoContractSavings(DepoContractSavings depoContractSavings);
    int insertDepoSavingsPayment(DepoSavingsPaymentReq depoSavingsPayment);

    Long selectBranchIdByEmpId(Long empId);

}
