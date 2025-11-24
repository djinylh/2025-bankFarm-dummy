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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardStatmentDummy extends Dummy {

    @Autowired
    UserCardRepository userCardRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CreditCardStatementRepository cardStatementRepository;
    @Autowired
    CardInstallmentScheduleRepository cardInstallmentScheduleRepository;

    @Autowired
    CardBillingRepository cardBillingRepository;

    List<UserCard> userCardList;
    List<Card> cardList;
    List<CreditCardStatement> crdCardStmList;
    List<CardInstallmentSchedule> schedules;
    List<CardBilling> cardBillingList;

    @BeforeAll
    void beforeAll() {
        userCardList = userCardRepository.findAll();
        cardList = cardRepository.findAll();
        crdCardStmList = cardStatementRepository.findAll();
        schedules = cardInstallmentScheduleRepository.findAll();
        cardBillingList = cardBillingRepository.findAll();
    }

    @Test
    @Rollback(false)
    @Transactional
    void insOneByOne() {

//        new CheckCard();

//        int count=0;
//        for( UserCard uc: userCardList){
//            if(uc.getCard().getCardTp()==0){
//                insCrdCardStm(uc);
//                insInstmSchd();
//            }
//            count++;
//            if(count>=100){
//                break;
//            }
//        }
        insCardBillingGen();
        insCardBilling();
    }

    void insCardBillingGen(){
        for(CreditCardStatement cs : crdCardStmList){
            Long cardUserId = cs.getUserCard().getCardUserId();
            LocalDate billingYm2 = YearMonth.from(cs.getCardTrnsDt()).atDay(1);

            Optional<CardBilling> existingBilling = cardBillingRepository
                    .findByUserCard_CardUserIdAndCardBillingYearMonth(cardUserId, billingYm2);

            CardBilling billing = existingBilling.orElseGet(() -> {

                CardBilling newBilling = CardBilling.builder()
                        .userCard(cs.getUserCard())
                        .cardBillingYearMonth(billingYm2)
                        .cardInstallmentAmt(0)
                        .cardNewCharges(0)
                        .cardTotalDue(0)
                        .cardPaidAmt(0)
                        .cardBillingSts(faker.options().option("CD026","CD027","CD028","CD029","CD030"))
                        .cardDueDate(LocalDateTime.now().plusDays(20))
                        .build();
                return cardBillingRepository.save(newBilling);
            });
            if (cs.getCardInstallments() == 1) {  // 일시불만
                billing.setCardNewCharges(billing.getCardNewCharges() + cs.getCardOgAmt());
                billing.setCardTotalDue(billing.getCardTotalDue() + cs.getCardOgAmt());
            }

        }

    }


    @Transactional
    void insCardBilling() {

        Map<YearMonth, List<CardInstallmentSchedule>> grouped = schedules.stream()
                .collect(Collectors.groupingBy(s -> YearMonth.from(s.getCardDueAt())));

        for (YearMonth ym : grouped.keySet()) {
            List<CardInstallmentSchedule> monthSchedules = grouped.get(ym);

            for (CardInstallmentSchedule schedule : monthSchedules) {
                long cardUserId = schedule.getCreditCardStatement()
                        .getUserCard()
                        .getCardUserId();

                LocalDate billingYm = ym.atDay(1);

                Optional<CardBilling> optBilling = cardBillingRepository
                        .findByUserCard_CardUserIdAndCardBillingYearMonth(cardUserId, billingYm);

                if (optBilling.isPresent()) {
                    CardBilling billing = optBilling.get();
                    boolean isBill = (billing.getCardBillingSts().equals("BILLED"));

                    if (!isBill && schedule.getCardScheduleRefundYn().equals("N")) {
                        // 3️⃣ 할부금 누적 반영
                        billing.setCardInstallmentAmt(
                                billing.getCardInstallmentAmt() + schedule.getCardInstallmentAmt()
                        );

                        billing.setCardTotalDue(
                                billing.getCardNewCharges() + billing.getCardInstallmentAmt()
                        );

                        // 4️⃣ 청구 상태 변경
                        billing.setCardBillingSts("BILLED");
                    }
                    //                cardBillingRepository.flush();
                }
            }
        }
    }





    void insInstmSchd(){
        for(CreditCardStatement cs:crdCardStmList){
            int monthNo = cs.getCardInstallments();
            if(cs.getCardCrdRefundYn().equals("N")) {
                if (cardInstallmentScheduleRepository.existsByCreditCardStatement(cs)) continue;
                for (int i = 1; i <= monthNo; i++) {
                    CardInstallmentSchedule cis = generateCis(cs,i);
                    cardInstallmentScheduleRepository.save(cis);
                }
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

        CreditCardStatement crdCardStm = generateCrdCardStm(uc);
        cardStatementRepository.save(crdCardStm);
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
