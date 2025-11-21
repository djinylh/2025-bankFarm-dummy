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


    @Test
    void join() {

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        CardOptionParamMapper cardOptionParamMapper = sqlSession.getMapper(CardOptionParamMapper.class);

        List<CardOptionGetRes> res = cardOptionParamMapper.getListByOption();

        for (CardOptionGetRes res1 : res) {

            if(res1.getCardOptionCode().equals("QUICK_PAY")){
                String[] pay = {"삼성페이","카카오페이","네이버페이"};

                for(int i =0; i<3; i++){

                    CardOptionParamReq req = new CardOptionParamReq();
                    req.setCardOptionDefId(res1.getCardOptionDefId());
                    req.setCardOptionDes(pay[i]);
                    req.setCardActiveYn('Y');
                    cardOptionParamMapper.cardOptionParamDetail(req);
                    sqlSession.flushStatements();
                }

            }
            else if(res1.getCardOptionCode().equals("SMS_ALERT")){
                String[] pay = {"문자수신","PUSH알림","이메일알림"};

                for(int i =0; i<3; i++){

                    CardOptionParamReq req = new CardOptionParamReq();
                    req.setCardOptionDefId(res1.getCardOptionDefId());
                    req.setCardOptionDes(pay[i]);
                    req.setCardActiveYn('Y');
                    cardOptionParamMapper.cardOptionParamDetail(req);
                    sqlSession.flushStatements();
                }

            }
            else if(res1.getCardOptionCode().equals("TRANSIT")){
                String[] pay = {"T-money","캐시비","후불교통카드"};

                for(int i =0; i<3; i++){

                    CardOptionParamReq req = new CardOptionParamReq();
                    req.setCardOptionDefId(res1.getCardOptionDefId());
                    req.setCardOptionDes(pay[i]);
                    req.setCardActiveYn('Y');
                    cardOptionParamMapper.cardOptionParamDetail(req);
                    sqlSession.flushStatements();
                }

            }
            else if(res1.getCardOptionCode().equals("OVERSEAS")){
                String[] pay = {"한도0원","한도100달러","한도300달러"};

                for(int i =0; i<3; i++){

                    CardOptionParamReq req = new CardOptionParamReq();
                    req.setCardOptionDefId(res1.getCardOptionDefId());
                    req.setCardOptionDes(pay[i]);
                    req.setCardActiveYn('Y');
                    cardOptionParamMapper.cardOptionParamDetail(req);
                    sqlSession.flushStatements();
                }

            }
            else if(res1.getCardOptionCode().equals("ONLINE_AUTH")){
                String[] pay = {"OTP","PIN","BIO"};

                for(int i =0; i<3; i++){

                    CardOptionParamReq req = new CardOptionParamReq();
                    req.setCardOptionDefId(res1.getCardOptionDefId());
                    req.setCardOptionDes(pay[i]);
                    req.setCardActiveYn('Y');
                    cardOptionParamMapper.cardOptionParamDetail(req);
                    sqlSession.flushStatements();
                }

            }
            //


        }


    }

}