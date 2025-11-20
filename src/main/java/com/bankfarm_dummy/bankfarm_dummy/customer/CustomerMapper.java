package com.bankfarm_dummy.bankfarm_dummy.customer;

import com.bankfarm_dummy.bankfarm_dummy.customer.model.CustBusinessReq;
import com.bankfarm_dummy.bankfarm_dummy.customer.model.CustomerJoinReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    int custJoin(CustomerJoinReq cust);
    int busineesJoin(CustBusinessReq businees);

}
