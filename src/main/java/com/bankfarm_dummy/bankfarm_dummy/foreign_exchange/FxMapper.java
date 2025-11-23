package com.bankfarm_dummy.bankfarm_dummy.foreign_exchange;

import com.bankfarm_dummy.bankfarm_dummy.foreign_exchange.medel.FxRtHistoryReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FxMapper {
    int insertFxRateHistory(FxRtHistoryReq fxRtHistoryReq);

    List<String> selectActiveCurrencies();
}
