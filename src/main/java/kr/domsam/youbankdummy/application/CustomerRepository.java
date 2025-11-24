package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, String> {
}
