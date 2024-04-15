package com.tech.ibara.biz.service.search;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tech.ibara.biz.dao.BizIDao;
import com.tech.ibara.biz.dto.BizHomeDto;
import com.tech.ibara.biz.dto.BizRvDto;
import com.tech.ibara.biz.dto.BizSearchTempDto;
import com.tech.ibara.biz.service.BizServiceInter;

@Service
public class BizAddrSearchRvCntService implements BizServiceInter {

	private SqlSession sqlSession;
	
	public BizAddrSearchRvCntService(SqlSession sqlSession) {
		// TODO Auto-generated constructor stub
		this.sqlSession=sqlSession;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		System.out.println("service BizAddrSearchRvCntService>>>");
		
		Map<String, Object> map=model.asMap();
		
		BizIDao dao=sqlSession.getMapper(BizIDao.class);
		
		String addrToSearch=map.get("addrToSearch").toString();
		System.out.println("입력주소: "+addrToSearch);
		
		//시/군/구를 API에서 받아와서 별점순 정렬
		
//		시군구가 같은 업체들의 시공리뷰 별점평균 staravg 리스트와  inteno 구해서 BizSearchTempDto에 담기
		ArrayList<BizSearchTempDto> bizStarAvgNoList = dao.bizStarAvgNoList(addrToSearch);
		
//		시군구가 같은 업체들의 시공리뷰 개수와  inteno 구해서 BizSearchTempDto에 담기
//		시군구가 같은 업체들의 시공리뷰 개수와  inteno 구하기
		ArrayList<BizSearchTempDto> bizRvCntNoList =dao.bizRvCntNoList(addrToSearch);
		
        // bizRvCntNoList를 기준으로 BizSearchTempDto을 업데이트
        for (BizSearchTempDto dto2 : bizRvCntNoList) {
            boolean found = false;
            for (BizSearchTempDto dto1 : bizStarAvgNoList) {
                if (dto1.getInteno()==dto2.getInteno()) {
                    // 동일한 column name이 있으면 해당 행의 값을 업데이트
                    dto1.setBst_rvcnt(dto2.getBst_rvcnt());
                    found = true;
                    break;
                }
            }
            // list1에 해당 column name이 없으면 새로운 행으로 추가
            if (!found) {
            	bizStarAvgNoList.add(dto2);
            }
        }

        // 결과 확인
        for (BizSearchTempDto dto : bizStarAvgNoList) {
            System.out.println("inteno: " + dto.getInteno() + ", rvcnt: " + dto.getBst_rvcnt());
        }
		
		
		
		
//		위에 만든 BizRvTempStaravgDto를 for문으로 돌려서 db 템프테이블에 inteno가 같은 열을 찾아 staravg 넣기
//		DB 템프테이블에 해당 BIZ_IDNO가 존재하지 않을 경우에만 INSERT, 존재 시 UPDATE
//		먼저 DB 템프테이블에 해당 BIZ_IDNO가 존재하는지 알아보기
//		위 dto에 있는 모든 inteno를 조회해서 존재하면 update, 존재하지 않으면 insert
		Integer bizRvTempCnt=0;

		for (int i = 0; i < bizStarAvgNoList.size(); i++) {
			
//			해당 템프테이블에 inteno와 매치되는 데이터 개수 알아보기(0=매치되는 데이터가 없다)
			bizRvTempCnt=dao.bizRvTempBizIdnoCheck(bizStarAvgNoList.get(i).getInteno());
			if(bizRvTempCnt!=0) {
				dao.bizRvCntUpdate(bizStarAvgNoList.get(i).getBst_staravg(), bizStarAvgNoList.get(i).getBst_rvcnt(), bizStarAvgNoList.get(i).getInteno());
			}else {
				dao.bizRvCntInsert(bizStarAvgNoList.get(i).getBst_staravg(), bizStarAvgNoList.get(i).getBst_rvcnt(),  bizStarAvgNoList.get(i).getInteno());
			}
			
		}
		
//		JOIN을 통해 시군구가 같은 업체만 골라서 리뷰개수순으로 정렬
		ArrayList<BizHomeDto> findHomeLikeInputRvCnt = dao.findHomeLikeInputRvCnt(addrToSearch);
		System.out.println("시군구 입력 후 검색 결과 수: "+findHomeLikeInputRvCnt.size());
		
        model.addAttribute("bizHomeSearch", findHomeLikeInputRvCnt);
        
		
//		리뷰첫줄 불러오는 함수
		ArrayList<BizRvDto> bizRvContentListJoinRvCnt=dao.bizRvContentListJoinRvCnt(addrToSearch);
		model.addAttribute("bizRvContentListJoinRvCnt", bizRvContentListJoinRvCnt);
		
		ArrayList<String> bizRvContentListRvCnt=new ArrayList<String>();
		
		System.out.println("시군구 입력 후 로딩된 리뷰 첫줄 개수: "+bizRvContentListJoinRvCnt.size());
	
		for (int j = 0; j < bizRvContentListJoinRvCnt.size(); j++) {
			bizRvContentListRvCnt.add(bizRvContentListJoinRvCnt.get(j).getBr_content());
		}
		model.addAttribute("bizRvContentList", bizRvContentListRvCnt);
		
		
	}
	
	
	
}