package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.CardInstallmentSchedule;
import kr.domsam.youbankdummy.entity.CreditCardStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CardInstallmentScheduleRepository extends JpaRepository<CardInstallmentSchedule, String> {

    List<CardInstallmentSchedule> findByCardDueAtBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByCreditCardStatement(CreditCardStatement cs);

    @Query("select distinct c.creditCardStatement.cardCrdStatementId from CardInstallmentSchedule c")
    Set<Long> findAllCreditCardStatementIds();

}
