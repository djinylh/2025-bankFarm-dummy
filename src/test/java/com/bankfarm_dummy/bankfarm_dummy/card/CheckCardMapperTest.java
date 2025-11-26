package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CheckCardReq;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CheckCardRes;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckCardMapperTest extends Dummy {

    @Test
    void join() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        CheckCardMapper checkCardMapper = sqlSession.getMapper(CheckCardMapper.class);
        // 체크카드 PK 리스트 알아내는 메소드
        List<CheckCardRes> checkCardResList = checkCardMapper.checkCardList();

        for(CheckCardRes res : checkCardResList){
            CheckCardReq checkCardReq = new CheckCardReq();
            checkCardReq.setCardUserId(res.getCardUserId());
            checkCardReq.setCardAcctId(res.getCardUserId());
            checkCardMapper.checkCardJoin(checkCardReq);
            sqlSession.flushStatements();
        }
        sqlSession.close();

    }

}