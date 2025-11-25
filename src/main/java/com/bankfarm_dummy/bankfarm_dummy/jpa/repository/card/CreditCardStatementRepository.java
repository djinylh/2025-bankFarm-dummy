package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CreditCardStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardStatementRepository extends JpaRepository<CreditCardStatement, String> {
}
