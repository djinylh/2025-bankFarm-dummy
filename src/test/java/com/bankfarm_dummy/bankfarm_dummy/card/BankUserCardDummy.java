package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.JpaDummy;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.*;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.account.AccountRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CardRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CheckCardRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CreditCardRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.UserCardRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.customer.CustomerRepository2;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.employees.EmployeesRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankUserCardDummy extends JpaDummy {
    @Autowired
    CustomerRepository2 customerRepository2;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    UserCardRepository userCardRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    CheckCardRepository checkCardRepository;

    List<Customer2> customerList2;
    List<Account> accountList;
    List<Card> cardList;
    List<Employees> employeeList;

    @BeforeAll
    void beforeAll() {

        customerList2 = customerRepository2.findAll();
        accountList = accountRepository.findAll();
        cardList = cardRepository.findAll();
        employeeList = employeesRepository.findAll();
    }


    @Test
    @Rollback(false)
    void insCards() {
        int SIZE = 310100;
        int BATCH_SIZE = 100;  // flush 주기
        int empIdx = 0;

        List<UserCard> ucList = new ArrayList<>();
        List<CheckCard> checkCardList = new ArrayList<>();
        List<CreditCard> creditCardList = new ArrayList<>();

        List<Customer2> shuffledList = new ArrayList<>(customerList2);
        Collections.shuffle(shuffledList);
        List<Account> shuffledAccounts = new ArrayList<>(accountList);
        Collections.shuffle(shuffledAccounts);

        int total = SIZE;
        int processed = 0;

        for (int i = 0; i < SIZE; i++) {
            // ✅ 고객 1명당 1장의 카드만 생성
            Customer2 c = shuffledList.get(i % shuffledList.size());
            Employees assignedEmp = employeeList.get(empIdx);
            Card assignedCard = cardList.get(faker.random().nextInt(cardList.size()));
            UserCard uc = generateUserCard(c, assignedCard, assignedEmp);
            ucList.add(uc);

            if (assignedCard.getCardTp() == 1) {
                Account a = shuffledAccounts.get(i % shuffledAccounts.size());
                checkCardList.add(generateCheckCard(uc, a));
            } else if (assignedCard.getCardTp() == 0) {
                creditCardList.add(generateCreditCard(uc));
            }

            empIdx = (empIdx + 1) % employeeList.size();
            processed++;

            // ✅ 100개마다 batch 저장
            if (processed % BATCH_SIZE == 0 || processed == SIZE) {
                userCardRepository.saveAll(ucList);
                checkCardRepository.saveAll(checkCardList);
                creditCardRepository.saveAll(creditCardList);

                userCardRepository.flush();
                checkCardRepository.flush();
                creditCardRepository.flush();

                ucList.clear();
                checkCardList.clear();
                creditCardList.clear();

                printProgress(processed, total);
            }
        }
    }

    private void printProgress(int current, int total) {
        int percent = (current * 100) / total;
        System.out.printf("진행률: %3d%% (%d / %d)%n", percent, current, total);
        System.out.flush(); // 즉시 콘솔 반영
    }


    CheckCard generateCheckCard(UserCard uc, Account a) {
        return CheckCard.builder()
                .userCard(uc)
                .account(a)
                .build();
    }


    CreditCard generateCreditCard(UserCard uc) {
        return CreditCard.builder()
                .userCard(uc)
                .cardAcctId(generateAccountNo())
                .cardDueDay(generateDueDay())
                .cardBankCode(faker.options().option("BK001","BK002","BK003","BK004","BK005",
                        "BK006","BK007","BK008","BK009","BK010",
                        "BK011","BK012","BK013","BK014","BK015",
                        "BK016","BK017","BK018","BK019","BK020"))
                .build();
    }


    UserCard generateUserCard(Customer2 customer2,
                              Card card,
                              Employees employees) {
        return UserCard.builder()
                .card(card)
                .employee(employees)
                .customer2(customer2)
                .cardNum(generateCardNo())
                .cardSts(faker.options().option("CD006","CD007","CD008","CD009","CD010"))
                .cardDayLimit(faker.options().option(300000,500000,1000000,1500000))
                .cardMonthLimit(faker.options().option(1000000,1500000,2000000,3000000))
                .cardEdAt(randomDateFuture())
                .cardDeacAt(null)
                .build();


    }


    private static String generateCardNo() {
        return faker.number().digits(16);
    }

    private static String generateAccountNo() {
        return faker.number().digits(14);
    }


    private static byte generateDueDay() {
        return (byte) faker.number().numberBetween(1, 31);
    }

    private static LocalDateTime randomDateFuture() {
        return LocalDateTime.now().plusDays(
                ThreadLocalRandom.current().nextInt(30, 3000)
        );
    }








}
