package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
