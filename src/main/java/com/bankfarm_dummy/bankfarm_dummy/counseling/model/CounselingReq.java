package com.bankfarm_dummy.bankfarm_dummy.counseling.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CounselingReq {
    private long counId;
    private long empId;
    private long custId;
    private String counTitle;
    private String counContent;
    private String counTpCd;
    private String counCrtAt;

}
