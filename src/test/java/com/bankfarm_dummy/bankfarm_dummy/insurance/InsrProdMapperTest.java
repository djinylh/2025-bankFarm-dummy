package com.bankfarm_dummy.bankfarm_dummy.insurance;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.insurance.model.InsrProdReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

class InsrProdMapperTest extends Dummy {

    final int ADD_ROW_COUNT = 500;

    @Test
    void insertInsrProd() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        InsuranceMapper insrMapper = sqlSession.getMapper(InsuranceMapper.class);

        // partner 테이블에서 사용중(Y)인 제휴사 id 리스트
        List<Long> partnerIds = insrMapper.selectActivePartnerIds();

        Random rnd = new Random();

        // 보험 타입 코드
        String[] typeCodes = {
                "IN006", // 종신보험
                "IN007", // 정기보험
                "IN008", // 저축/만기환급형
                "IN013", // 건강보험
                "IN014", // 암보험
                "IN015", // 중대질병(CI)보험
                "IN016", // 의료실비보험
                "IN017", // 상해보험
                "IN018", // 운전자보험
                "IN019", // 여행자보험
                "IN022", // 자동차보험
                "IN023"  // 반려동물보험
        };

        // 타입 코드랑 매핑할 보험 이름
        String[] typeNameKor = {
                "종신 보험",
                "정기 보험",
                "저축/만기환급형 보험",
                "건강 보험",
                "암 보험",
                "중대질병 보험",
                "의료실비 보험",
                "상해 보험",
                "운전자 보험",
                "여행자 보험",
                "자동차 보험",
                "반려동물 보험"
        };

        // 앞, 가운데에 붙일 수식어
        String[] prefix = {
                "자녀", "뉴라이프", "스마트", "행복든든", "평생안심",
                "프라임", "더케어", "굿라이프", "안전지킴", "미래보장",
                "웰빙", "헬스플러스", "하이파이브", "메디케어", "올케어",
                "굿케어", "맘편한", "든든한", "뉴패밀리", "라이프에이스",
                "하이엔드", "퍼스트클래스", "스페셜케어", "메디플랜", "라이프케어"
        };
        String[] middle = {
                "안심", "플러스", "프리미엄", "베이직", "실속형",
                "종합형", "맞춤형", "고급형", "스페셜", "클래식",
                "스마트형", "선진형", "스탠다드", "하이엔드", "슈퍼",
                "라이트", "프로텍트", "세이프", "케어", "업그레이드",
                "멀티케어", "뉴패밀리형", "단독형", "복합형", "보장강화형"
        };

        for (int i = 0; i < ADD_ROW_COUNT; i++) {

            Long partId = partnerIds.get(rnd.nextInt(partnerIds.size()));

            // 타입 하나 뽑기
            int tpIdx = rnd.nextInt(typeCodes.length);
            String prodTpCode = typeCodes[tpIdx];
            String baseTypeNm = typeNameKor[tpIdx];

            // 상품 코드
            String prodCd = "INS" + String.format("%05d", i + 1);

            // 상품 이름: 수식어 + 타입명 조합
            String prodNm =
                    prefix[rnd.nextInt(prefix.length)] + " " +
                            middle[rnd.nextInt(middle.length)] + " " +
                            baseTypeNm;

            // 판매 시작일: 2020~2024 사이 랜덤
            LocalDate openDt = LocalDate.of(2020, 1, 1)
                    .plusDays(rnd.nextInt(365 * 5));

            // 종료일: 어떤 건 null, 어떤 건 시작일 기준 몇 년 뒤
            LocalDate closeDt = null;
            if (rnd.nextBoolean()) {
                closeDt = openDt.plusYears(1 + rnd.nextInt(4));
            }

            // 수수료: 0.50 ~ 5.00% 이런 느낌
            BigDecimal commission = BigDecimal
                    .valueOf(0.5 + rnd.nextDouble(4.5))
                    .setScale(2, RoundingMode.HALF_UP);

            // 판매 여부: 종료일이 없거나 오늘 이후면 Y, 아니면 N
            String saleYn;
            if (closeDt == null || closeDt.isAfter(LocalDate.now())) {
                saleYn = "Y";
            } else {
                saleYn = "N";
            }

            InsrProdReq req = new InsrProdReq();
            req.setPartId(partId);
            req.setInsrCd(prodCd);
            req.setInsrNm(prodNm);
            req.setInsrProdTp(prodTpCode);
            req.setInsrOpenDt(openDt);
            req.setInsrCloseDt(closeDt);
            req.setInsrCommission(commission);
            req.setInsrSaleYn(saleYn);

            insrMapper.insrProdInsert(req);

            if (i > 0 && i % 200 == 0) {
                sqlSession.flushStatements();
            }
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
