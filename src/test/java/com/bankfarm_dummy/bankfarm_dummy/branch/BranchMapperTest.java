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
    final int ADD_ROW_COUNT = 5;
    final int ADD_ROW_COUNT_TWO = 4;
    final int ADD_ROW_COUNT_THREE = 5;

    @Test
    void branchMapperTest() {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

    BranchMapper branchMapper = sqlSession.getMapper(BranchMapper.class);

    for(int i=0;i<ADD_ROW_COUNT;i++) {

        String[] city = {"서울","경기도","대구","부산", "경북"};

        for(int j=0;j<ADD_ROW_COUNT_TWO;j++) {
            String[] district = {"동구", "서구", "중구", "북구"};
            String branchName = city[i] + district[j] + "점";


            for (int k = 0; k < ADD_ROW_COUNT_THREE; k++) {
                String[] address = {"현풍읍", "상인동", "삼성동", "상리읍", "서면"};

                Random red = new Random();
                StringBuilder sb = new StringBuilder();
                for (int d = 0; d < 5; d++) {
                    sb.append(red.nextInt(10));
                    if (d == 2) {
                        sb.append("-");
                    }

                    String btanchaddress = city[i] + district[j] + address[k] + sb;


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
                    if(k==2){
                        req.setBranActive("BR001");
                    } else {
                    req.setBranActive("BR003");
                    }
                    req.setBranRegionCd(bCode.toString());

                    branchMapper.branchJoin(req);
                    sqlSession.flushStatements();


                    }

                }

            }

        }
    }
}