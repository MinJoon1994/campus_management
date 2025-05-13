<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	request.setCharacterEncoding("UTF-8"); // 한글 처리
	String contextPath = request.getContextPath();
	Vector<LectureListVo> list = (Vector<LectureListVo>) request.getAttribute("v");
	Gson gson = new Gson();

	int pageSize = 10; // 한 페이지에 출력할 강의 수
	int pageNum = 1;   // 기본 페이지
	if (request.getParameter("pageNum") != null) {
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
	}

	int startRow = (pageNum - 1) * pageSize;
	int endRow = Math.min(startRow + pageSize, list != null ? list.size() : 0);
	List<LectureListVo> pageList = (list != null) ? list.subList(startRow, endRow) : new Vector<LectureListVo>();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>강의 목록</title>
	
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
	    table {
	        width: 100%;
	        border-collapse: collapse; /* 셀 테투리와 셀 간 간격을 없애고 테두리들을 깔끔하게 붙임 */
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
	<h2 style="text-align: center; padding-bottom: 20px;">강의 목록</h2>
	<table border="1" cellpadding="8"> <!-- 글자와 셀 테두리 사이의 여백 -->
		<tr>
			<th>과목코드</th>
			<th>과목명</th>
			<th>이수구분</th>
			<th>개설학년</th>
			<th>분배</th>
			<th>학점</th>
			<th>교수</th>
			<th>시간</th>
			<th>신청</th>
			<th>정원</th>
			<th>강의계획서</th>
		</tr>

		<%
		// 리스트 객체가 아예 없는 경우 && 리스트가 있어도 안에 데이터 없는 경우
		if (pageList != null && !pageList.isEmpty()) {
			for (LectureListVo vo : pageList) {
				String json = gson.toJson(vo); // 자바 객체 -> JSON 문자열
				System.out.println("json : " + gson);
				String encodeJson = URLEncoder.encode(json, "utf-8"); // JSON 문자열 -> URL 안전한 문자열
				System.out.println("encodeJson :  " + encodeJson);
				
				Boolean tempBoolean = true;
				if(vo.getisAvailable() == false) tempBoolean = false;
				if(vo.getisAvailable() == true) {
		%>
				<tr>
					<td><%=vo.getSubjectCode()%></td> 
					<td><%=vo.getSubjectName()%></td>
					<td><%=vo.getSubjectType()%></td>
					<td><%=vo.getOpenGrade()%></td>
					<td><%=vo.getDivision()%></td>
					<td><%=vo.getCredit()%></td>
					<td><%=vo.getProfessor()%></td>
					<td><%=vo.getSchedule()%></td>
					<td><%=vo.getEnrollment()%></td>
					<td><%=vo.getCapacity()%></td>
					<td>
						<button id="LecturePlanBtn" 
							onclick="addLecturePlan('<%= encodeJson %>', '<%=vo.getSubjectCode()%>')">강의계획서 등록
						</button>
					</td>
				</tr>
		<%
				}
				if(tempBoolean = false) {
		%>
					<tr>
						<td colspan="11" style="text-align:center;">조회된 강의가 없습니다.</td>
					</tr>
		<%
				}
			}
		} else {
		%>
			<tr>
				<td colspan="11" style="text-align:center;">조회된 강의가 없습니다.</td>
			</tr>
		<%
		}
		%>
	</table>

	<div class="pagination" style="text-align:center; margin-top:20px;">
	<%
		if (list != null && !list.isEmpty()) {
			int totalPage = (int)Math.ceil((double)list.size() / pageSize);
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

<script>
	function addLecturePlan(subjectList, subjectCode) {
	    const url = "<%=contextPath%>/professor/lectures/lectureplan?subjectList=" + subjectList + "&subjectCode=" + subjectCode;
	    window.open(url, 'lecturePlanPopup', "width=700,height=1000,left=200,top=500,resizable=no,scrollbars=yes");
	}
</script>
</body>
</html>