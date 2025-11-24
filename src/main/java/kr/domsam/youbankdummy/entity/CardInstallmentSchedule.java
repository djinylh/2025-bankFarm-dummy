package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card_installment_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInstallmentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_installment_schedule_id", nullable = false, columnDefinition = "BIGINT COMMENT '할부 ID'")
    private Long cardInstallmentScheduleId;

    /**
     * credit_card_statement 테이블의 외래키
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_crd_statement_id", referencedColumnName = "card_crd_statement_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_card_installment_statement"))
    private CreditCardStatement creditCardStatement;

    @Column(name = "card_month_no", nullable = false, columnDefinition = "INT COMMENT '할부회차'")
    private Integer cardMonthNo;

    @Column(name = "card_installment_amt", nullable = false, columnDefinition = "INT COMMENT '할부 금액'")
    private Integer cardInstallmentAmt;

    @Column(name = "card_due_at", nullable = false, columnDefinition = "DATETIME COMMENT '결제예정일'")
    private LocalDateTime cardDueAt;
    @Builder.Default
    @Column(name = "card_crt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일'")
    private LocalDateTime cardCrtAt = LocalDateTime.now();
    @Builder.Default
    @Column(name = "card_schedule_refund_yn", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N' COMMENT '환불여부'")
    private String cardScheduleRefundYn = "N";
}