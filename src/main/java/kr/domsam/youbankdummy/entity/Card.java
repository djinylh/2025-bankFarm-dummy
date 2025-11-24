package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long cardId; // 신용/체크 카드(상품) ID

    @Column(name = "card_nm", length = 30, nullable = false)
    private String cardNm; // 카드명

    @Column(name = "card_annual_fee")
    private Integer cardAnnualFee; // 제휴연회비

    @Column(name = "card_tp", nullable = false)
    private Integer cardTp; // 카드종류 (0: 신용, 1: 체크)

    @Column(name = "card_min_require")
    private Integer cardMinRequire; // 최소요구실적

    @Column(name = "card_sale_yn", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private String cardSaleYn; // 판매상태 (Y: 판매중, N: 판매중지)

    @Column(name = "card_st_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime cardStAt; // 출시일

    @Column(name = "card_ed_at")
    private LocalDateTime cardEdAt; // 종료일

    @PrePersist
    protected void onCreate() {
        if (cardStAt == null) {
            cardStAt = LocalDateTime.now();
        }
        if (cardSaleYn == null) {
            cardSaleYn = "Y";
        }
    }
}

