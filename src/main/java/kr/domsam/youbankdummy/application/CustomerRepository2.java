package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.Customer2;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository2 extends JpaRepository<Customer2, String> {
}
