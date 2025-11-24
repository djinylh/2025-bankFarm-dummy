package com.bankfarm_dummy.bankfarm_dummy.account;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumReq;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumRes;
import com.bankfarm_dummy.bankfarm_dummy.account.model.GetDemandProdRes;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractMapper;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DemandDepoAccountMapperTest extends Dummy {


    @Test
    void getAccountByID() {

        for(int i =0; i<500;i++){

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        //계좌 맵퍼
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        //계좌 상품 맵퍼
        DepoProdMapper depoProdMapper = sqlSession.getMapper(DepoProdMapper.class);
        //계좌 계약 맵퍼
        DepoContractMapper depoContractMapper = sqlSession.getMapper(DepoContractMapper.class);
        //상품 불러오기
        List<GetDemandProdRes> getDemandProdRes = accountMapper.prodByDemand();

        String finalAcctNum = accountNum();

        int custId = (int)(Math.random()*99999)+1;
        int acctBal = (int)(Math.random()*100000000)+7000000;

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("1234");

        // 계좌 상태
        String[] cd = {"AS001","AS002","AS003","AS004","AS005"};
        int cdIdx = (int)(Math.random()*cd.length);

        // 일일 한도
        int[] limit = {300000,500000,1000000,2000000,3000000};
        int limitIdx = (int)(Math.random()*limit.length);

        AccountInsertReq req = new AccountInsertReq();
        req.setCustId(custId);
        req.setAccTp((byte)0);
        req.setAcctSavTp("AC001");
        req.setAcctNum(finalAcctNum);
        req.setAcctPw(password);
        req.setAcctBal(acctBal);
        req.setAcctDayLimit(limit[limitIdx]);
        req.setAcctStsCd(cd[cdIdx]);
        req.setAcctIsDedYn('Y');

        accountMapper.accountInsert(req);
        // 계좌 생성.
//        sqlSession.flushStatements();

        List<GetDemandProdRes> list= accountMapper.prodByDemand();
        int listIdx = (int)(Math.random()*list.size());

        GetDemandProdRes finalItem = list.get(listIdx);

        long finalNum = finalItem.getProdId();

        // 직원
            int empId = (int)(Math.random()*51023)+1;

            // 일자
            LocalDate start = LocalDate.of(1980, 1, 2);
            LocalDate end   = LocalDate.of(2020, 12, 31);
            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
            LocalDate randomDate = start.plusDays((long)(Math.random() * days));


            LocalDate start2 = LocalDate.of(2026, 1, 2);
            LocalDate end2   = LocalDate.of(2040, 12, 30);
            long days2 = java.time.temporal.ChronoUnit.DAYS.between(start2, end2);
            LocalDate randomDate2 = start2.plusDays((long)(Math.random() * days2));

        DepoContractInsertReq contReq = new DepoContractInsertReq();
        contReq.setCustId(custId);
        contReq.setDepoProdId(finalNum);
        contReq.setAcctId(req.getAcctId());
        contReq.setEmpId(empId);
        contReq.setDepoProdTp("PD006");
        contReq.setDepoContractDt(randomDate);
        contReq.setDepoMaturityDt(randomDate2);
        contReq.setDepoActiveCd("AP002");

        depoContractMapper.depoContractInsert(contReq);
        //계약 생성
//        sqlSession.flushStatements();




        }


    }


    private String accountNum(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        //계좌 맵퍼
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        while(true){
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for(int i= 0; i<14;i++){
                sb.append(random.nextInt(10));
                if(i==6 || i==8){
                    sb.append("-");
                }
            }
            String accountNum = sb.toString();
            AccountFindAcctNumReq acctFindReq =  new AccountFindAcctNumReq();
            acctFindReq.setAcctNum(accountNum);
            AccountFindAcctNumRes y = accountMapper.countByAccountNum(acctFindReq);

            if(y==null){
                return accountNum;
            }
        }




    }


}