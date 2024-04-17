<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" /> -->
<script defer src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script defer src="${path}/resources/js/my/myinteriorchart.js"></script>
</head>
<body>
<h3>interiorcases.jsp</h3>
	<div class="mypage_category">
        <div class="mypage_category_list list_1"><a href="mypage">프로필</a></div>
	    <div class="mypage_category_list list_2"><a href="interiorestimate">견적</a></div> <br />
    </div>
    <div class="mypage_category">
        <div class="mypage_category_list list_1_1"><a href="interiorestimate">견적리스트</a></div>
	    <!-- <div class="mypage_category_list list_1_2">내시공사례</div> -->
        <!-- <div class="mypage_category_list list_1_3">질문과답변</div> -->
        <div class="mypage_category_list list_1_2"><a href="#">시공사례</a></div>
        <div class="mypage_category_list list_1_3">견적자재금액관리</div>
    </div>
    
    
    
</body>
</html>