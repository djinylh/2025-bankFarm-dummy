package com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common;

import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.GetBranchByEmpIdRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.GetLoanOverdueRes;
import com.bankfarm_dummy.bankfarm_dummy.loan.overdue_common.model.LoanOverdueCommonInsertReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanOverdueCommonMapper {
    // 총 연체기록 입력
    int overdueCommonInsert(LoanOverdueCommonInsertReq req);
    // 연체기록 정보 불러오기 직원 포함
    List<GetLoanOverdueRes> getLoanOverdueList();
    // 직원 아이디로 지점 아이디 찾기
    GetBranchByEmpIdRes getBranchIdByEmpId(long empId);

}
