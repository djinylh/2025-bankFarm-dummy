package com.bankfarm_dummy.bankfarm_dummy.loan.rate;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.rate.model.ProdRateInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

class ProdRateMapperTest extends Dummy {

    @Test
    void prodRateMapperTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ProdRateMapper prodRateMapper = sqlSession.getMapper(ProdRateMapper.class);


        for (int i = 1; i < 300001; i++) {

            int random = (int) (Math.random() * 3);

            if (random == 0) {
                ProdRateInsertReq req = new ProdRateInsertReq();
                req.setProdTp("RT007");
                req.setProdId(i);
                req.setProdRtId(10);
                prodRateMapper.prodRateInsert(req);

                ProdRateInsertReq req2 = new ProdRateInsertReq();
                req2.setProdTp("RT007");
                req2.setProdId(i);
                req2.setProdRtId(46);
                prodRateMapper.prodRateInsert(req2);


                ProdRateInsertReq req3 = new ProdRateInsertReq();
                req3.setProdTp("RT007");
                req3.setProdId(i);
                req3.setProdRtId(44);
                prodRateMapper.prodRateInsert(req3);


                ProdRateInsertReq req4 = new ProdRateInsertReq();
                req4.setProdTp("RT007");
                req4.setProdId(i);
                req4.setProdRtId(1);
                prodRateMapper.prodRateInsert(req4);

                sqlSession.flushStatements();
            }


            if (random == 0) {
                ProdRateInsertReq req = new ProdRateInsertReq();
                req.setProdTp("RT007");
                req.setProdId(i);
                req.setProdRtId(10);
                prodRateMapper.prodRateInsert(req);

                ProdRateInsertReq req2 = new ProdRateInsertReq();
                req2.setProdTp("RT007");
                req2.setProdId(i);
                req2.setProdRtId(46);
                prodRateMapper.prodRateInsert(req2);


                ProdRateInsertReq req3 = new ProdRateInsertReq();
                req3.setProdTp("RT007");
                req3.setProdId(i);
                req3.setProdRtId(44);
                prodRateMapper.prodRateInsert(req3);


                ProdRateInsertReq req4 = new ProdRateInsertReq();
                req4.setProdTp("RT007");
                req4.setProdId(i);
                req4.setProdRtId(1);
                prodRateMapper.prodRateInsert(req4);

                ProdRateInsertReq req5 = new ProdRateInsertReq();
                req5.setProdTp("RT007");
                req5.setProdId(i);
                req5.setProdRtId(50);
                prodRateMapper.prodRateInsert(req5);

                sqlSession.flushStatements();
            }


            if (random == 1) {
                ProdRateInsertReq req = new ProdRateInsertReq();
                req.setProdTp("RT007");
                req.setProdId(i);
                req.setProdRtId(11);
                prodRateMapper.prodRateInsert(req);

                ProdRateInsertReq req2 = new ProdRateInsertReq();
                req2.setProdTp("RT007");
                req2.setProdId(i);
                req2.setProdRtId(12);
                prodRateMapper.prodRateInsert(req2);

                ProdRateInsertReq req4 = new ProdRateInsertReq();
                req4.setProdTp("RT007");
                req4.setProdId(i);
                req4.setProdRtId(2);
                prodRateMapper.prodRateInsert(req4);

                ProdRateInsertReq req5 = new ProdRateInsertReq();
                req5.setProdTp("RT007");
                req5.setProdId(i);
                req5.setProdRtId(49);
                prodRateMapper.prodRateInsert(req5);

                sqlSession.flushStatements();
            }

            if (random == 2) {
                ProdRateInsertReq req = new ProdRateInsertReq();
                req.setProdTp("RT007");
                req.setProdId(i);
                req.setProdRtId(11);
                prodRateMapper.prodRateInsert(req);

                ProdRateInsertReq req2 = new ProdRateInsertReq();
                req2.setProdTp("RT007");
                req2.setProdId(i);
                req2.setProdRtId(13);
                prodRateMapper.prodRateInsert(req2);

                ProdRateInsertReq req3 = new ProdRateInsertReq();
                req3.setProdTp("RT007");
                req3.setProdId(i);
                req3.setProdRtId(7);
                prodRateMapper.prodRateInsert(req3);

                ProdRateInsertReq req4 = new ProdRateInsertReq();
                req4.setProdTp("RT007");
                req4.setProdId(i);
                req4.setProdRtId(1);
                prodRateMapper.prodRateInsert(req4);

                ProdRateInsertReq req5 = new ProdRateInsertReq();
                req5.setProdTp("RT007");
                req5.setProdId(i);
                req5.setProdRtId(49);
                prodRateMapper.prodRateInsert(req5);

                sqlSession.flushStatements();
            }

        }
            sqlSession.close();
    }
}