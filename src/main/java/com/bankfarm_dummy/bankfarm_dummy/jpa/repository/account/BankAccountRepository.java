package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.account;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
