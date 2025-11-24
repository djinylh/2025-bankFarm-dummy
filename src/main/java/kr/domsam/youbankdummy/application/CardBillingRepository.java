package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.CardBilling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CardBillingRepository extends JpaRepository<CardBilling, String>  {
    Optional<CardBilling> findByUserCard_CardUserIdAndCardBillingYearMonth(Long cardUserId, LocalDate ym);
}
