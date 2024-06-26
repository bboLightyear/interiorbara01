package com.tech.ibara.biz.service.search;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tech.ibara.biz.dao.BizIDao;
import com.tech.ibara.biz.dto.BizHomeDto;
import com.tech.ibara.biz.dto.BizRvDto;
import com.tech.ibara.biz.service.BizServiceInter;
import com.tech.ibara.biz.vo.BizInfiniteSearchVO;

@Service
public class BizProSearchService implements BizServiceInter {

	private SqlSession sqlSession;

	public BizProSearchService(SqlSession sqlSession) {
		// TODO Auto-generated constructor stub
		this.sqlSession=sqlSession;
	}

	@Override
	public void execute(Model model) {

		System.out.println("service BizAddrSearchService>>>");
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=(HttpServletRequest) map.get("request");
		BizInfiniteSearchVO searchVO=(BizInfiniteSearchVO) map.get("searchVO");
		
		BizIDao dao=sqlSession.getMapper(BizIDao.class);

		
		//sk값 가져오기
				String searchKeyword=request.getParameter("sk");
				if (searchKeyword==null) {
					searchKeyword="";
				}
				model.addAttribute("resk", searchKeyword);
				System.out.println("skkkk: "+searchKeyword);		
				
//				<paging---->
				String strPage=request.getParameter("page");
				
				//검색어 아무것도 입력 안해줬을 때의 null처리
				if(strPage==null) {
					strPage="1";}
				
				int page=Integer.parseInt(strPage);
				searchVO.setPage(page);

				int total=0;
				if(searchKeyword!="") {
					total=dao.selectAddrSearchTotalCount1(searchKeyword);
					
				}else {
					total=dao.selectAddrSearchTotalCount2(searchKeyword);
				}
				
				System.out.println("total : "+total);

				searchVO.pageCalculate(total);
				//계산된 값
				System.out.println("totRow : "+total);
				System.out.println("clickpage : "+strPage);
				System.out.println("pageStart : "+searchVO.getPageStart());
				System.out.println("pageEnd : "+searchVO.getPageEnd());
				System.out.println("rowStart : "+searchVO.getRowStart());
				System.out.println("rowEnd : "+searchVO.getRowEnd());
				
				int rowStart=searchVO.getRowStart();
				int rowEnd=searchVO.getRowEnd();
				
				ArrayList<BizHomeDto> bizHomeSearch = null;
				
				if(searchKeyword!="") {
					bizHomeSearch=dao.bizSearchHomeList(rowStart,rowEnd,searchKeyword,"1");
				}else {
					bizHomeSearch = dao.bizSearchHomeList(rowStart,rowEnd,searchKeyword,"2");
				}
				System.out.println(bizHomeSearch);
				model.addAttribute("bizHomeSearch", bizHomeSearch);
				model.addAttribute("totRowcnt", total);
				model.addAttribute("searchVO", searchVO);
				
				
//				전문시공영역에 따라 분류하기
				
				String bh_pro=request.getParameter("bh_pro");
				
		
//		DB에서 pro에 해당 전문시공영역이 들어가는 업체 검색 돌려줄 함수
		
		
//		첫화면 보여주기(전문시공영역 없이)
		System.out.println("로딩된 홈개수: "+bizHomeSearch.size());
	

//		해당업체 시공리뷰 별점의 평균을 세서, for 문을 통해 그 평균들을 arrayList에 담아주는 함수
		ArrayList<Float> bizSearchRvPointAvgList = new ArrayList<Float>();
		try {
			Float bizSearchRvPointAvg=0.0f;
			for (int i = 0; i < bizHomeSearch.size(); i++) {
				bizSearchRvPointAvg=dao.bizSearchRvPointAvg(bizHomeSearch.get(i).getInteno());
				if(bizSearchRvPointAvg!=null) {
					bizSearchRvPointAvgList.add(bizSearchRvPointAvg);
				}else {
					bizSearchRvPointAvgList.add(bizSearchRvPointAvg);
					System.out.println(i+"번째 bizSearchRvPointAvg 값이 null입니다.");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("업체별점 리스트 사이즈: "+bizSearchRvPointAvgList.size());
		model.addAttribute("bizSearchRvPointAvgList", bizSearchRvPointAvgList);

		
//		리뷰첫줄 불러오는 함수
		ArrayList<BizRvDto> bizRvContentListJoin=dao.bizRvContentListJoin();
		model.addAttribute("bizRvContentListJoin", bizRvContentListJoin);
		
		ArrayList<String> bizRvContentList=new ArrayList<String>();
		
		System.out.println("로딩된 리뷰개수: "+bizRvContentListJoin.size());
		for (int j = 0; j < bizRvContentListJoin.size(); j++) {
			bizRvContentList.add(bizRvContentListJoin.get(j).getBr_content());
		}
		model.addAttribute("bizRvContentList", bizRvContentList);
		
		
		
		
	}

}
