package kr.domsam.youbankdummy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_cust_mst")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer {

    @Id
    @Column(name = "CUST_ID", length = 10, nullable = false)
    private String custId;

    @Column(name = "CUST_NM", length = 10, nullable = false)
    private String custName;

    @Column(name = "CUST_PSWD", length = 20, nullable = false)
    private String custPassword;

    @Column(name = "INHB_REG_NO", length = 13, nullable = false)
    private String residentRegNo;

    @Column(name = "BIRTH_DT", nullable = false)
    private LocalDate birthDate;

    @Column(name = "GENDER", length = 1, nullable = false)
    private String gender;

    @Column(name = "CUST_TEL_NO", length = 20, nullable = false)
    private String custTelNo;

    @Column(name = "CUST_ADDRESS", length = 300, nullable = false)
    private String custAddress;

    @Column(name = "REGT_DT", nullable = false)
    private LocalDate regtDate;

    @Column(name = "RGT_GUBUN", length = 2, nullable = false)
    private String regtGubun;

    @Column(name = "RGT_ID", length = 10, nullable = false)
    private String regtId;

    @Column(name = "RGT_DTM", nullable = false)
    private LocalDateTime regtDateTime;

    @Column(name = "MDF_ID", length = 10)
    private String mdfId;

    @Column(name = "MDF_DTM")
    private LocalDateTime mdfDateTime;
}
