package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.CheckCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckCardRepository extends JpaRepository<CheckCard, String>{

}
