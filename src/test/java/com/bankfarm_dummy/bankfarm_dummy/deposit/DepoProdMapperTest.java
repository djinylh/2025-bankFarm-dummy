package com.bankfarm_dummy.bankfarm_dummy.deposit;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.depo.DepoProdTermReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdInsertReq;
import com.bankfarm_dummy.bankfarm_dummy.depo.common.DepoProdMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.Random;

public class DepoProdMapperTest extends Dummy {
  final int ADD_ROW_COUNT = 300;

  @Test
  void insertDepoProd() {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
    DepoProdMapper depoProdMapper = sqlSession.getMapper(DepoProdMapper.class);

    Random rnd = new Random();

    // 상품 타입 코드
    String[] typeCodes = {
        "DO002", // 정기 예금
        "DO003", // 정기 적금
        "DO004"  // 자유 적금
    };

    // 타입 코드랑 매핑할 상품 이름
    String[] typeNameKor = {
        "예금",
        "적금",
        "자유 적금"
    };

    // 앞, 가운데에 붙일 수식어
    String[] prefix = {
        "스마트", "하이", "플러스", "알뜰", "프리",
        "청년", "직장인", "아이꿈", "미래", "내일",
        "새싹", "씨앗", "모아", "든든", "행복",
        "골드", "프라임", "스타", "라이트", "에코",
        "디지털", "e-", "온", "마이", "콤보"
    };
    String[] middle = {
        "모아모아", "더하기", "플랜", "챌린지", "드림",
        "점프업", "부스트", "스페셜", "프리미엄", "클래식",
        "세이브", "기쁨", "스텝", "패키지", "온리",
        "베스트", "스마트", "케어", "에디션", "라운지",
        "스마일", "위드유", "투게더", "스토리", "플러스"
    };

    String[] regularDepositDesc = {
        "일정 기간 예치 시 안정적인 금리를 제공하는 기본형 예금상품",
        "만기까지 유지하면 약정 금리를 그대로 받을 수 있는 안전한 예금상품",
        "다양한 예치 기간을 선택해 자금 계획에 맞춰 운용할 수 있는 예금상품",
        "중·장기 자금 보관에 적합한 안정형 정기예금상품",
        "원금 보장이 필요한 고객에게 적합한 기간형 예금상품"
    };

    String[] regularSavingsDesc = {
        "매달 일정 금액을 꾸준히 적립하며 목표 금액을 모을 수 있는 적립식 상품",
        "자동 이체로 편리하게 납입하고 약정 금리를 제공받는 적금상품",
        "꾸준한 저축 습관 형성에 적합한 기본 정기적금상품",
        "목표 금액을 단계적으로 달성할 수 있는 계획형 적금상품",
        "일정 기간 동안 매달 불입해 안정적인 수익을 기대할 수 있는 적금상품"
    };

    String[] freeSavingsDesc = {
        "월 납입 금액을 자유롭게 설정해 유연하게 저축할 수 있는 적립식 상품",
        "여유 자금이 생길 때마다 원하는 금액만큼 납입할 수 있는 자유적금상품",
        "고정 금액 부담 없이 자금을 유동적으로 운영할 수 있는 실속형 적금상품",
        "자동이체와 자유납입을 동시에 지원하는 유연한 적립상품",
        "다양한 소비 패턴에 맞춰 소액부터 차곡차곡 모을 수 있는 자유적금상품"
    };

    for (int i = 0; i < ADD_ROW_COUNT; i++) {

      // 타입 하나 뽑기
      int tpIdx = rnd.nextInt(typeCodes.length);
      String prodTpCode = typeCodes[tpIdx];
      String baseTypeNm = typeNameKor[tpIdx];

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

      // 상품 설명
      String prodDes = "";
      switch(tpIdx){
        case 0:
          prodDes = regularDepositDesc[rnd.nextInt(regularDepositDesc.length)];
          break;
        case 1:
          prodDes = regularSavingsDesc[rnd.nextInt(regularSavingsDesc.length)];
          break;
        case 2:
          prodDes = freeSavingsDesc[rnd.nextInt(freeSavingsDesc.length)];
      }

      // 판매 여부: 종료일이 없거나 오늘 이후면 Y, 아니면 N
      String saleYn;
      if (closeDt == null || closeDt.isAfter(LocalDate.now())) {
        saleYn = "Y";
      } else {
        saleYn = "N";
      }

      DepoProdInsertReq req = new DepoProdInsertReq();
      req.setDepoProdNm(prodNm);
      req.setDepoStDt(openDt);
      req.setDepoEdDt(closeDt);
      req.setDepoProdTp(prodTpCode);
      req.setDepoPodDes(prodDes);
      req.setDepoIntrstCalcUnit("DO017");
      req.setDepoIntrstPayCycle("DO021");
      req.setDepoIntrstCalcTp("DO027");
      req.setDepoSaleYn(saleYn);

      depoProdMapper.insertDepoProd(req);

      System.out.println("new prodId = " + req.getDepoProdId());

      // 만기 개월
      int[] monthArr = {12, 24, 36, 48, 60, 72, 84, 96, 108, 120};
      int termMonth = monthArr[rnd.nextInt(monthArr.length)];

      int minAmt = 0;
      Integer maxAmt = 0;

      if(prodTpCode.equals("DO002")){
        // 정기예금 최소 예치 금액
        int[] depoMinArr = {100000, 2000000, 3000000};
        minAmt = depoMinArr[rnd.nextInt(depoMinArr.length)];
        maxAmt = null;
      } else if (prodTpCode.equals("DO003")) {
        // 정기적금 최소 납입 금액
        int[] savInstallMinArr = {10000, 20000};
        minAmt = savInstallMinArr[rnd.nextInt(savInstallMinArr.length)];

        // 정기적금 최대 납입 금액
        int[] saveInstallMaxArr = {1000000, 2000000, 3000000, 4000000, 5000000};
        maxAmt = saveInstallMaxArr[rnd.nextInt(saveInstallMaxArr.length)];
      }else if (prodTpCode.equals("DO004")) {
        // 자유 적금 최소 납입 금액
        int[] saveFreeMinArr = {1000, 5000, 10000};
        minAmt = saveFreeMinArr[rnd.nextInt(saveFreeMinArr.length)];

        // 자유 적금 최대 납입 금액
        int[] saveFreeMaxArr = {1000000, 2000000, 3000000};
        maxAmt = saveFreeMaxArr[rnd.nextInt(saveFreeMaxArr.length)];

      }

      DepoProdTermReq termReq = new DepoProdTermReq();
      termReq.setDepoProdId(req.getDepoProdId());
      termReq.setDepoTermMonth(termMonth);
      termReq.setDepoMinAmt(minAmt);
      termReq.setDepoMaxAmt(maxAmt);

      depoProdMapper.insertDepoProdTerm(termReq);

      if (i > 0 && i % 100 == 0) {
        sqlSession.flushStatements();
      }
    }

    sqlSession.commit();
    sqlSession.close();
  }
}
