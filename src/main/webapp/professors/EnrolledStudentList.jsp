<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.EnrolledStudentVo"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    request.setCharacterEncoding("UTF-8"); // 한글 처리
    String contextPath = request.getContextPath();
    Vector<EnrolledStudentVo> list = (Vector<EnrolledStudentVo>) request.getAttribute("enrolledStudentList");
    Gson gson = new Gson();

	int pageSize = 10; // 한 페이지에 출력할 수 있는 학생 수 (1페이지에 표시할 항목 수)
	int pageNum = 1; // 기본 페이지 번호 (페이지 번호가 없으면 1로 설정)
	
	// 요청 파라미터에서 페이지 번호(pageNum)를 가져오고, 값이 있으면 해당 값으로 설정
	if (request.getParameter("pageNum") != null) {
	    pageNum = Integer.parseInt(request.getParameter("pageNum")); // 페이지 번호를 정수로 변환
	}
	
	// 시작 행(startRow)은 (현재 페이지 번호 - 1) * 페이지 크기 (0부터 시작하므로)
	int startRow = (pageNum - 1) * pageSize; 
	
	// 끝 행(endRow)은 startRow + pageSize로 설정. list의 크기를 초과하지 않도록 처리
	// Math.min을 사용하여 list.size()가 끝 인덱스를 초과하지 않게 보장
	int endRow = Math.min(startRow + pageSize, list != null ? list.size() : 0); 
	
	// pageList는 list의 부분 목록을 추출. startRow부터 endRow까지의 구간을 subList로 가져옴
	// list가 null이 아니면 해당 부분을 잘라서 가져오고, null이면 빈 목록을 반환
	List<EnrolledStudentVo> pageList = ((list != null) ? list.subList(startRow, endRow) : new Vector<EnrolledStudentVo>());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수강신청 학생명단</title>
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
    }
    .container {
        width: 90%; 
        margin: 0 auto; /* 중앙 정렬 */
        padding-top: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th, td {
        text-align: center;
    }
   	th {
        background-color: #2c3e50;
        color: white;
    }
    .pagination a {
        margin: 0 5px;
        text-decoration: none;
    }
    .pagination strong {
        margin: 0 5px;
        font-weight: bold;
    }
</style>
</head>
<body>
<div class="container">
    <h2 style="text-align: center; padding-bottom: 20px;">수강신청 학생명단</h2>
	<table border="1" cellpadding="8" cellspacing="0">
	    <tr>
	        <th>교수명</th>
	        <th>과목코드</th>
	        <th>과목명</th>
	        <th>학생ID</th>
	        <th>학생이름</th>
	        <th>학번</th>
	        <th>학과</th>
	    </tr>
	
	   <% 
		// pageList가 null이 아니고 비어있지 않은지 확인
		if (pageList != null && !pageList.isEmpty()) { 
		    // pageList에 있는 각 학생 정보를 순차적으로 처리
		    for (EnrolledStudentVo vo : pageList) {
		        // EnrolledStudentVo 객체를 JSON 문자열로 변환
		        String json = gson.toJson(vo); // 자바 객체 -> JSON 문자열
		        // JSON 문자열을 URL-safe한 문자열로 인코딩
		        String encodeJson = URLEncoder.encode(json, "utf-8"); // JSON 문자열 -> URL 안전한 문자열
		%>
		      <!-- 학생 정보를 테이블 행에 표시 -->
		      <tr>
		          <td><%= vo.getProfessorNumber() %></td> <!-- 교수 번호 표시 -->
		          <td><%= vo.getSubjectCode() %></td>    <!-- 과목 코드 표시 -->
		          <td><%= vo.getSubjectName() %></td>    <!-- 과목 이름 표시 -->
		          <td><%= vo.getStudentId() %></td>      <!-- 학생 ID 표시 -->
		          <td><%= vo.getStudentName() %></td>    <!-- 학생 이름 표시 -->
		          <td><%= vo.getStudentNumber() %></td>  <!-- 학번 표시 -->
		          <td><%= vo.getDepartment() %></td>     <!-- 학과 표시 -->
		      </tr>
		<% 
		    }
		} else {
		%>
		  <!-- 조회된 학생이 없을 때 메시지 표시 -->
		  <tr>
		      <td colspan="7" style="text-align:center;">조회된 학생이 없습니다.</td> <!-- 학생이 없는 경우 메시지 -->
		  </tr>
		<% 
		}
		%>
	</table>

	<div class="pagination" style="text-align:center; margin-top:20px;">
	<%
	    // list가 null이 아니고 비어있지 않은 경우에만 페이지네이션 처리
	    if (list != null && !list.isEmpty()) {
	        // 전체 페이지 수 계산 (list.size()는 전체 데이터 수)
	        int totalPage = (int)Math.ceil((double)list.size() / pageSize); 
	        int pageBlock = 5; // 한 번에 보여줄 페이지 수
	        // 현재 페이지 번호를 기준으로 시작 페이지 계산
	        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
	        // 끝 페이지 계산 (현재 페이지가 마지막 페이지가 아닐 경우에만 해당)
	        int endPage = startPage + pageBlock - 1;
	        // 만약 endPage가 전체 페이지 수를 초과하면 endPage는 totalPage로 설정
	        if (endPage > totalPage) endPage = totalPage;
	
	        // 시작 페이지가 1보다 클 경우, 이전 페이지로 이동하는 버튼 생성
	        if (startPage > 1) {
	%>
	            <!-- 이전 페이지로 이동하는 버튼 -->
	            <a href="?pageNum=<%=startPage - 1%>">&#9664;</a>
	<%
	        }
	        // startPage부터 endPage까지의 페이지 번호를 출력
	        for (int i = startPage; i <= endPage; i++) {
	            // 현재 페이지가 선택된 페이지인 경우 강조 표시
	            if (i == pageNum) {
	%>
	                <!-- 현재 페이지 번호는 강조 처리 -->
	                <strong><%=i%></strong>
	<%
	            } else {
	%>
	                <!-- 현재 페이지가 아닌 경우 링크로 출력 -->
	                <a href="?pageNum=<%=i%>"><%=i%></a>
	<%
	            }
	        }
	        // endPage가 totalPage보다 작을 경우, 다음 페이지로 이동하는 버튼 생성
	        if (endPage < totalPage) {
	%>
	            <!-- 다음 페이지로 이동하는 버튼 -->
	            <a href="?pageNum=<%=endPage + 1%>">&#9654;</a>
	<%
	        }
	    }
	%>
	</div>

</div>
</body>
</html>