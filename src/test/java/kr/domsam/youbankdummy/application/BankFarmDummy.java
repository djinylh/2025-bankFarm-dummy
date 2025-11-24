package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.Dummy;
import kr.domsam.youbankdummy.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankFarmDummy extends Dummy {
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
    void insOneByOne() {
        int empIdx = 0;
        int count = 0;
        for(Customer2 c : customerList2) {

            Employees assignedEmp = employeeList.get(empIdx);
            Card assignedCard = cardList.get(faker.random().nextInt(cardList.size()));
            UserCard uc = generateUserCard(c,assignedCard,assignedEmp);
            userCardRepository.save(uc);
            if(assignedCard.getCardTp()==1) {
                insCreditCard(uc);
            }else if (assignedCard.getCardTp()==0){
                insCheckCard(uc);
            }
            empIdx = (empIdx + 1) % employeeList.size();

            count++;
            if(count>=50000) break;
        }
        userCardRepository.flush();
    }


    void insCheckCard(UserCard uc) {
        Account a = accountList.get(faker.random().nextInt(accountList.size()));
        CheckCard checkCard = generateCheckCard(uc,a);
        checkCardRepository.save(checkCard);
        checkCardRepository.flush();
    }

    CheckCard generateCheckCard(UserCard uc, Account a) {
        return CheckCard.builder()
                .cardUserId(uc.getCardUserId())
                .account(a)
                .build();
    }

    void insCreditCard(UserCard uc) {
        CreditCard creditCard = generateCreditCard(uc);
        creditCardRepository.save(creditCard);
        creditCardRepository.flush();
    }

    CreditCard generateCreditCard(UserCard uc) {
        return CreditCard.builder()
                .cardUserId(uc.getCardUserId())
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
