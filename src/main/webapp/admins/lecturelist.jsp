<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    table.lecture-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 30px;
        font-size: 15px;
    }

    .lecture-table th, .lecture-table td {
        padding: 12px;
        border: 1px solid #ccc;
        text-align: center;
    }

    .lecture-table th {
        background-color: #2c3e50;
        color: white;
    }

    .lecture-table tr:nth-child(even) {
        background-color: #f5f5f5;
    }

    .lecture-table button {
        padding: 5px 12px;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
    }

    .btn-approve {
        background-color: #27ae60;
        color: white;
    }

    .btn-deny {
        background-color: #c0392b;
        color: white;
    }

    .btn-approve:hover {
        background-color: #219150;
    }

    .btn-deny:hover {
        background-color: #a83222;
    }
</style>
</head>
<body>
<div class="container">
	
	<h2>강의 신청 목록</h2>
	
	<table class="lecture-table">
	    <thead>
	        <tr>
	            <th>과목코드</th>
	            <th>과목명</th>
	            <th>유형</th>
	            <th>학년</th>
	            <th>분반</th>
	            <th>학점</th>
	            <th>교수명</th>
	            <th>시간표</th>
	            <th>정원</th>
	            <th>승인 여부</th>
	            <th>처리</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="lecture" items="${lectureList}">
	            <tr>
	                <td>${lecture.subject_code}</td>
	                <td>${lecture.subject_name}</td>
	                <td>${lecture.subject_type}</td>
	                <td>${lecture.open_grade}</td>
	                <td>${lecture.division}</td>
	                <td>${lecture.credit}</td>
	                <td>${lecture.professor_name}</td>
	                <td>${lecture.schedule}</td>
	                <td>${lecture.capacity}</td>
	                <td>
	                    <c:choose>
	                        <c:when test="${lecture.is_available}">
	                            승인됨
	                        </c:when>
	                        <c:otherwise>
	                            거부됨
	                        </c:otherwise>
	                    </c:choose>
	                </td>
	                <td>
	                    <form method="post" action="${contextPath}/admin/approve" style="display:inline;">
	                        <input type="hidden" name="subject_code" value="${lecture.subject_code}">
	                        <button type="submit" class="btn-approve">승인</button>
	                    </form>
	                    <form method="post" action="${contextPath}/admin/reject" style="display:inline;">
	                        <input type="hidden" name="subject_code" value="${lecture.subject_code}">
	                        <button type="submit" class="btn-deny">거부</button>
	                    </form>
	                </td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>

</div>
</body>
</html>