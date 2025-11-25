package com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
