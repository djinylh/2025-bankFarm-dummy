package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeesRepository extends JpaRepository<Employees, String> {
}
