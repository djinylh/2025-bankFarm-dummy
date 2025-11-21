package com.bankfarm_dummy.bankfarm_dummy.counseling;


import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.counseling.model.CounselingReq;
import com.bankfarm_dummy.bankfarm_dummy.customer.CustomerMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

class CounselingMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 100000;

    @Test
    void join(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        // 매퍼 주소값 얻기
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);

        CounselingMapper mapper = sqlSession.getMapper(CounselingMapper.class);

        for(int i=0;i<ADD_ROW_COUNT;i++){
            CounselingReq counselingReq = new CounselingReq();

            // 직원 PK
            int empId = (int)(Math.random() *50777) + 1;
            counselingReq.setEmpId(empId);

            // 고객 PK
            int custId = (int)(Math.random() *99373) + 1;
            counselingReq.setCustId(custId);

            // 상담 제목
            counselingReq.setCounTitle("상담 제목 " + (i + 1));

            // 상담 내용
            counselingReq.setCounContent(kofaker.lorem().sentence());

            // 상담 유형 코드
            String[] types = {"N01", "N02", "N03", "N04"};
            int idx = (int)(Math.random() * types.length);
            counselingReq.setCounTpCd(types[idx]);

            // 상담 등록 일자
            counselingReq.setCounCrtAt(
                    "2025-11-21 09:" +
                            String.format("%02d", (i % 60)) +
                            ":00"
            );


            mapper.counselingJoin(counselingReq);




        }
        sqlSession.flushStatements();
    }


}
