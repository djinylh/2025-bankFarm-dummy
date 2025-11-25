package com.bankfarm_dummy.bankfarm_dummy.counseling;

import com.bankfarm_dummy.bankfarm_dummy.counseling.model.CounselingReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CounselingMapper {

    // 상담 등록
    int counselingJoin(CounselingReq counselingReq);

}
