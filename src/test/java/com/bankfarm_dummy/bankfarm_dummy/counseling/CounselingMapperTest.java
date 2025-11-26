package com.bankfarm_dummy.bankfarm_dummy.counseling;


import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.counseling.model.CounselingReq;
import com.bankfarm_dummy.bankfarm_dummy.customer.CustomerMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

class CounselingMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 30;

    @Test
    void join(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        // 매퍼 주소값 얻기


        CounselingMapper mapper = sqlSession.getMapper(CounselingMapper.class);

        Random random = new Random();

        // 직원아이디 전체 조회 및 랜덤선택
        List<Long> empIds = mapper.getEmpId();
        // 고객아이디 전체 조회
        List<Long> custIds = mapper.getCustId();

        for(int i=0;i<ADD_ROW_COUNT;i++){
            CounselingReq counselingReq = new CounselingReq();

            // 직원 PK
//            int empId = (int)(Math.random() *50777) + 1;
//            counselingReq.setEmpId(empId);

            // 고객 PK
//            int custId = (int)(Math.random() *99373) + 1;
//            counselingReq.setCustId(custId);


            // 직원 랜덤 선택
            Long empId = empIds.get(random.nextInt(empIds.size()));
            counselingReq.setEmpId(empId);



            // 고객 랜덤 선택
            Long custId = custIds.get(random.nextInt(custIds.size()));
            counselingReq.setCustId(custId);

            System.out.printf("%d 번째 -> 직원ID:%d, 고객ID:%d%n", i, empId, custId);


            // 상담 제목
            counselingReq.setCounTitle("상담 제목 " + (i + 1));

            // 상담 내용
            counselingReq.setCounContent(kofaker.lorem().sentence());

            // 상담 유형 코드
            String[] types = {"CO001", "CO002", "CO003", "CO004", "CO005",
                               "C0007",  "C0008", "C0009", "C0010", "C0011",
                                "C0012",
            };
            int idx = (int)(Math.random() * types.length);
            counselingReq.setCounTpCd(types[idx]);

//            // 상담 등록 일자
//            counselingReq.setCounCrtAt(
//                    "2025-11-21 09:" +
//                            String.format("%02d", (i % 60)) +
//                            ":00"
//            );

            // 상담 등록 일자 랜덤생성
            LocalDateTime randomDateTime = LocalDateTime.now()
                    .minusDays(random.nextInt(365))     // 오늘 기준 → 1년 안에서 아무 날이나 과거 날짜 선택
                    .minusHours(random.nextInt(24))     // 0~23시간 중 하나 랜덤 → 시간 랜덤
                    .minusMinutes(random.nextInt(60))   // 0~59분 중 하나 랜덤 → 분 랜덤
                    .minusSeconds(random.nextInt(60));  // 0~59초 중 하나 랜덤 → 초 랜덤

            // 문자열 변환
            String dateStr = randomDateTime.format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            counselingReq.setCounCrtAt(dateStr);




            mapper.counselingJoin(counselingReq);




        }
        sqlSession.flushStatements();
    }


}
