package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer2 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id", nullable = false)
    private Long custId; // 고객 ID

    @Column(name = "cust_nm", length = 11, nullable = false)
    private String custNm; // 이름

    @Column(name = "cust_phone", length = 13, nullable = false)
    private String custPhone; // 연락처

    @Column(name = "cust_email", length = 255, nullable = false)
    private String custEmail; // 이메일

    @Column(name = "cust_birth", length = 10, nullable = false)
    private String custBirth; // 생년월일

    @Column(name = "cust_crd_point", nullable = false)
    private Integer custCrdPoint; // 신용점수

    @Column(name = "cust_crt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime custCrtAt; // 가입일

    @Column(name = "cust_ssn", length = 20, nullable = false)
    private String custSsn; // 주민번호

    @Column(name = "cust_cd", length = 30, nullable = false)
    private String custCd; // 고객등급 (BASIC, SILVER, GOLD, VIP)

    @Column(name = "cust_tp", length = 30, nullable = false)
    private String custTp; // 고객유형 (PERSONAL, BUSINESS, CORPORATE)

    @Column(name = "cust_marketing_yn", length = 1, nullable = false)
    private String custMarketingYn; // 마케팅 동의여부 (Y/N)

    @PrePersist
    protected void onCreate() {
        if (custCrtAt == null) {
            custCrtAt = LocalDateTime.now();
        }
    }
}
