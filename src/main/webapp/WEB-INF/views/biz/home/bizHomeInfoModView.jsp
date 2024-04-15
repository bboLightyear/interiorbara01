<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$(document).ready(function(){
	var bh_profromDb = '${bizHome.bh_pro }';
	var bh_proFromPage = document.getElementsByName("bh_pro");
	for(var i=0; i<bh_proFromPage.length; i++) {	
		if(bh_profromDb==bh_proFromPage[i].value){
			bh_proFromPage[i].checked = true;				    	
	    }
	 }
});
</script>

<meta charset="UTF-8">
<title>업체 HOME</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.3/dist/zephyr/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
  	<style>
	 .fa-solid{
	 	color: #1a1f27;
	 }
	 .fa-solid:hover{
	 	color: #1e90ff;
	 } 
	</style>
</head>
<body>


	<form action="bizHomeInfoMod" method="post" enctype="multipart/form-data">
	<input type="hidden" name="inteno" value="${inteno }" />  <!-- 딱히 방법 없을 때 숨겨서 가져오는 법 -->
	<input type="hidden" name="bh_no" value="${bizHome.bh_no }" />  <!-- 딱히 방법 없을 때 숨겨서 가져오는 법 -->
	  <fieldset>
			<div class="pt-3 bg-light bg-opacity-75">
					<div class="d-flex justify-content-center">
						<div class="px-3" style="width: 750px" >
									<ul class="list-inline">
										<li class="list-inline-item">
											<h4><span style="--bs-text-opacity: 1; background-color: #1034a6; padding: 3px; !important;">
										    	<strong style="color: white;">정보 수정</strong>
										   	</span></h4>
										</li>
										<li class="list-inline-item">
												<small class="text-body-secondary">업체 정보를 수정할까요?</small>
										</li>
									</ul>	
						</div>
						<div class="d-flex align-items-end mb-4" onclick="location.href='/ibara/biz/home/bizHome?inteno=${bizHome.inteno}'">
							<span class="text-body-secondary" style="font-size: 12px; cursor:pointer;">홈으로 <i class="fa-solid fa-rotate-left" style="cursor:pointer;"></i></span>
						</div>
					</div>
			<div class="d-flex justify-content-center">
				<div class="px-3" style="width: 750px" >						
				    <div class="px-3 py-1">
				      <label for="headerImg" class="col-sm-2 col-form-label">헤더 이미지</label>
				      <div class="px-3 py-1">
				      	<img id="headerImg" src="../../resources/upload/biz/home/${bizHome.bh_img }" width="100%" style="max-width: 750px">
						<input class="mt-4 form-control" type="file" name="fileNew"  />
						<input type="hidden" name="bh_img" value="${bizHome.bh_img }" />
				      </div>
				    </div>
				    <div class="px-3 py-1">
				      <label for="bh_name" class="form-label mt-4">업체명</label>
				      <input type="text" class="form-control" name="bh_name" id="bh_name" value="${bizHome.bh_name }"  />
				    </div>
				    <div class="px-3 pt-4" style="font-size: 16px;">전문시공영역(선택 필수)</div>
				      <div class="form-check pb-1 pe-3 pt-1 ps-4">
				      	<input type="radio" name="bh_pro" value="마루"  />마루시공 <span style="width: 20px; color: transparent;">_</span>
						<input type="radio" name="bh_pro" value="도배·페인트"  />도배/페인트 <span style="width: 20px; color: transparent;">_</span>
						<input type="radio" name="bh_pro" value="장판·타일"  />장판/타일 <span style="width: 20px; color: transparent;">_</span>
						<input type="radio" name="bh_pro" value="욕실"  />욕실시공 <br />
						<input type="radio" name="bh_pro" value="주방"  />주방시공 <span style="width: 20px; color: transparent;">_</span>
						<input type="radio" name="bh_pro" value="문·샷시"  />문/샷시 <span style="width: 20px; color: transparent;">_____</span>
						<input type="radio" name="bh_pro" value="조명"  />조명시공 <span style="width: 20px; color: transparent;">__</span>
						<input type="radio" name="bh_pro" value="시트·필름"  />시트/필름
				     </div>
				    <div class="px-3 py-1">
				      <label for="sigungu" class="form-label mt-3">주소1(시군구)</label>
				      <input class="form-control" id="sigungu" type="text" name="bh_addr1" value="${bizHome.bh_addr1 }"  />
				    </div>
				    <div class="px-3 py-1">
				      <label for="detailAddr" class="form-label mt-4">주소2(상세주소)</label>
				      <input class="form-control" id="detailAddr" type="text" name="bh_addr2" value="${bizHome.bh_addr2 }"  />
				    </div>
				    <div class="px-3 py-1">
				      <label for="bh_notice" class="form-label mt-4">공지</label>
				      		<textarea class="form-control" name="bh_notice" id="bh_notice" cols="35" rows="5">
								${bizHome.bh_notice }
							</textarea>
				      <small id="noticeHelp" class="form-text text-muted">홈 상단에 노출됩니다.</small>
				    </div>
				    <div class="px-3 py-1 pb-3">
				      <label for="bh_intro" class="form-label mt-4">소개글</label>
				      		<textarea class="form-control"  name="bh_intro" id="bh_intro" cols="35" rows="5">
								${bizHome.bh_intro }
							</textarea>
				      <small id="noticeHelp" class="form-text text-muted">업체에 대한 자세한 소개; 홈 정보란에 노출됩니다.</small>
				    </div>
				    <div class="px-3 py-1">
					    <button type="submit" class="btn btn-primary">modify</button>
					    <%-- <button class="btn btn-primary" onclick="location.href='bizHome?inteno=${bizHome.inteno }">돌아가기</button> --%>
					 </div>
				</div>
			</div>		
		</div>	    
	  </fieldset>
	</form>
</body>
</html>