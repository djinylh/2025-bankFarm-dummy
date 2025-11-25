package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.Dummy;
import kr.domsam.youbankdummy.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardStatmentDummy extends Dummy {

    @Autowired
    UserCardRepository userCardRepository;
    @Autowired
    CreditCardStatementRepository cardStatementRepository;
    @Autowired
    CardInstallmentScheduleRepository cardInstallmentScheduleRepository;

    List<UserCard> userCardList;
    List<CreditCardStatement> crdCardStmList;


    @BeforeAll
    void beforeAll() {
        userCardList = userCardRepository.findAll();
        crdCardStmList = cardStatementRepository.findAll();

    }

    @Test
    @Rollback(false)
    @Transactional
    void insCardTrns() {
        int SIZE = 100000; //1000개 사용자 카드중에
        for (int i = 0; i < SIZE; i++) {
            int randomIndex = (int) (Math.random() * userCardList.size());
            UserCard uc = userCardList.get(randomIndex);
            // 신용카드인 카드 명세서 발급(반 정도)
            if (uc.getCard().getCardTp() == 0) {
                insCrdCardStm(uc);
            }
        }
        // 신용카드명세서들의 할부스케줄 생성
        insInstmSchd();
    }


    void insInstmSchd() {
        Set<Long> existIds = cardInstallmentScheduleRepository
                .findAllCreditCardStatementIds();
        for(CreditCardStatement cs : crdCardStmList ) {
            if (cs.getCardCrdRefundYn().equals("N") && !existIds.contains(cs.getCardCrdStatementId())) {
                int monthNo = cs.getCardInstallments();
                List<CardInstallmentSchedule> batch = new ArrayList<>();
                for (int n = 1; n <= monthNo; n++) {
                    batch.add(generateCis(cs, n));
                }
                cardInstallmentScheduleRepository.saveAll(batch);
            }
        }
        cardInstallmentScheduleRepository.flush();
    }

    CardInstallmentSchedule generateCis(CreditCardStatement cs,int n){
        int istmAmt= cs.getCardOgAmt() / cs.getCardInstallments();
        LocalDateTime trnsDate = cs.getCardTrnsDt();
        boolean isRefund = (cs.getCardCrdRefundYn().equals("Y"));
        String refundYn = "N";
        if(isRefund){
            refundYn="Y";
        }
        return CardInstallmentSchedule.builder()
                .creditCardStatement(cs)
                .cardMonthNo(n)
                .cardInstallmentAmt(istmAmt)
                .cardDueAt(trnsDate.plusMonths(n))
                .cardScheduleRefundYn(refundYn)
                .build();
    }

    void insCrdCardStm(UserCard uc) {
        List<CreditCardStatement> statementList = new ArrayList<>();

        CreditCardStatement cs = generateCrdCardStm(uc);
        statementList.add(cs);
        cardStatementRepository.saveAll(statementList);
        cardStatementRepository.flush();
    }

    CreditCardStatement generateCrdCardStm(UserCard uc){
        return CreditCardStatement.builder()
                .userCard(uc)
                .cardPlace(faker.options().option("스타벅스",
                        "투썸플레이스", "이디야커피", "엔제리너스", "커피빈",
                        "롯데리아", "버거킹", "맥도날드", "KFC", "피자헛",
                        "도미노피자", "파파존스", "CU", "GS25", "세븐일레븐",
                        "이마트24", "이마트", "롯데마트", "홈플러스", "코스트코"))
                .cardCrdRefundYn(faker.options().option("Y","N","N","N","N","N","N","N","N","N"))
                .cardOgAmt(faker.options().option(100000,300000,600000,1000000,500000,2000000,3000000,4000000))
                .cardInstallments(faker.options().option(1,2,3,4,6,7,8,10,12,15,18,20,24,36))
                .cardTrnsDt(randomDatePast())
                .build();
    }


    private static LocalDateTime randomDatePast() {
        return LocalDateTime.now().minusDays(
                ThreadLocalRandom.current().nextInt(30, 3000)
        );
    }

    private static LocalDateTime randomDateFuture() {
        return LocalDateTime.now().plusDays(
                ThreadLocalRandom.current().nextInt(30, 3000)
        );
    }


    }
