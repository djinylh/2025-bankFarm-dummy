package com.bankfarm_dummy.bankfarm_dummy.employees;


import com.bankfarm_dummy.bankfarm_dummy.employees.model.EmployeesJoinReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeesMapper {

    int employeesJoin(EmployeesJoinReq epReq);

}
