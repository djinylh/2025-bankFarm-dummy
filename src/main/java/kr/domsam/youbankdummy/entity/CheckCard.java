package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "check_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckCard {

    @Id
    @Column(name = "card_user_id", nullable = false, columnDefinition = "BIGINT COMMENT '카드 ID'")
    private Long cardUserId;

    /**
     * user_card 테이블의 외래키
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_user_id", referencedColumnName = "card_user_id",
            foreignKey = @ForeignKey(name = "fk_check_card_user"))
    private UserCard userCard;

    /**
     * account 테이블의 외래키
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_acct_id", referencedColumnName = "acct_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_check_card_account"))
    private Account account;

}