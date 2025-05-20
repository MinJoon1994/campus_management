<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>성적 관리</title>

<style>
	 body {
	    margin: 0;  /* 화면을 꽉 차게 하기위한 기본설정 */
	    padding: 0;
	    font-family: Arial, sans-serif;
	 }
    .container {
        width: 90%;         /* 전체 폭의 70% 사용 (양쪽 15% 여백) */
        margin: 0 auto;     /* 중앙 정렬 */
        padding-top: 20px;
    }
    .grade-buttons {
	    display: flex;
	    justify-content: center;  /* 가로 중앙 정렬 */
	    align-items: center;      /* 세로 중앙 정렬 (필요 시) */
	    gap: 60px;                /* 버튼 사이 간격 */
	    margin-top: 180px;        /* 위에서 약간 띄움 */
	}
    .grade-buttons button {
        width: 300px;
        height: 225px;
        font-size: 32px;
        font-weight: bold;
        color: white;
        border: none;
        border-radius: 20px;
        cursor: pointer;
        box-shadow: 0 6px 12px rgba(0,0,0,0.2);
        transition: transform 0.2s, background-color 0.3s;
    }

    .grade-insert {
        background-color: #27ae60; /* 녹색 */
    }

    .grade-insert:hover {
        background-color: #2ecc71;
        transform: translateY(-5px);
    }

    .grade-update {
        background-color: #2980b9; /* 파랑 */
    }

    .grade-update:hover {
        background-color: #3498db;
        transform: translateY(-5px);
    }
</style>
</head>
<body>

<div id="container">
	<div class="grade-buttons">
	    <button class="grade-insert" onclick="location.href='<%=contextPath%>/professor/gradesinsert'">성적 입력</button>
	    <button class="grade-update" onclick="location.href='<%=contextPath%>/professor/gradesupdate'">성적 수정</button>
	</div>
</div>

</body>
</html>
