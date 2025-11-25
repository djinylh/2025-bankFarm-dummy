package com.bankfarm_dummy.bankfarm_dummy.deposit;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.account.AccountInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.account.AccountMapper;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumReq;
import com.bankfarm_dummy.bankfarm_dummy.account.model.AccountFindAcctNumRes;
import com.bankfarm_dummy.bankfarm_dummy.account.model.GetDemandProdRes;
import com.bankfarm_dummy.bankfarm_dummy.branch.BranchMapper;
import com.bankfarm_dummy.bankfarm_dummy.branch.model.GetBranchByEmpRes;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoContractMapper;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdMapper;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.ProdDocumentMapper;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.model.ProdDocumentReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class DepoContractMapperTest extends Dummy {
  final int ADD_ROW_COUNT = 300;
  @Test
  void getAccountByID() {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    DepoContractMapper depoContractMapper = sqlSession.getMapper(DepoContractMapper.class);
    DepoProdMapper depoProdMapper = sqlSession.getMapper(DepoProdMapper.class);
    BranchMapper branchMapper = sqlSession.getMapper(BranchMapper.class);
    ProdDocumentMapper prodDocumentMapper = sqlSession.getMapper(ProdDocumentMapper.class);

    for (int i = 0; i < ADD_ROW_COUNT; i++) {
      Random random = new Random();

      // fk로 쓸 아이디 리스트
      List<Long> custIds = depoContractMapper.selectCustomerIds();
      List<Long> empIds = depoContractMapper.selectEmployeeIds();
      List<Long> prodIds = depoContractMapper.selectDepoProdIds();

      // 랜덤으로 뽑은 fk 아이디
      Long custId =  custIds.get(random.nextInt(custIds.size()));
      Long empId = empIds.get(random.nextInt(empIds.size()));
      Long prodId = prodIds.get(random.nextInt(prodIds.size()));

      // 계좌 번호 생성
      String finalAcctNum = accountNum();

      AccountInsertReq accountInsertReq = new AccountInsertReq();

      // 비밀번호 암호화
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String password = passwordEncoder.encode("1234");

      // 계좌 상태
      String[] cd = {"AS001","AS002","AS005"};
      String acctSts = cd[(int)(Math.random()*cd.length)];


      AccountInsertReq req = new AccountInsertReq();
      req.setCustId(custId);
      req.setAccTp((byte)0);
      req.setAcctSavTp("AC001");
      req.setAcctNum(finalAcctNum);
      req.setAcctPw(password);
      req.setAcctBal(0);
      req.setAcctDayLimit(0);
      req.setAcctStsCd(acctSts);
      req.setAcctIsDedYn('N');

    }

    for(int i =0; i<500;i++){

      // 비밀번호 암호화
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String password = passwordEncoder.encode("1234");

      // 계좌 상태
      String[] cd = {"AS001","AS002","AS003","AS004","AS005"};
      int cdIdx = (int)(Math.random()*cd.length);

      // 일일 한도
      int[] limit = {300000,500000,1000000,2000000,3000000};
      int limitIdx = (int)(Math.random()*limit.length);


      // 계좌 생성.
      sqlSession.flushStatements();


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
//      contReq.setCustId(custId);
//      contReq.setDepoProdId(finalNum);
//      contReq.setAcctId(req.getAcctId());
//      contReq.setEmpId(empId);
//      contReq.setDepoContractDt(randomDate);
//      contReq.setDepoMaturityDt(randomDate2);
//      contReq.setDepoActiveCd("AP002");

      depoContractMapper.depoContractInsert(contReq);
      //계약 생성
      sqlSession.flushStatements();



      // 상품 계약 테이블 이동
      GetBranchByEmpRes empRes = branchMapper.getBranchIdByEmpId(empId);
      long branchId = empRes.getBranId();


      ProdDocumentReq prodReq = new ProdDocumentReq();
      prodReq.setBranId(branchId);
      prodReq.setDocProdTp("PD006");
      prodReq.setDocNm("요구불 계좌 문서 이름");
      prodReq.setDocProdId(contReq.getDepoContractId());
      prodDocumentMapper.prodDocumentJoin(prodReq);

      sqlSession.flushStatements();

    }
    sqlSession.commit();
    sqlSession.close();


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
