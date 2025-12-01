package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange;

import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxRtHistoryReq;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FxMapper {
    int insertFxRateHistory(FxRtHistoryReq fxRtHistoryReq);

    List<String> selectActiveCurrencies();

    Long selectLatestFxRateByCurrencyAndDate(String currencyId, LocalDateTime date);
}
