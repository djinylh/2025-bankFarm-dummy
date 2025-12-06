package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange;

import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxCurrencyExchangeReq;
import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxRtHistoryReq;
import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.model.FxRtHistoryRes;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FxMapper {
    int insertFxRateHistory(FxRtHistoryReq fxRtHistoryReq);

    List<String> selectActiveCurrencies();

    List<Long> selectCustomerIds();
    List<Long> selectEmployeeIds();
    List<Long> selectAccountIds();
    List<FxRtHistoryRes> selectAllFxRateHistory();

    int insertCurrencyExchange(FxCurrencyExchangeReq req);
}
