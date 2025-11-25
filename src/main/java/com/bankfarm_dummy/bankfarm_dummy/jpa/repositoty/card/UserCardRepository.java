package com.bankfarm_dummy.bankfarm_dummy.jpa.repositoty.card;

import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCardRepository extends JpaRepository<UserCard, String> {
}
