package kr.domsam.youbankdummy.entity;

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
    @MapsId  // user_card의 PK를 공유
    @JoinColumn(name = "card_user_id", foreignKey = @ForeignKey(name = "fk_credit_card_user"))
    private UserCard userCard;

    @Column(name = "card_bank_code", length = 5, nullable = false)
    private String cardBankCode; // 은행코드

    @Column(name = "card_acct_id", length = 30, nullable = false)
    private String cardAcctId; // 실사용 계좌

    @Column(name = "card_due_day", nullable = false)
    private Byte cardDueDay; // 결제 예정 날짜
}