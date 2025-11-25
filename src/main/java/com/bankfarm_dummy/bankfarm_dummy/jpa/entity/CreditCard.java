package com.bankfarm_dummy.bankfarm_dummy.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard {

    @Id
    @Column(name = "card_user_id", nullable = false)
    private Long cardUserId; // 카드 ID

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // card_user_id가 UserCard의 PK를 그대로 공유함
    @JoinColumn(name = "card_user_id", foreignKey = @ForeignKey(name = "fk_credit_card_user_card"))
    private UserCard userCard; // user_card 테이블 참조 (1:1 관계)

    @Column(name = "card_bank_code", length = 5, nullable = false)
    private String cardBankCode; // 은행코드

    @Column(name = "card_acct_id", length = 30, nullable = false)
    private String cardAcctId; // 실사용 계좌

    @Column(name = "card_due_day", nullable = false)
    private Byte cardDueDay; // 결제 예정 날짜
}