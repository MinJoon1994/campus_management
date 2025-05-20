<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     

<c:set var="center" value="${requestScope.center}" />

<%-- 처음 클라이언트가 ProfessorMain.jsp메인화면을 MVC패턴으로 요청했을때 중앙화면은 ProfessorFirstCenter.jsp로 보이게 설정 --%>
<c:if test="${center == null}">
    <c:set var="center" value="StudentFirstCenter.jsp" />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사관리 메인</title>
	<style>
	    html, body {
	        margin: 0;
	        padding: 0;
	        height: 100%;
	    }
	    .container {
	        display: flex;
	        flex-direction: column;
	        height: 100vh;   /* 브라우저 전체 높이 */
	        width: 100vw;    /* 브라우저 전체 너비 */
	    }
	    .top {
	        position: fixed; /* 요소를 브라우저 전체 기준으로 항상 같은 위치에 고정 */
	        top: 0;          /* 화면 좌측 상단 모서리에 딱 붙게 만든다. */
	        left: 0;
	        width: 100vw;    /* 브라우저 창 가로 폭 전체를 차지한다.  */
	        height: 110px;   /* 상단 메뉴 높이 */
	        z-index: 1000;   /* z-index는 요소들이 쌓이는 순서 => 클수록 화면 위쪽에 표시 */
	    }
	    .center {
	    	margin-top: 110px; /* 상단 메뉴만큼 아래로 밀기 */
	        width: 100%;
	    }
		.center img.register-success-img {
		    height: auto;    /* 폭만 조절하고 높이는 자연스럽게 따라오게(이미지 비율을 유지) */   
		    max-width: 90%;  /* 이미지의 최대 가로길이를 부모 너비의 90%로 제한  */
		    margin: 0 auto;  /* 상하 여백은 0, 좌우 여백은 auto => 가운데 정렬 */
		    display: block;  /* margin: 0 auto;해도 그 요소가 block 요소여야 가운데 정렬이 된다. */
		}

	</style>
</head>
<body>
    <div class="container">
        <!-- 상단 영역 -->
        <div class="top">
            <jsp:include page="StudentTop.jsp" />
        </div>

        <!-- 중앙 영역 -->
        <div class="center">
            <jsp:include page="${center}" />
        </div>
    </div>
</body>
</html>
