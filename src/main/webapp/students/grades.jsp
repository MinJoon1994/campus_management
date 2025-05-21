<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>성적 조회</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding:0;}
        .container {width: 90%; margin: 0 auto; padding-top: 10px;}
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background-color: #2c3e50; color: white; }
    </style>
</head>
<body>
	<div class="container"> 
	    <h2 style="text-align:center;">나의 성적 조회</h2>
	    <table>
	        <thead>
	            <tr>
	                <th>학생 ID</th>
	                <th>이름</th>
	                <th>과목 코드</th>
	                <th>과목명</th>
	                <th>점수</th>
	                <th>등급</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
	                <c:when test="${not empty list}">
	                    <c:forEach var="vo" items="${list}">
	                        <tr>
	                            <td>${vo.studentId}</td>
	                            <td>${vo.studentName}</td>
	                            <td>${vo.subjectCode}</td>
	                            <td>${vo.subjectName}</td>
	                            <td>${vo.score}</td>
	                            <td>${vo.grade}</td>
	                        </tr>
	                    </c:forEach>
	                </c:when>
	                <c:otherwise>
	                    <tr>
	                        <td colspan="6">조회된 성적이 없습니다.</td>
	                    </tr>
	                </c:otherwise>
	            </c:choose>
	        </tbody>
	    </table>
    </div>
</body>
</html>
