package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_billing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_billing_id", nullable = false, columnDefinition = "BIGINT COMMENT '청구서 ID'")
    private Long cardBillingId;

    /**
     * card 테이블의 외래키
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_user_id", referencedColumnName = "card_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_card_billing_user"))
    private UserCard userCard;

    @Column(name = "card_billing_year_month", nullable = false, columnDefinition = "DATE COMMENT '청구연월'")
    private LocalDate cardBillingYearMonth;
    @Builder.Default
    @Column(name = "card_installment_amt", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '결제할 할부금'")
    private Integer cardInstallmentAmt = 0;
    @Builder.Default
    @Column(name = "card_new_charges", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '신규사용액'")
    private Integer cardNewCharges = 0;
    @Builder.Default
    @Column(name = "card_total_due", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '최종 청구액'")
    private Integer cardTotalDue = 0;
    @Builder.Default
    @Column(name = "card_paid_amt", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '납부한 금액'")
    private Integer cardPaidAmt = 0;

    @Column(name = "card_due_date", columnDefinition = "DATETIME DEFAULT NULL COMMENT '납부 마감일'")
    private LocalDateTime cardDueDate;

    @Column(name = "card_billing_sts", length = 30, nullable = false, columnDefinition = "VARCHAR(5) COMMENT '청구 상태 (GENERATED / BILLED / PAID / OVERDUE / CLOSED)'")
    private String cardBillingSts;
}