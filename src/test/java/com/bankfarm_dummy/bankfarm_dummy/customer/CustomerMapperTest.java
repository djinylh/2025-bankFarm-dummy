package com.bankfarm_dummy.bankfarm_dummy.customer;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.customer.model.CustBusinessReq;
import com.bankfarm_dummy.bankfarm_dummy.customer.model.CustomerJoinReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 100000;

    @Test
    void join(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        // 맵퍼 주소값 얻기
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);

        for(int i=0;i<ADD_ROW_COUNT;i++) {

            // 고객 이름 띄어쓰기 없애기
            String str = kofaker.name().name();
            String custName = str.replaceAll("\\s","");

            // 신용등급 우리는 1~10으로 하기로
            int randomNumber = (int)(Math.random()*10)+1;

            // 생일
            LocalDate date = kofaker.timeAndDate().birthday();
            String birthday = String.valueOf(date);

            // 주민번호
            String birth6 = date.format(DateTimeFormatter.ofPattern("yyMMdd"));

            // 성별 코드(1~4 중 하나 선택)
            int genderCode = kofaker.options().option(1, 2, 3, 4);

            // 뒤 6자리 랜덤 생성
            String randomDigits = kofaker.number().digits(6);

            // 최종 주민번호
            String jumin = birth6 + "-" + genderCode + randomDigits;

            // 고객 신용 설정
            String[] custCd = {"CU001","CU002","CU003","CU004"};
            int cdIdx = (int)(Math.random() * custCd.length);

            //고객 유형
            String[] custTp = {"CU011","CU012","CU013"};
            int tpIdx = (int)(Math.random() * custTp.length);

            //마케팅 동의
            String[] custMarketingYn = {"Y","N"};
            int ynIdx = (int)(Math.random() * custMarketingYn.length);


            //이메일
            String custFirstName = enfaker.name().firstName();
            String custNameRe = custFirstName.replaceAll("\\.","");
            String[] domains = {"naver.com", "daum.net", "gmail.com", "hotmail.com"};
            int dmIdx = (int)(Math.random() *  domains.length);
            String allEmail = custNameRe +'@' + domains[dmIdx];

            CustomerJoinReq req = new CustomerJoinReq();
            req.setCustName(custName);
            req.setCustPhone(kofaker.phoneNumber().cellPhone());
            req.setCustEmail(allEmail);
            req.setCustbirth(birthday);
            req.setCustCrdPoint(randomNumber);
            req.setCustSsn(jumin);
            req.setCustCd(custCd[cdIdx]);
            req.setCustTp(custTp[tpIdx]);
            req.setCustMarketingYn(custMarketingYn[ynIdx]);
            customerMapper.custJoin(req);

            // 여기서 부모 PK를 먼저 만들어 줘야 req에 PK값이 들어감
            sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();

            // 팩스 번호
            Random rnd = new Random();
            String[] fax = {"02-","053-", "056-","031-"};
            int faxIdx = (int)(Math.random() * fax.length);
            String faxNm = fax[faxIdx] + (100+rnd.nextInt(900)) + "-" + (1000+rnd.nextInt(9000));

            // 개인/법인 사업자 번호가 살아있는지
            char[] bsYn = {'Y','N'};
            int bsYnIdx = (int)(Math.random() * bsYn.length);
            char yn = bsYn[bsYnIdx];

            // 회사 이름 [대충 고객 이름에서 + 회사]
            int num = rnd.nextInt(1000);
            String cpNm =  "(주)" + custName + "회사" + num;

            // 여기서 사업자 고객이면
            if(req.getCustTp().equals("CU012") ){
                Random red = new Random();
                StringBuilder sb = new  StringBuilder();
                for(int k = 0; k < 10; k++){
                    sb.append(red.nextInt(10));
                    if(k==2 || k==4){sb.append("-");}
                }

                CustBusinessReq businesse = new CustBusinessReq();
                businesse.setCustBsNm(sb.toString());
                businesse.setCustId(req.getCustId());
                businesse.setCustCpNm(cpNm);
                businesse.setCustFax(faxNm);
                businesse.setCustBsYn(yn);


                customerMapper.busineesJoin(businesse);
            }


            // 법인 고객이면
            if(req.getCustTp().equals("CU013") ) {
                Random red = new Random();
                StringBuilder sb = new  StringBuilder();

                for(int k = 0; k < 13; k++){
                    sb.append(red.nextInt(10));
                    if(k==6){sb.append("-");}
                }


                CustBusinessReq businesse = new CustBusinessReq();
                businesse.setCustBsNm(sb.toString());
                businesse.setCustId(req.getCustId());
                businesse.setCustCpNm(cpNm);
                businesse.setCustFax(faxNm);
                businesse.setCustBsYn(yn);
                customerMapper.busineesJoin(businesse);
            }

            sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();

        }


        }

}