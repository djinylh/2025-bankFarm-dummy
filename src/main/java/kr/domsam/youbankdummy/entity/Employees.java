package kr.domsam.youbankdummy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", nullable = false)
    private Long empId; // 직원 ID

    @Column(name = "bran_id", nullable = false)
    private Long branId; // 지점 ID

    @Column(name = "emp_email", length = 255, nullable = false)
    private String empEmail; // 이메일

    @Column(name = "emp_nm", length = 50, nullable = false)
    private String empNm; // 직원명

    @Column(name = "emp_phone", length = 20, nullable = false)
    private String empPhone; // 직원 연락처

    @Column(name = "emp_hire_dt", nullable = false)
    private LocalDate empHireDt; // 입사 일자

    @Column(name = "emp_resignation_at")
    private LocalDate empResignationAt; // 퇴사 일자 (NULL 허용)

    @Column(name = "emp_position_nm", length = 30, nullable = false)
    private String empPositionNm; // 직급명

    @Column(name = "emp_crt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime empCrtAt; // 생성 일시

    @Column(name = "emp_upt_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime empUptAt; // 수정 일시

    @PrePersist
    protected void onCreate() {
        if (empCrtAt == null) {
            empCrtAt = LocalDateTime.now();
        }
        if (empUptAt == null) {
            empUptAt = LocalDateTime.now();
        }
    }


    @PreUpdate
    protected void onUpdate() {
        empUptAt = LocalDateTime.now();
    }
}