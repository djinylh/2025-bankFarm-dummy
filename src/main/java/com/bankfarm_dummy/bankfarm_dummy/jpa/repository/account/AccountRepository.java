package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.account;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, String> {
}
