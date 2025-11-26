package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanAppInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanGetAppPkRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanAppMapper {
    //대출 계약
    int loanAppInsert(LoanAppInsertReq req);
    // 대출 승인이 난 계약 Pk, 대출 기간, 승인 시간, 금액
    List<LoanGetAppPkRes> loanAppByCd002();
}
