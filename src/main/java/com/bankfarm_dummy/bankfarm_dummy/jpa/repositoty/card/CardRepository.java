package com.bankfarm_dummy.bankfarm_dummy.jpa.repositoty.card;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardRepository extends JpaRepository<Card, String> {
}
