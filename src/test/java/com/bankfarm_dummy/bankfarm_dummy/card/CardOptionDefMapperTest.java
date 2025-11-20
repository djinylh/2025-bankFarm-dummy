//package com.bankfarm_dummy.bankfarm_dummy.card;
//
//import com.bankfarm_dummy.bankfarm_dummy.Dummy;
//import com.bankfarm_dummy.bankfarm_dummy.card.model.CardOptionDefReq;
//import com.bankfarm_dummy.bankfarm_dummy.card.model.CardReq;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CardOptionDefMapperTest extends Dummy {
//    final int ADD_ROW_COUNT= 10;
//
//    @Test
//    void join(){
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
//
//        // 맵퍼 주소값 얻기
//        CardMapper cardMapper = sqlSession.getMapper(CardMapper.class);
//
//
//        for (int i=0;i<ADD_ROW_COUNT;i++) {
//
//            CardOptionDefReq req = new CardOptionDefReq();
//
//            String[] firstName = {"하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉", "열"};
//
//            for (int j = 0; j < 4; j++) {
//                String[] secondName = {"어제", "오늘", "내일", "모레"};
//                String cardName = firstName[i] + secondName[j] + "체크" + "카드";
//                CardReq req = new CardReq();
//                req.setCardNm(cardName);
//
//                int[] annualFee = {5000, 10000, 20000};
//
//                int feeIdx = (int) (Math.random() * annualFee.length);
//                req.setCardAnnualFee(annualFee[feeIdx]);
//
//                int[] tpCard = {0, 1};
//
//                int tpIdx = (int) (Math.random() * tpCard.length);
//                req.setCardAnnualFee(tpCard[tpIdx]);
//
//                int[] minRequire = {100000, 150000, 200000, 250000, 300000};
//
//                int minRequireIdx = (int) (Math.random() * minRequire.length);
//                req.setCardAnnualFee(minRequire[minRequireIdx]);
//
//                char[] saleYn = {'Y', 'N' };
//                int saleYnIdx = (int) (Math.random() * saleYn.length);
//                req.setCardSaleYn(saleYn[saleYnIdx]);
//
//                cardMapper.cardProduct(req);
//
//                sqlSession.flushStatements();
//            }
//
//        }
//
//
//
//
//    }
//}