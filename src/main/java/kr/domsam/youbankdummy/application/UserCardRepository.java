package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCardRepository extends JpaRepository<UserCard, String> {
}
