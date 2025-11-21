package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;

import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionDefReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardOptionDefMapperTest extends Dummy {
    final int ADD_ROW_COUNT= 4;

    @Test
    void join(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        CardOptionDefMapper cardOptionDefMapper = sqlSession.getMapper(CardOptionDefMapper.class);


        for (int i=0;i<ADD_ROW_COUNT;i++) {

            CardOptionDefReq req = new CardOptionDefReq();

            String[] optionCode = {"QUICK_PAY","SMS_ALERT","TRANSIT","OVERSEAS"};

            String[] optionName = {"간편결제기능","문자알림기능","교통카드기능","해외결제기능"};

            req.setCardOptionCode(optionCode[i]);
            req.setCardOptionName(optionName[i]);

            cardOptionDefMapper.cardOptionDef(req);

            sqlSession.flushStatements();

        }

    }




}