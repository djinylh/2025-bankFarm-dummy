package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionParamReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardOptionParamMapperTest extends Dummy {


    final int ADD_ROW_COUNT= 20;

    @Test
    void join() {

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        CardOptionParamMapper cardOptionParamMapper = sqlSession.getMapper(CardOptionParamMapper.class);

        for (int i=0;i<ADD_ROW_COUNT;i++) {

            List<CardOptionGetRes> res = cardOptionParamMapper.getListByOption();

            



            sqlSession.flushStatements();

        }


    }

}