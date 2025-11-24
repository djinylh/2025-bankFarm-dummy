package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, String> {
}
