package com.bankfarm_dummy.bankfarm_dummy.card;


import com.bankfarm_dummy.bankfarm_dummy.JpaDummy;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CardBilling;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CardInstallmentSchedule;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CreditCardStatement;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CardBillingRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CardInstallmentScheduleRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CreditCardStatementRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankCardBillingDummy extends JpaDummy {

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

        List<CardBilling> newBillings = new ArrayList<>();
        List<CardBilling> updatedBillings = new ArrayList<>();

        for(CreditCardStatement cs : crdCardStmList){
            Long cardUserId = cs.getUserCard().getCardUserId();
            LocalDate billingYearMonth = YearMonth.from(cs.getCardTrnsDt()).atDay(1);

            Optional<CardBilling> existingBillingOpt = cardBillingRepository
                    .findByUserCard_CardUserIdAndCardBillingYearMonth(cardUserId, billingYearMonth);

            CardBilling billing = existingBillingOpt.orElse(null);

            if (billing == null) {
                billing = CardBilling.builder()
                        .userCard(cs.getUserCard())
                        .cardBillingYearMonth(billingYearMonth)
                        .cardInstallmentAmt(0)
                        .cardNewCharges(0)
                        .cardTotalDue(0)
                        .cardPaidAmt(0)
                        .cardBillingSts(faker.options().option("CD026","CD027","CD028","CD029","CD030"))
                        .cardDueDate(cs.getCardTrnsDt().plusDays(20))
                        .build();

                newBillings.add(billing);
            }

            // ✅ 일시불 거래인 경우 금액 추가
            if (cs.getCardInstallments() == 1) {
                billing.setCardNewCharges(billing.getCardNewCharges() + cs.getCardOgAmt());
                billing.setCardTotalDue(billing.getCardTotalDue() + cs.getCardOgAmt());
            }

            // 기존 billing이었으면 업데이트 목록에 추가
            if (existingBillingOpt.isPresent() && !updatedBillings.contains(billing)) {
                updatedBillings.add(billing);
            }


            // ✅ 새로 생성된 billing 한 번에 insert
            if (!newBillings.isEmpty()) {
                cardBillingRepository.saveAll(newBillings);
            }

            // ✅ 기존 billing 변경사항 한 번에 update
            if (!updatedBillings.isEmpty()) {
                cardBillingRepository.saveAll(updatedBillings);
            }

        }
        cardBillingRepository.flush();
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
