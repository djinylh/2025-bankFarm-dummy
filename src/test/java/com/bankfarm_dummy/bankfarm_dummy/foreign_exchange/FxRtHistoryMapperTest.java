package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.medel.FxRtHistoryReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FxRtHistoryMapperTest extends Dummy {
    @Test
    void insertFxRate() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        FxMapper fxMapper = sqlSession.getMapper(FxMapper.class);

        // 활성화된 통화 코드 가져오기
        List<String> currencyList = fxMapper.selectActiveCurrencies();

        Random rnd = new Random();

        // 통화별 기본 시작 환율
        Map<String, double[]> fxRanges = new HashMap<>();

        fxRanges.put("USD", new double[]{1200, 1500});
        fxRanges.put("JPY", new double[]{900, 1200});
        fxRanges.put("CNY", new double[]{160, 210});
        fxRanges.put("EUR", new double[]{1300, 1600});
        fxRanges.put("GBP", new double[]{1500, 2000});
        fxRanges.put("AUD", new double[]{750, 950});
        fxRanges.put("CAD", new double[]{900, 1100});
        fxRanges.put("HKD", new double[]{140, 200});
        fxRanges.put("SGD", new double[]{900, 1100});
        fxRanges.put("CHF", new double[]{1400, 1650});
        fxRanges.put("THB", new double[]{30, 45});
        fxRanges.put("TWD", new double[]{35, 45});
        fxRanges.put("PHP", new double[]{20, 30});

        LocalDateTime today9 = LocalDate.now().atTime(9, 0);

        for (String cur : currencyList) {

            double min = fxRanges.get(cur)[0];
            double max = fxRanges.get(cur)[1];

            // 시작 환율
            double baseRate = min + rnd.nextDouble() * (max - min);

            // 일수 30일 더미
            for (int i = 0; i < 30; i++) {

                // 전날 대비 ±0.5% 변동
                double rate = baseRate * (0.995 + rnd.nextDouble() * 0.01);

                // 소수점 4자리
                BigDecimal bdRate = BigDecimal.valueOf(rate)
                        .setScale(4, RoundingMode.HALF_UP);

                // 수수료 0.5% ~ 2%
                BigDecimal commission = BigDecimal.valueOf(0.005 + rnd.nextDouble() * 0.015)
                        .setScale(4, RoundingMode.HALF_UP);

                FxRtHistoryReq req = new FxRtHistoryReq();
                req.setFxCurrencyId(cur);
                req.setFxChargeRt(bdRate);
                req.setFxCommission(commission);
                req.setFxCrtAt(today9.minusDays(30 - i));

                fxMapper.insertFxRateHistory(req);

                // 다음날 환율이 전날을 기준으로 오르내리게
                baseRate = rate;
            }

            sqlSession.flushStatements();
        }

        sqlSession.commit();
        sqlSession.close();
    }

}
