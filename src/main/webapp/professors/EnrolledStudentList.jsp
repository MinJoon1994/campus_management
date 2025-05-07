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

    int pageSize = 10; // 한 페이지에 출력할 수강신청 학생 수
    int pageNum = 1; // 기본 페이지
    if (request.getParameter("pageNum") != null) {
        pageNum = Integer.parseInt(request.getParameter("pageNum"));
    }

    int startRow = (pageNum - 1) * pageSize;
    int endRow = Math.min(startRow + pageSize, list != null ? list.size() : 0);
    List<EnrolledStudentVo> pageList = (list != null) ? list.subList(startRow, endRow) : new Vector<EnrolledStudentVo>();
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
    }
    .container {
        width: 70%; /* 전체 폭의 70% 사용 (양쪽 15% 여백) */
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
    <h2>수강신청 학생명단</h2>
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
        if (pageList != null && !pageList.isEmpty()) {
            for (EnrolledStudentVo vo : pageList) {
                String json = gson.toJson(vo); // 자바 객체 -> JSON 문자열
                String encodeJson = URLEncoder.encode(json, "utf-8"); // JSON 문자열 -> URL 안전한 문자열
        %>
                <tr>
                    <td><%=vo.getProfessorNumber()%></td>
                    <td><%=vo.getSubjectCode()%></td>
                    <td><%=vo.getSubjectName()%></td>
                    <td><%=vo.getStudentId()%></td>
                    <td><%=vo.getStudentName()%></td>
                    <td><%=vo.getStudentNumber()%></td>
                    <td><%=vo.getDepartment()%></td>
                </tr>
        <%
            }
        } else {
        %>
            <tr>
                <td colspan="7" style="text-align:center;">조회된 학생이 없습니다.</td>
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
</body>
</html>