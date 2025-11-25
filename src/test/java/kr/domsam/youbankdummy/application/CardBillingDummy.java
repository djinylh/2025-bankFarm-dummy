package kr.domsam.youbankdummy.application;

import kr.domsam.youbankdummy.Dummy;
import kr.domsam.youbankdummy.entity.CardBilling;
import kr.domsam.youbankdummy.entity.CardInstallmentSchedule;
import kr.domsam.youbankdummy.entity.CreditCardStatement;
import kr.domsam.youbankdummy.entity.UserCard;
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
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardBillingDummy extends Dummy {

    @Autowired
    CardBillingRepository cardBillingRepository;
    @Autowired
    CreditCardStatementRepository cardStatementRepository;
    @Autowired
    CardInstallmentScheduleRepository cardInstallmentScheduleRepository;


    List<CreditCardStatement> crdCardStmList;
    List<CardInstallmentSchedule> scheduleList;

    @BeforeAll
    void beforeAll() {
        crdCardStmList = cardStatementRepository.findAll();
        scheduleList = cardInstallmentScheduleRepository.findAll();
    }

    @Test
    @Rollback(false)
    @Transactional
    void run() {
       insBilling();
       updateBilling();
    }

    void insBilling(){
        for(CreditCardStatement cs : crdCardStmList){
            Long cardUserId = cs.getUserCard().getCardUserId();
            LocalDate billingYearMonth = YearMonth.from(cs.getCardTrnsDt()).atDay(1);

            Optional<CardBilling> existingBilling = cardBillingRepository
                    .findByUserCard_CardUserIdAndCardBillingYearMonth(cardUserId, billingYearMonth);

            CardBilling billing = existingBilling.orElseGet(() -> {

                CardBilling newBilling = CardBilling.builder()
                        .userCard(cs.getUserCard())
                        .cardBillingYearMonth(billingYearMonth)
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

    void updateBilling(){
        Map<YearMonth, List<CardInstallmentSchedule>> grouped = scheduleList.stream()
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
                    boolean isBill = (billing.getCardBillingSts().equals("CD027"));

                    if (!isBill && schedule.getCardScheduleRefundYn().equals("N")) {
                        // 3️⃣ 할부금 누적 반영
                        billing.setCardInstallmentAmt(
                                billing.getCardInstallmentAmt() + schedule.getCardInstallmentAmt()
                        );

                        billing.setCardTotalDue(
                                billing.getCardNewCharges() + billing.getCardInstallmentAmt()
                        );

                        // 4️⃣ 청구 상태 변경
                        billing.setCardBillingSts("CD027");
                    }
                    //                cardBillingRepository.flush();
                }
            }
        }
    }

}
