package com.bankfarm_dummy.bankfarm_dummy.deposit;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.depo.DepoTermMapper;
import com.bankfarm_dummy.bankfarm_dummy.depo.DepoTermReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.DepoTermRes;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DepoTermMapperTest extends Dummy {

    @Test
    void inertTermContract() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        DepoTermMapper depoTermMapper = sqlSession.getMapper(DepoTermMapper.class);
        List<DepoTermRes> res = depoTermMapper.selectTermContract();

        for (DepoTermRes re : res) {
            DepoTermReq req = new DepoTermReq();
            req.setContractId(re.getDepoContractId());
            req.setTermTp("DO037");
            req.setTermDt(re.getDepoMaturityDt());

            depoTermMapper.insertTermContract(req);
        }
        sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.close();
    }
}
