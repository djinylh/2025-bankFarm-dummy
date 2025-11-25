package com.bankfarm_dummy.bankfarm_dummy.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_bacnt_mst")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankAccount {


    @Id
    @Column(name = "BACNT_NO", length = 20, nullable = false)
    private String accountNo;

    // FK 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUST_ID", nullable = false)
    private Customer customer;

    @Column(name = "BACNT_PSWD", length = 6, nullable = false)
    private String accountPassword;

    @Column(name = "BANK_CD", length = 10, nullable = false)
    private String bankCode;

    @Column(name = "BACNT_NM", length = 100, nullable = false)
    private String accountName;

    @Column(name = "DPSTR_NM", length = 10, nullable = false)
    private String depositorName;

    @Column(name = "BACNT_BLNC", precision = 15, scale = 0, nullable = false)
    private BigDecimal balance;

    @Column(name = "BACNT_ESTBL_YMD", nullable = false)
    private LocalDateTime establishedDate;

    @Column(name = "BACNT_MTRY_YMD", nullable = false)
    private LocalDateTime maturityDate;

    @Column(name = "BACNT_CNCL_YMD")
    private LocalDateTime cancelDate;

    @Column(name = "BACNT_TY", length = 2)
    private String accountType;

    @Column(name = "LIM_AMT", precision = 10, scale = 0, nullable = false)
    private BigDecimal limitAmount;

    @Column(name = "MNG_BRNCH_ID", length = 10, nullable = false)
    private String manageBranchId;

    @Column(name = "BACNT_USE_YN", length = 1, nullable = false)
    private String useYn;

    @Column(name = "VR_BACNT_YN", length = 1)
    private String virtualAccountYn;

    @Column(name = "RMRK", length = 300)
    private String remark;

    @Column(name = "RGT_GUBUN", length = 2, nullable = false)
    private String regtGubun;

    @Column(name = "RGT_ID", length = 10, nullable = false)
    private String regtId;

    @Column(name = "RGT_DTM", nullable = false)
    private LocalDateTime regtDatetime;

    @Column(name = "MDF_ID", length = 10)
    private String mdfId;

    @Column(name = "MDF_DTM")
    private LocalDateTime mdfDatetime;
}
