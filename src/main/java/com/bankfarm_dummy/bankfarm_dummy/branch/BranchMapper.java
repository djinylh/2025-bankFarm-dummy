package com.bankfarm_dummy.bankfarm_dummy.branch;

import com.bankfarm_dummy.bankfarm_dummy.branch.model.BranchReq;
import com.bankfarm_dummy.bankfarm_dummy.branch.model.GetBranchByEmpRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BranchMapper {

    // 지점 전체 조회
    int branchJoin(BranchReq branchReq);
    GetBranchByEmpRes getBranchIdByEmpId(long  empId);

}
