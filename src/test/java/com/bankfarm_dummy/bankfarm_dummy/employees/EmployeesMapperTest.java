package com.bankfarm_dummy.bankfarm_dummy.employees;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.employees.model.EmployeesJoinReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeesMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 51023;


    @Test
    void empJoin(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        EmployeesMapper empMapper = sqlSession.getMapper(EmployeesMapper.class);

        for(int i=0;i<ADD_ROW_COUNT;i++){

            long branID =  (long)(Math.random() * 264)+1;

            String str = kofaker.name().name();
            String empName = str.replaceAll("\\s","");

            String custFirstName = enfaker.name().firstName();
            String custNameRe = custFirstName.replaceAll("\\.","");
            String[] domains = {"naver.com", "daum.net", "gmail.com", "hotmail.com"};
            int dmIdx = (int)(Math.random() *  domains.length);
            String allEmail = custNameRe +'@' + domains[dmIdx];

            String[] positionNm = {"사원","주임","대리","과장","차장"};
            int positionNmIdx = (int)(Math.random() *positionNm.length);

            LocalDate start = LocalDate.of(1980, 1, 2);
            LocalDate end   = LocalDate.of(2025, 12, 31);
            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
            LocalDate randomDate = start.plusDays((long)(Math.random() * days));

            int random = (int)(Math.random() *5);

        EmployeesJoinReq epReq = new EmployeesJoinReq();
        epReq.setBranId(branID);
        epReq.setEmpEmail(allEmail);
        epReq.setEmpNm(empName);
        epReq.setEmpPhone(kofaker.phoneNumber().cellPhone());
        if(i==0){
            epReq.setEmpPositionNm("회장");
        }else if(i==1){
            epReq.setEmpPositionNm("부회장");
        }else if(i%3==0||i%2==0){
            epReq.setEmpPositionNm("사원");
        }else {
        epReq.setEmpPositionNm(positionNm[positionNmIdx]);
        }
        epReq.setEmpHireDt(randomDate);
        if(random == 2){
        epReq.setEmpResignationAt(randomDate);
        }
        empMapper.employeesJoin(epReq);
        sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();
        }

    }



}