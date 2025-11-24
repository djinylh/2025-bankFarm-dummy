package com.bankfarm_dummy.bankfarm_dummy.branch;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.branch.model.BranchReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BranchMapperTest extends Dummy {
    final int ADD_ROW_COUNT = 22;
    final int ADD_ROW_COUNT_TWO = 12;

    @Test
    void branchMapperTest() {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

    BranchMapper branchMapper = sqlSession.getMapper(BranchMapper.class);

    for(int i=0;i<ADD_ROW_COUNT;i++) {

        String[] city = {"서울","경기도","대구","부산", "경주", "인천","강원도"
                ,"제주", "대전", "울산","밀양", "광주", "군산", "청주","나주"
                ,"원주","강릉","여수","안동","진주","강릉","논산"
        };

        for(int j=0;j<ADD_ROW_COUNT_TWO;j++) {
            String[] district = {"동구", "서구", "중구", "북구","율하구","서면구","남동구", "분당구","해운대구","수성구","동래구","송파구"};
            String branchName = city[i] + district[j] + "점";

            String[] address = {"현풍읍", "상인동", "삼성동", "상리읍", "서면"};
            int addIdx = (int) (Math.random() * address.length);

            Random red = new Random();
            StringBuilder sb = new StringBuilder();
            sb.append(red.nextInt(800));

            String btanchaddress = city[i] + district[j] + address[addIdx] + sb;


            double latitude = Double.parseDouble(kofaker.address().latitude());
            double longitude = Double.parseDouble(kofaker.address().longitude());

            // 개업일
            LocalDate start = LocalDate.of(1980, 1, 1);
            LocalDate end   = LocalDate.of(2023, 12, 31);
            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
            LocalDate randomDate = start.plusDays((long)(Math.random() * days));


            //지역코드 랜덤
            StringBuilder bCode = new StringBuilder();
            for (int c = 0; c < 5; c++) {
                bCode.append(red.nextInt(10));
            }

            BranchReq req = new BranchReq();
            req.setBranNm(branchName);
            req.setBranTel(kofaker.phoneNumber().phoneNumber());
            req.setBranAddress(btanchaddress);
            req.setBranLatitude(latitude);
            req.setBranLongitude(longitude);
            req.setBranOpenedAt(randomDate);
            if(i==2){
                req.setBranActive("BR001");
            } else {
                req.setBranActive("BR003");
            }
            req.setBranRegionCd(bCode.toString());

            branchMapper.branchJoin(req);
            sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();

            }


        }
    }

}
