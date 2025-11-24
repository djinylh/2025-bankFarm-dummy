package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_user_id", nullable = false)
    private Long cardUserId; // 카드 ID

    // 카드상품 (card.card_id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card; // 카드상품 ID

    // 고객 (customer.cust_id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cust_id", nullable = false)
    private Customer2 customer2; // 고객 ID

    // 직원 (employees.emp_id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employees employee; // 직원 ID

    @Column(name = "card_num", length = 30, nullable = false, unique = true)
    private String cardNum; // 카드번호

    @Column(name = "card_sts", length = 30, nullable = false)
    private String cardSts; // 카드상태 (정상, 거래정지, 분실, 만료, 해지 등)

    @Column(name = "card_day_limit", nullable = false)
    private Integer cardDayLimit; // 일일한도

    @Column(name = "card_month_limit", nullable = false)
    private Integer cardMonthLimit; // 월간한도

    @Column(name = "card_crt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime cardCrtAt; // 생성일

    @Column(name = "card_ed_at", nullable = false)
    private LocalDateTime cardEdAt; // 만료일

    @Column(name = "card_deac_at")
    private LocalDateTime cardDeacAt; // 비활성일시 (활성화 시 다시 NULL)

    @PrePersist
    protected void onCreate() {
        if (cardCrtAt == null) {
            cardCrtAt = LocalDateTime.now();
        }
    }
}
