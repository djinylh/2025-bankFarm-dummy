package com.bankfarm_dummy.bankfarm_dummy.account;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractMapper;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DemandDepoProdMapperTest extends Dummy {

    @Test
    void demandDepoProdMapperTest(){



        for(int i=0;i<8;i++){

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        //계좌 상품 맵퍼
        DepoProdMapper depoProdMapper = sqlSession.getMapper(DepoProdMapper.class);



        //판매 여부
        String[] sale = {"Y","N"};
        int saleIdx = (int)(Math.random()*sale.length);

        //상품 이름
        String[] acctProdFName= {"바로","체크","편한","생활","자유","내맘대로","평생","미래"};
//        int fNameIdx = (int)(Math.random()*acctProdFName.length);

        String[] acctProdMName= {"든든","스마트","요구불","플러스","플랜","행복","사랑","가득"};
        int mNameIdx = (int)(Math.random()*acctProdMName.length);

        String acctFullName = acctProdFName[i] +  acctProdMName[mNameIdx] + "요구불 통장" + (int)(Math.random()* 20)+1;

        // 상품 설명
        String[] acctProdDes= {
                "입출금이 자유로운 기본 요구불 계좌"
                ,"체크카드와 연동되는 생활자금 계좌"
                ,"손쉽게 관리 가능한 요구불 계좌"
                ,"급여, 생활비 등 일상 자금 관리용 계좌"
                ,"최소 잔액 제한 없이 자유롭게 입출금 가능"
                ,"입출금은 자유지만 소액 이자도 지급하는 계좌"
        };
        int desIdx = (int)(Math.random()*acctProdDes.length);


        LocalDate start = LocalDate.of(1980, 1, 2);
        LocalDate end   = LocalDate.of(2020, 12, 31);
        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        LocalDate randomDate = start.plusDays((long)(Math.random() * days));


        LocalDate start2 = LocalDate.of(2021, 1, 2);
        LocalDate end2   = LocalDate.of(2025, 12, 30);
        long days2 = java.time.temporal.ChronoUnit.DAYS.between(start2, end2);
        LocalDate randomDate2 = start2.plusDays((long)(Math.random() * days2));




        DepoProdInsertReq prodReq = new DepoProdInsertReq();
        prodReq.setDepoProdNm(acctFullName);
        prodReq.setDepoStDt(randomDate);
        prodReq.setDepoEdDt(randomDate2);
        prodReq.setDepoPodDes(acctProdDes[desIdx]);
        prodReq.setDepoIntrstCalcUnit("DO018");
        prodReq.setDepoIntrstPayCycle("DO022");
        prodReq.setDepoIntrstCalcTp("DO026");
        prodReq.setDepoSaleYn(sale[saleIdx]);

        // 계좌 상품 만들기
        depoProdMapper.depoProdInsert(prodReq);
        sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();
        }


    }



}