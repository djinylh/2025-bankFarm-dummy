package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.JpaDummy;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CardBilling;
import com.bankfarm_dummy.bankfarm_dummy.jpa.entity.CreditCardStatement;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CardBillingRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CardInstallmentScheduleRepository;
import com.bankfarm_dummy.bankfarm_dummy.jpa.repository.card.CreditCardStatementRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankCardBillingDummy2 extends JpaDummy {

    @Autowired
    CardBillingRepository cardBillingRepository;
    @Autowired
    CreditCardStatementRepository cardStatementRepository;
    @Autowired
    CardInstallmentScheduleRepository cardInstallmentScheduleRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeAll
    void beforeAll() {
        System.out.println("✅ 카드 명세서 데이터 페이징 로딩 준비 완료");
    }

    @Test
    @Transactional
    @Rollback(false)
    void run() {
        insBillingPaged();    //
//        bulkUpdateBilling();  //
    }

    private LocalDateTime calculateDueDate(LocalDate billingYm) {
        return billingYm.plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();
    }

    /**
     * ✅ 페이징 단위로 청구서 생성
     */

    void insBillingPaged() {
        int page = 0;
        int size = 5000; // 💡 한번에 5,000건씩 불러오기
        Page<CreditCardStatement> pageResult;

        int totalInserted = 0;
        int globalIndex = 0;
        int start = 100001; //
        int end = 150000;

        int total = end - start + 1;
        long startTime = System.currentTimeMillis();

        do {
            pageResult = cardStatementRepository.findActiveStatements(PageRequest.of(page, size));
//            pageResult = cardStatementRepository.findAll(PageRequest.of(page, size));
            List<CreditCardStatement> stmts = pageResult.getContent();
            List<CardBilling> newBillings = new ArrayList<>();

            for (CreditCardStatement cs : stmts) {
                globalIndex++;
                if (globalIndex < start || globalIndex > end) {
                    continue;
                }

                Long cardUserId = cs.getUserCard().getCardUserId();
                LocalDate billingYm = YearMonth.from(cs.getCardTrnsDt()).plusMonths(1).atDay(1);

                Optional<CardBilling> existing = cardBillingRepository
                        .findByUserCard_CardUserIdAndCardBillingYearMonth(cardUserId, billingYm);

                if (existing.isEmpty()) {
                    CardBilling billing = CardBilling.builder()
                            .userCard(cs.getUserCard())
                            .cardBillingYearMonth(billingYm)
                            .cardInstallmentAmt(0)
                            .cardNewCharges(cs.getCardInstallments() == 1 ? cs.getCardOgAmt() : 0)
                            .cardTotalDue(cs.getCardInstallments() == 1 ? cs.getCardOgAmt() : 0)
                            .cardPaidAmt(0)
                            .cardBillingSts("CD026")
                            .cardDueDate(calculateDueDate(billingYm))
                            .build();
                    newBillings.add(billing);
                }else{
                    CardBilling billing = existing.get();

                    if (cs.getCardInstallments() == 1) {
                        billing.setCardNewCharges(billing.getCardNewCharges() + cs.getCardOgAmt());
                        billing.setCardTotalDue(billing.getCardTotalDue() + cs.getCardOgAmt());
                    } else {
                        billing.setCardInstallmentAmt(billing.getCardInstallmentAmt() + cs.getCardOgAmt());
                        billing.setCardTotalDue(billing.getCardTotalDue() + cs.getCardOgAmt());
                    }

                    // 상태 및 납부기한은 그대로 두거나 갱신
                    billing.setCardBillingSts("CD026");
                    billing.setCardDueDate(calculateDueDate(billingYm));

                    // ✅ save()로 갱신
                    cardBillingRepository.save(billing);
                }

                if ((globalIndex - start + 1) % 100 == 0 && globalIndex >= start && globalIndex <= end) {
                    printProgress(globalIndex - start + 1, total, startTime);
                }

                if (globalIndex >= end) {
                    break;
                }
            }

            if (!newBillings.isEmpty()) {
                cardBillingRepository.saveAll(newBillings);
                totalInserted += newBillings.size();
            }

            cardBillingRepository.flush();
            em.clear();   // 메모리 초기화
            System.gc();  // 테스트 환경에서만

            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            System.out.printf("%d건 삽입 완료 (현재 페이지 %d, 총 소요 %ds)%n",
                    totalInserted, page + 1, elapsed);

            page++;
            if (globalIndex >= end) break;

        } while (!pageResult.isLast());

    }

    void bulkUpdateBilling() {
        int updated = cardBillingRepository.updateBillingAmounts();
        System.out.println("✅ 청구서 일괄 업데이트 완료: " + updated + "건 반영됨");
    }

    private void printProgress(int current, int total, long startTime) {
        int percent = (int) ((current * 100L) / total);
        int barCount = percent / 2;
        String bar = "█".repeat(barCount) + "-".repeat(50 - barCount);
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;

        System.out.printf("[%s] %3d%% (%d/%d) ⏱ %ds%n", bar, percent, current, total, elapsed);
        System.out.flush(); // ✅ 즉시 콘솔로 내보내기
    }

}