package com.bankfarm_dummy.bankfarm_dummy.loan;

import com.bankfarm_dummy.bankfarm_dummy.Dummy;
import com.bankfarm_dummy.bankfarm_dummy.loan.model.LoanProdInsertReq;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanProdMapperTest extends Dummy {

    @Test
    void loanProdMapperTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        LoanProdMapper loanProdMapper = sqlSession.getMapper(LoanProdMapper.class);



        for(int i=0;i<100000;i++){
        // 상품 이름
        String[] firstNm = {"겨울", "봄맞이", "여름","신년"};
        int firstIdx = (int)(Math.random()*firstNm.length);
        String[] secondNm = {"스페셜", "프로모션", "프리미엄"};
        int secondIdx = (int)(Math.random()*secondNm.length);
        String[] thirdNm = {"행복", "안심", "스피드","더조은","직장인","스마트"};
        int thirdIdx = (int)(Math.random()*thirdNm.length);
        int num = (int)(Math.random()*10000)+1;

        String finalNm = firstNm[firstIdx] + secondNm[secondIdx] + thirdNm[thirdIdx] + "대출" + num ;

        // 상품 설명
        String[] prodDes = {
                "우수 신용등급 고객을 위한 낮은 금리 대출 상품입니다.",
                "중간 신용등급 고객에게 제공되는 합리적 금리의 대출 상품입니다.",
                "저신용 고객도 신청 가능한 보증 기반 대출 상품입니다.",
                "단기 자금이 필요한 고객을 위한 단기 대출 상품입니다.",
                "12개월 이상 장기 상환을 지원하는 장기 대출 상품입니다.",
                "신규 직장인을 대상으로 한 사회초년생 우대 대출입니다.",
                "사업자를 위한 운영자금 목적의 기업 대출 상품입니다.",
                "전세 보증금을 마련할 수 있는 전세자금 대출 상품입니다.",
                "주택 구입을 위한 장기 모기지 대출 상품입니다.",
                "차량 구입 고객을 위한 자동차 대출 상품입니다.",
                "다자녀 가구를 위한 우대금리 적용 대출 상품입니다.",
                "급여이체 등록 고객에게 금리 혜택을 제공하는 대출입니다.",
                "카드 사용 실적이 있는 고객에게 금리를 우대하는 상품입니다.",
                "모바일 앱으로 비대면 신청 시 우대금리가 적용되는 대출입니다.",
                "연금 수령 고객에게 제공되는 안정적인 대출 상품입니다.",
                "전기차·하이브리드 차량 보유 고객 우대 대출입니다.",
                "공과금 자동납부 등록 고객에게 금리 혜택이 제공됩니다.",
                "기존 거래 고객을 위한 전용 우대 대출 상품입니다.",
                "신혼부부의 주거 자금 마련을 위한 우대 대출입니다.",
                "자영업자의 단기 운영자금을 지원하는 맞춤형 대출입니다."
        };
        int prodIdx = (int)(Math.random()*prodDes.length);

        // 최대 대출 금액
        int amt = ((int)(Math.random()*10) +1) * 100000000;

        // 상환 방식
        String[] rpmt = {"LN001","LN002","LN003","LN004","LN005","LN006","LN007","LN008"};
        int rpmtIdx = (int)(Math.random()*rpmt.length);

        // 번호
        int term = (int)(Math.random()*360) +36;

        // 상품 판매 시작일
        LocalDate start = LocalDate.of(1980, 1, 2);
        LocalDate end   = LocalDate.of(2020, 12, 31);
        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        LocalDate randomDate = start.plusDays((long)(Math.random() * days));

        //상품 판매 종료일
        LocalDate edDate = LocalDate.of(2099,12,30);


        LoanProdInsertReq req = new LoanProdInsertReq();
        req.setLoanProdNm(finalNm);
        req.setLoanDes(prodDes[prodIdx]);
        req.setLoanMaxAmt(amt);
        req.setLoanRpmtTp(rpmt[rpmtIdx]);
        req.setLoanTermMo(term);
        req.setLoanStDt(randomDate);
        req.setLoanEdDt(edDate);
        req.setLoanStsYn('Y');

        loanProdMapper.loanProdInsert(req);

        sqlSession.flushStatements();
        }
        sqlSession.close();



    }

}