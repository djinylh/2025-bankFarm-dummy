package com.bankfarm_dummy.bankfarm_dummy.jpa.repositoty.card;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CardInstallmentSchedule;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CreditCardStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardInstallmentScheduleRepository extends JpaRepository<CardInstallmentSchedule, String> {

    List<CardInstallmentSchedule> findByCardDueAtBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByCreditCardStatement(CreditCardStatement cs);
}
