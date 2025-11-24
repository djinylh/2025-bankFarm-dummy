package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
