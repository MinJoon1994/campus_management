<%@page import="java.util.ArrayList"%>
<%@page import="student.vo.StudentGradeVO"%>
<%@page import="java.util.List"%>
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
	    <%
	        List<StudentGradeVO> studentGradeVo = (List<StudentGradeVO>) request.getAttribute("list");
	
	        int pageSize = 10; // 한 페이지에 출력할 강의 수
	        int pageNum = 1;   // 기본 페이지
	        if (request.getParameter("pageNum") != null) {
	            pageNum = Integer.parseInt(request.getParameter("pageNum"));
	        }
	
	        int startRow = (pageNum - 1) * pageSize;
	        int endRow = Math.min(startRow + pageSize, studentGradeVo != null ? studentGradeVo.size() : 0);
	        List<StudentGradeVO> pageList = (studentGradeVo != null) ? studentGradeVo.subList(startRow, endRow) : new ArrayList<StudentGradeVO>();
	        request.setAttribute("pageList", pageList);
     	%>
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
	                    <c:forEach var="vo" items="${requestScope.pageList}">
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
	    	<div class="pagination" style="text-align:center; margin-top:20px;">
			<%
			    if (studentGradeVo != null && !studentGradeVo.isEmpty()) {
			        int totalPage = (int)Math.ceil((double)studentGradeVo.size() / pageSize); 
			        int pageBlock = 5; // 한 번에 보여줄 페이지 수
			        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
			        int endPage = startPage + pageBlock - 1;
			        if (endPage > totalPage) endPage = totalPage;
			
			        if (startPage > 1) {
			%>
			            <a href="?pageNum=<%=startPage - 1%>">&#9664;</a>
			<%
			        }
			        for (int i = startPage; i <= endPage; i++) {
			            if (i == pageNum) {
			%>
			                <strong><%=i%></strong>
			<%
			            } else {
			%>
			                <a href="?pageNum=<%=i%>"><%=i%></a>
			<%
			            }
			        }
			        if (endPage < totalPage) {
			%>
			            <a href="?pageNum=<%=endPage + 1%>">&#9654;</a>
			<%
			        }
			    }
			%>
			</div>
    </div>
</body>
</html>
