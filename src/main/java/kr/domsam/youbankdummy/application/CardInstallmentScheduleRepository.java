package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.CardInstallmentSchedule;
import kr.domsam.youbankdummy.entity.CreditCardStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardInstallmentScheduleRepository extends JpaRepository<CardInstallmentSchedule, String> {

    List<CardInstallmentSchedule> findByCardDueAtBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByCreditCardStatement(CreditCardStatement cs);
}
