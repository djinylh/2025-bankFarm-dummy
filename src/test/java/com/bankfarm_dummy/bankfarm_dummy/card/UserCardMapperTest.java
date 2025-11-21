package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.CreditCardReq;
import com.bankfarm_dummy.bankfarm_dummy.card.model.UserCardGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.UserCardReq;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.ProdDocumentMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserCardMapperTest extends Dummy {

    @Test
    void join() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        UserCardMapper userCardMapper = sqlSession.getMapper(UserCardMapper.class);
        ProdDocumentMapper prodDocumentMapper = sqlSession.getMapper(ProdDocumentMapper.class);
        CreditCardMapper creditCardMapper = sqlSession.getMapper(CreditCardMapper.class);

        UserCardReq req = new UserCardReq();

        long cardId = (long)(Math.random()*40)+1;
        req.setCardId(cardId);
        long custId = (long)(Math.random()*100000)+1;
        req.setCustId(custId);
        long empId = (long)(Math.random()*51023)+1;
        req.setEmpId(empId);

        Random red = new Random();
        StringBuilder sb = new  StringBuilder();
        for(int k = 0; k < 16; k++){
            sb.append(red.nextInt(10));
            if(k==3||k==7||k==11){sb.append("-");}
        }
        req.setCardNum(sb.toString());
        String[] cardSts = {"CD005","CD006","CD007","CD008","CD009"};
        int cardIdx = (int)(Math.random()*cardSts.length);
        req.setCardSts(cardSts[cardIdx]);
        int[] cardDayLimit = {1000000,3000000,5000000};
        int cardLimitIdx = (int)(Math.random()*cardDayLimit.length);
        req.setCardDayLimit(cardDayLimit[cardLimitIdx]);
        int[] cardMonthLimit = {5000000,7000000,10000000};
        int cardMonthLimitIdx = (int)(Math.random()*cardMonthLimit.length);
        req.setCardMonthLimit(cardMonthLimit[cardMonthLimitIdx]);
        int number = (int)(Math.random()*10);

        int mon = (int)(Math.random()*12)+1;
        String reMon = String.valueOf(mon);

        if(mon <10){
            reMon = "0" + mon;
        }

        int day = (int)(Math.random()*30)+1;
        String reDay = String.valueOf(day);

        if(day<10){
            reDay = "0" + day;
        }





        String date = "203"+number+"-"+ reMon + "-"+ reDay;
        LocalDate cardEdDate = LocalDate.parse(date);

        req.setCardEdAt(cardEdDate);
        userCardMapper.userCardJoin(req);
        sqlSession.flushStatements();

        CreditCardReq creditCardReq = new CreditCardReq();
        creditCardReq.setCardUserId(req.getCardUserId());
        String[] bankCode = {"BK001","BK002","BK003","BK004","BK005"
                            ,"BK006","BK007","BK008","BK009","BK010"
                            ,"BK011","BK012","BK013","BK014","BK015"
                            ,"BK016","BK017","BK018","BK019","BK020"};
        int bankCodeIdx = (int)(Math.random()*bankCode.length);
        creditCardReq.setCardBankCode(bankCode[bankCodeIdx]);
        Random red3 = new Random();
        StringBuilder sb3 = new StringBuilder();
        for(int k = 0; k < 14; k++){
            sb3.append(red3.nextInt(10));
            if(k==2||k==8){sb3.append("-");}
        }
        creditCardReq.setCardAccountId(sb3.toString());
        int dueDay = (int)(Math.random()*30)+1;
        byte one = (byte) dueDay;
        creditCardReq.setCardDueDay(one);
        creditCardMapper.creditCardJoin(creditCardReq);




    }
}
