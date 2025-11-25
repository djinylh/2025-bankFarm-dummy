package com.bankfarm_dummy.bankfarm_dummy.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "credit_card_statement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_crd_statement_id", nullable = false, columnDefinition = "BIGINT COMMENT '명세서 ID'")
    private Long cardCrdStatementId;

    /**
     * user_card 테이블의 외래키
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_user_id", referencedColumnName = "card_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_credit_card_user"))
    private UserCard userCard;

    @Column(name = "card_place", length = 11, nullable = false, columnDefinition = "VARCHAR(11) COMMENT '사용처'")
    private String cardPlace;
    @Builder.Default
    @Column(name = "card_crd_refund_yn", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N' COMMENT '환불여부 (Y/N)'")
    private String cardCrdRefundYn = "N";

    @Column(name = "card_og_amt", nullable = false, columnDefinition = "INT COMMENT '원금액'")
    private Integer cardOgAmt;
    @Builder.Default
    @Column(name = "card_installments", columnDefinition = "INT DEFAULT 1 COMMENT '할부기간 (1초과시 할부)'")
    private Integer cardInstallments = 1;
    @Builder.Default
    @Column(name = "card_trns_dt", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '사용일자'")
    private LocalDateTime cardTrnsDt = LocalDateTime.now();

    @Column(name = "card_refunded_at", columnDefinition = "DATETIME COMMENT '환불일시'")
    private LocalDateTime cardRefundedAt;
}