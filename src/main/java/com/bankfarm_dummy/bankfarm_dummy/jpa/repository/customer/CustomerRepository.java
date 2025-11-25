package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.customer;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, String> {
}
