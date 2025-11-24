package kr.domsam.youbankdummy.application;


import kr.domsam.youbankdummy.Dummy;
import kr.domsam.youbankdummy.entity.BankAccount;
import kr.domsam.youbankdummy.entity.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountDummy extends Dummy {

    @Autowired CustomerRepository customerRepository;
    @Autowired BankAccountRepository bankAccountRepository;
    List<Customer> customerList;

    @BeforeAll
    void beforeAll() {
        customerList = customerRepository.findAll();
    }


    @Test
    @Rollback(false)
    void insOneByOne() {
        for(Customer c : customerList) {
            BankAccount ba = generateBankAccount(c);
            bankAccountRepository.save(ba);
            System.out.println(ba);
        }
        bankAccountRepository.flush();
    }

    BankAccount generateBankAccount(Customer c) {
        return BankAccount.builder()
                .accountNo(generateAccountNo())
                .customer(c)
                .accountPassword(faker.number().digits(6))
                .bankCode(faker.number().digits(3))             // 은행코드는 3자리 숫자로 예시
                .accountName(faker.finance().iban())            // 계좌명 → IBAN 등 랜덤 금융 문자열
                .depositorName(c.getCustName())        // 예금주명

                // 잔액
                .balance(new BigDecimal(faker.number().numberBetween(0, 999999999))) // 최대 9억

                // 날짜
                .establishedDate(randomDatePast())              // 개설일
                .maturityDate(randomDateFuture())               // 만기일
                .cancelDate(null)                               // 기본 NULL

                // 타입/코드
                .accountType(faker.options().option("01", "02", "03", "04")) // 계좌타입
                .limitAmount(BigDecimal.valueOf(faker.number().numberBetween(0, 10000000))) // 한도
                .manageBranchId(faker.number().digits(4))       // 4자리 지점 ID

                // 사용 여부
                .useYn("Y")
                .virtualAccountYn(faker.bool().bool() ? "Y" : "N")

                // 비고
                .remark(faker.lorem().sentence())

                // 등록 정보
                .regtGubun("3")
                .regtId("SYS")
                .regtDatetime(LocalDateTime.now())
                .mdfId(null)
                .mdfDatetime(null)
                .build();
    }

    @Test
    @Rollback(false)
    void insAddAccount() {
        final int SIZE = 100;

        for(int i=0; i<SIZE; i++) {
            int randomIndex = (int) (Math.random() * customerList.size());
            Customer c = customerList.get(randomIndex);
//            Customer c = customerList.get(  faker.random().nextInt(customerList.size())  );
            BankAccount ba = generateBankAccount(c);
            bankAccountRepository.save(ba);
            System.out.println(ba);
        }
        bankAccountRepository.flush();
    }

    // 계좌번호 (20자리 숫자)
    private static String generateAccountNo() {
        return faker.number().digits(20);
    }
    // 과거 임의 날짜
    private static LocalDateTime randomDatePast() {
        return LocalDateTime.now().minusDays(
                ThreadLocalRandom.current().nextInt(30, 3000)
        );
    }
    // 미래 임의 날짜
    private static LocalDateTime randomDateFuture() {
        return LocalDateTime.now().plusDays(
                ThreadLocalRandom.current().nextInt(30, 3000)
        );
    }
}
