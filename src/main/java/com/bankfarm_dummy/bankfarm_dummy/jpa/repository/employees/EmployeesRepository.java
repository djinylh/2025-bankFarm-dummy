package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.employees;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeesRepository extends JpaRepository<Employees, String> {
}
