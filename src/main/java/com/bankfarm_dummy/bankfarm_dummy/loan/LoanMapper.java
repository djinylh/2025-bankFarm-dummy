package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.loan.model.GetLoanProductDesRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanInsertReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanMapper {
    // 대출
    int loanInsert(LoanInsertReq req);
    // 대출 리스트에서 직원이랑 가입일 찾기
    List<GetLoanProductDesRes> getLoanProductDesList();
}
