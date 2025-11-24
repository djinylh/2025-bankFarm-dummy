package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_id", nullable = false)
    private Long acctId; // 계좌 ID

    // 외래키: customer.cust_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cust_id", nullable = false)
    private Customer customer; // 고객 (FK)

    @Column(name = "acct_tp", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer acctTp; // 계좌 타입 (0: 일반, 1: 내부)

    @Column(name = "acct_sav_tp", length = 30, nullable = false)
    private String acctSavTp; // 이체 여부 (BOTH, DEPOSIT_ONLY, WITHDRAW_ONLY)

    @Column(name = "acct_num", length = 20, nullable = false, unique = true)
    private String acctNum; // 계좌 번호

    @Column(name = "acct_pw", length = 4)
    private String acctPw; // 비밀번호 (암호화 필요)

    @Column(name = "acct_bal", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long acctBal; // 보유 잔액

    @Column(name = "acct_day_limit", nullable = false)
    private Long acctDayLimit; // 일일 출금 한도

    @Column(name = "acct_sts_cd", length = 30, nullable = false)
    private String acctStsCd; // 계좌 상태

    @Column(name = "acct_crt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime acctCrtAt; // 생성 일시

    @Column(name = "acct_is_ded_yn", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private String acctIsDedYn; // 요구불 여부 (Y/N)

    @PrePersist
    protected void onCreate() {
        if (acctCrtAt == null) {
            acctCrtAt = LocalDateTime.now();
        }
        if (acctIsDedYn == null) {
            acctIsDedYn = "Y";
        }
        if (acctTp == null) {
            acctTp = 0;
        }
        if (acctBal == null) {
            acctBal = 0L;
        }
    }
}
