<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사관리시스템</title>
<style>

.top_box{
	background-color:rgb(44, 62, 80);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
    display: flex;
	height:100px;
}

.top_box div img{
	height:75px;
	width:75px;
}

.top_box div a{
	color:white;
	text-decoration:none;
	cursor:pointer;
	font-size:25px;
}

.top_box div a p{
	color:white;
	text-decoration:none;
	cursor:pointer;
	font-size:40px;
	margin:0;
}

.side-bar-hidden{
    margin-top: 40px; /* 헤더 높이만큼 여백을 줌 */
    position:fixed;
    top:60px;
    right:0;
    width:15%;
    height:100%;
    background-color:rgba(44, 62, 80,0.9);
    padding: 20px;
    z-index: 999; /* 헤더보다 낮지만 충분히 앞에 있도록 */
    display:none;
}

.login_name{
    color:white;
    text-decoration:none;
    cursor:pointer;
    font-size:25px;
    margin:0;
    margin-right: 20px;
}

.menu_side_bar h5{
	color:white;
    text-decoration:none;
    cursor:pointer;
    font-size:25px;
    margin:0;
}

.menu_side_bar hr{
	border: 1px solid white;
    margin: 10px 0;
}

.menu_side_bar ul{
    list-style-type:none;
    padding:0;
}

.menu-button {
	width: 50px;
	height: 50px;
	border-radius: 50%;
	background-color: #2c3e50;
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	margin-right: 20px;
}
.menu-button:hover {
	background-color: rgba(255,255,255,0.5);
}

.menu-button:active{
	background-color: rgba(255,255,255,0.3);
}

</style>
</head>
<div class="top_box d-flex justify-content-between">
	<div class="d-flex justify-content-center align-items-center logo_box">
		<img class="ms-3" src="../images/logo.png">
		<a class="me-3 ms-4" href="${contextPath}/campus/main"><p>OO 대학교</p></a>
	</div>
	<c:if test="${vo == null}">
		<div class="d-flex justify-content-center align-items-center">
			<a class="me-5 ms-2" href="${contextPath}/member/loginForm">로그인</a>
			<a class="me-5 ms-2" href="${contextPath}/member/register">회원가입</a>	
		</div>
	</c:if>
	<c:if test="${vo != null}">
		<div class="d-flex justify-content-center align-items-center">
		    <h5 class="login_name" style="color:white;">${vo.name}님 환영합니다.</h5>
			<a class="me-3 ms-3" href="${contextPath}/member/logout">로그아웃</a>
			<div id="menuToggle" class="menu-button">
				<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="white" viewBox="0 0 16 16">
					<path fill-rule="evenodd" d="M1.5 12a.5.5 0 010-1h13a.5.5 0 010 1h-13zm0-4a.5.5 0 010-1h13a.5.5 0 010 1h-13zm0-4a.5.5 0 010-1h13a.5.5 0 010 1h-13z"/>
				</svg>
			</div>
		</div>
	</c:if>
	<!-- toggle 사이드바 -->
    <div class="side-bar-hidden">
        <div class="menu_side_bar">
        	<c:choose>
        		<c:when test="${vo.role=='STUDENT'}">
	            <h5>학생</h5>
	            <hr>
	            <ul class="planner_menu">
	                <li><a href="${contextPath}/student/main">학생 메인</a></li>
	            </ul>
	            <br>
	            <h5>게시판</h5>
	            <hr>
	            <ul class="planner_menu">
                    <li><a href="${contextPath}/notice/list">공지사항</a></li>
                    <li><a href="${contextPath}/qna/list">질문게시판</a></li>
                    <li><a href="${contextPath}/student/qnaCampusForm">질문남기기</a></li>
	            </ul>
	            </c:when>
        		<c:when test="${vo.role=='PROFESSOR'}">
	            <h5>교수</h5>
	            <hr>
	            <ul class="planner_menu">
	                <li><a href="${contextPath}/professor/main">교수메인</a></li>
	            </ul>
	            <br>
	            <h5>게시판</h5>
	            <hr>
	            <ul class="planner_menu">
                    <li><a href="${contextPath}/notice/list">공지사항</a></li>
                    <li><a href="${contextPath}/qna/list">질문게시판</a></li>
	            </ul>
	            </c:when>
        		<c:when test="${vo.role=='ADMIN'}">
	            <h5>관리자</h5>
	            <hr>
	            <ul class="planner_menu">
	                <li><a href="${contextPath}/admin/memberlist">교내구성원 목록</a></li>
	                <li><a href="${contextPath}/admin/lecturelist">강의등록 목록</a></li>
	                <li><a href="${contextPath}/admin/schedulelist">학사일정 등록</a></li>
	                <li><a href="${contextPath}/admin/noticeform">공지사항 등록</a></li>
	            </ul>
	            <br>
	            <h5>게시판</h5>
	            <hr>
	            <ul class="planner_menu">
                    <li><a href="${contextPath}/notice/list">공지사항</a></li>
                    <li><a href="${contextPath}/qna/list">질문게시판</a></li>
	            </ul>
	            </c:when>
            </c:choose>
        </div>
    </div>
</div>

<script>

	document.addEventListener("DOMContentLoaded", function() {
		const toggleBtn = document.getElementById("menuToggle");       // 햄버거 버튼
		const sideBar = document.querySelector(".side-bar-hidden");    // 사이드바
	
		toggleBtn.addEventListener("click", function() {
			if (sideBar.style.display === "block") {
				sideBar.style.display = "none";
			} else {
				sideBar.style.display = "block";
			}
		});
	});
</script>
</html>