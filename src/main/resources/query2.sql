CREATE TABLE customer (
    cust_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '고객 ID',
    cust_nm VARCHAR(11) NOT NULL COMMENT '이름',
    cust_phone VARCHAR(13) NOT NULL COMMENT '연락처',
    cust_email VARCHAR(255) NOT NULL COMMENT '이메일',
    cust_birth VARCHAR(10) NOT NULL COMMENT '생년월일',
    cust_crd_point INT NOT NULL COMMENT '신용점수',
    cust_crt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
    cust_ssn VARCHAR(20) NOT NULL COMMENT '주민번호',
    cust_cd VARCHAR(30) NOT NULL COMMENT '고객등급 (BASIC(일반), SILVER, GOLD, VIP)',
    cust_tp VARCHAR(30) NOT NULL COMMENT '고객유형 (PERSONAL(개인), BUSINESS(개인사업자), CORPORATE(법인사업자))',
    cust_marketing_yn CHAR(1) NOT NULL COMMENT '마케팅 동의여부 (Y/N)',
    PRIMARY KEY (cust_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='고객';






넌 JPA 전문가야.
### CREATE문
CREATE TABLE customer (
    cust_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '고객 ID',
    cust_nm VARCHAR(11) NOT NULL COMMENT '이름',
    cust_phone VARCHAR(13) NOT NULL COMMENT '연락처',
    cust_email VARCHAR(255) NOT NULL COMMENT '이메일',
    cust_birth VARCHAR(10) NOT NULL COMMENT '생년월일',
    cust_crd_point INT NOT NULL COMMENT '신용점수',
    cust_crt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
    cust_ssn VARCHAR(20) NOT NULL COMMENT '주민번호',
    cust_cd VARCHAR(30) NOT NULL COMMENT '고객등급 (BASIC(일반), SILVER, GOLD, VIP)',
    cust_tp VARCHAR(30) NOT NULL COMMENT '고객유형 (PERSONAL(개인), BUSINESS(개인사업자), CORPORATE(법인사업자))',
    cust_marketing_yn CHAR(1) NOT NULL COMMENT '마케팅 동의여부 (Y/N)',
    PRIMARY KEY (cust_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='고객';


### 질문
CREATE문을 가지고 entity를 만들어줘.








CREATE TABLE employees (
    emp_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '직원 ID',
    bran_id BIGINT NOT NULL COMMENT '지점 ID',
    emp_email VARCHAR(255) NOT NULL COMMENT '이메일',
    emp_nm VARCHAR(50) NOT NULL COMMENT '직원명',
    emp_phone VARCHAR(20) NOT NULL COMMENT '직원 연락처',
    emp_hire_date DATE NOT NULL COMMENT '입사 일자',
    emp_resignation_at DATE NULL COMMENT '퇴사 일자',
    emp_position_name VARCHAR(30) NOT NULL COMMENT '직급명',
    emp_crt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    emp_upt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (emp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='직원';














CREATE TABLE card (
    card_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '신용/체크 카드(상품) ID',
    card_nm VARCHAR(11) NOT NULL COMMENT '카드명',
    card_annual_fee INT NULL COMMENT '제휴연회비',
    card_tp INT NOT NULL COMMENT '카드종류 (0: 신용, 1: 체크)',
    card_min_require INT NULL COMMENT '최소요구실적',
    card_sale_yn CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '판매상태 (Y: 판매중, N: 판매중지)',
    card_st_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '출시일',
    card_ed_at DATETIME NULL COMMENT '종료일',
    PRIMARY KEY (card_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='신용/체크 카드(상품)';











CREATE TABLE account (
    acct_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '계좌 ID',
    cust_id BIGINT NOT NULL COMMENT '고객 ID',
    acct_tp TINYINT NOT NULL DEFAULT 0 COMMENT '계좌 타입 (0: 일반, 1: 내부)',
    acct_sav_tp VARCHAR(30) NOT NULL COMMENT '이체 여부 (BOTH, DEPOSIT_ONLY, WITHDRAW_ONLY)',
    acct_num VARCHAR(20) NOT NULL COMMENT '계좌 번호',
    acct_pw VARCHAR(4) NULL COMMENT '비밀번호 (암호화 필요)',
    acct_bal BIGINT NOT NULL DEFAULT 0 COMMENT '보유 잔액',
    acct_day_limit BIGINT NOT NULL COMMENT '일일 출금 한도',
    acct_sts_cd VARCHAR(30) NOT NULL COMMENT '계좌 상태',
    acct_crt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    acct_is_ded_yn CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '요구불 여부 (Y/N)',
    PRIMARY KEY (acct_id),
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='계좌';





CREATE TABLE user_card (
    card_user_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '카드 ID',
    card_id BIGINT NOT NULL COMMENT '카드상품 ID',
    cust_id BIGINT NOT NULL COMMENT '고객 ID',
    emp_id BIGINT NOT NULL COMMENT '직원 ID',
    card_num VARCHAR(30) NOT NULL COMMENT '카드번호',
    card_sts VARCHAR(30) NOT NULL COMMENT '카드상태 (정상, 거래정지, 분실, 만료, 해지 등)',
    card_day_limit INT NOT NULL COMMENT '일일한도',
    card_month_limit INT NOT NULL COMMENT '월간한도',
    card_crt_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    card_ed_at DATETIME NOT NULL COMMENT '만료일',
    card_deac_at DATETIME NULL COMMENT '비활성일시 (활성화 시 다시 NULL)',
    PRIMARY KEY (card_user_id),
    FOREIGN KEY (card_id) REFERENCES card(card_id),
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 보유카드';




CREATE TABLE credit_card (
    card_user_id     BIGINT       NOT NULL COMMENT '카드 ID / 사용자 ID',
    card_bank_code   VARCHAR(5)   NOT NULL COMMENT '은행코드',
    card_account_id  VARCHAR(30)  NOT NULL COMMENT '실사용 계좌',
    card_due_day     TINYINT      NOT NULL COMMENT '결제 예정 날짜',

    PRIMARY KEY (card_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='신용카드';


CREATE TABLE check_card (
    card_user_id BIGINT NOT NULL COMMENT '카드 ID',
    card_acct_id BIGINT NOT NULL COMMENT '계좌 ID',

    PRIMARY KEY (card_user_id),
    CONSTRAINT fk_check_card_user
        FOREIGN KEY (card_user_id) REFERENCES user_card(card_user_id),
    CONSTRAINT fk_check_card_account
        FOREIGN KEY (card_acct_id) REFERENCES account(acct_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='체크카드';


CREATE TABLE credit_card_statement (
    card_crd_statement_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '명세서 ID',
    card_user_id          BIGINT NOT NULL COMMENT '카드 ID',
    card_place            VARCHAR(11) NOT NULL COMMENT '사용처',
    card_crd_refund_yn    CHAR(1) NOT NULL DEFAULT 'N' COMMENT '환불여부 (Y/N)',
    card_og_amt           INT NOT NULL COMMENT '원금액',
    card_installments     INT NULL DEFAULT 1 COMMENT '할부기간 (1초과시 할부)',
    card_trns_dt          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용일자',
    card_refunded_at      DATETIME NULL COMMENT '환불일시',

    PRIMARY KEY (card_crd_statement_id),
    CONSTRAINT fk_credit_card_user
        FOREIGN KEY (card_user_id) REFERENCES user_card(card_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='신용카드 명세서 (한 번 결제 시마다 내역)';


CREATE TABLE card_installment_schedule (
    card_installment_schedule_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '할부 ID',
    card_crd_statement_id        BIGINT NOT NULL COMMENT '명세서 ID',
    card_month_no                INT NOT NULL COMMENT '할부회차',
    card_installment_amt         INT NOT NULL COMMENT '할부 금액',
    card_due_at                  DATETIME NOT NULL COMMENT '결제예정일',
    card_crt_at                  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    card_schedule_refund_yn      CHAR(1) NOT NULL DEFAULT 'N' COMMENT '환불여부',

    PRIMARY KEY (card_installment_schedule_id),
    CONSTRAINT fk_card_installment_statement
        FOREIGN KEY (card_crd_statement_id) REFERENCES credit_card_statement(card_crd_statement_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='카드 할부 스케줄';






CREATE TABLE card_billing (
                              card_billing_id      BIGINT NOT NULL AUTO_INCREMENT COMMENT '청구서 ID',
                              card_user_id         BIGINT NOT NULL COMMENT '카드 ID',
                              card_billing_year_month DATE NOT NULL COMMENT '청구연월',
                              card_installment_amt INT NOT NULL DEFAULT 0 COMMENT '결제할 할부금',
                              card_new_charges     INT NOT NULL DEFAULT 0 COMMENT '신규사용액',
                              card_total_due       INT NOT NULL DEFAULT 0 COMMENT '최종 청구액',
                              card_paid_amt        INT NOT NULL DEFAULT 0 COMMENT '납부한 금액',
                              card_due_date        DATETIME DEFAULT NULL COMMENT '납부 마감일',
                              card_billing_sts     VARCHAR(5) NOT NULL COMMENT '청구 상태 (GENERATED / BILLED / PAID / OVERDUE / CLOSED)',

                              PRIMARY KEY (card_billing_id),
                              CONSTRAINT fk_card_billing_user
                                  FOREIGN KEY (card_user_id)
                                      REFERENCES user_card (card_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='월별 청구서';