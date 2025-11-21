package com.bankfarm_dummy.bankfarm_dummy.account;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractMapper;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemandDepoAccountMapperTest extends Dummy {

    @Test
    void getAccountByID() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        //계좌 맵퍼
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        //계좌 상품 맵퍼
        DepoProdMapper depoProdMapper = sqlSession.getMapper(DepoProdMapper.class);
        //계좌 계약 맵퍼
        DepoContractMapper depoContractMapper = sqlSession.getMapper(DepoContractMapper.class);



        //판매 여부
        char[] sale = {'Y','N'};
        int saleIdx = (int)(Math.random()*sale.length);

        DepoProdInsertReq prodReq = new DepoProdInsertReq();
//        prodReq.setDepoProdNm();
//        prodReq.setDepoStDt();
//        prodReq.setDepoEdDt();
//        prodReq.setDepoPodDes();
        prodReq.setDepoIntrstCalcUnit("DO018");
        prodReq.setDepoIntrstPayCycle("DO022");
        prodReq.setDepoIntrstCalcTp("DO026");
        prodReq.setDepoSaleYn(sale[saleIdx]);








    }


}