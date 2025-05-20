<%@page import="java.util.List"%>
<%@page import="java.util.HashSet"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="professorvo.AttendanceViewVo"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String contextPath = request.getContextPath();
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    Vector<AttendanceViewVo> studentList = (Vector<AttendanceViewVo>) request.getAttribute("studentList");

    String subjectCode = request.getParameter("subject_code");
    String selectedDate = request.getParameter("date");

    // 페이지 계산
    int pageSize = 10;
    int pageNum = 1;
    if (request.getParameter("pageNum") != null) {
        pageNum = Integer.parseInt(request.getParameter("pageNum"));
    }

    int startRow = (pageNum - 1) * pageSize;
    int endRow = Math.min(startRow + pageSize, studentList != null ? studentList.size() : 0);
    List<AttendanceViewVo> pageList = (studentList != null) ? studentList.subList(startRow, endRow) : new Vector<AttendanceViewVo>();

    // 출결 통계
    int present = 0, late = 0, absent = 0;
    if (studentList != null) {
        for (AttendanceViewVo vo : studentList) {
            if (vo.getStatus() == null || "PRESENT".equals(vo.getStatus())) present++;
            else if ("LATE".equals(vo.getStatus())) late++;
            else if ("ABSENT".equals(vo.getStatus())) absent++;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>출결 관리</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
    <style>
        body { margin: 0; padding: 0; font-family: Arial, sans-serif; margin: 20px; }
        .container { width: 90%; margin: 0 auto; }

        h2 { text-align: center; }

        form { margin-bottom: 20px; }

        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background-color: #f0f0f0; }

        .stat-box {
            margin: 15px 0;
            padding: 10px;
            background-color: #f9f9f9;
            border-left: 5px solid #3498db;
            font-size: 16px;
        }

        .absent { color: red; font-weight: bold; }
        .late { color: orange; font-weight: bold; }
        .present { color: green; font-weight: bold; }

        .pagination {
            text-align: center;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #2c3e50;
            font-weight: bold;
        }
        .pagination strong {
            margin: 0 5px;
            color: #3498db;
        }

        button {
            background-color: #2c3e50;
            color: white;
            border: none;
            padding: 8px 14px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #1a242f;
        }

        select, input[type="text"] {
            padding: 6px;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>출결 관리</h2>

    <!-- 과목 및 날짜 선택 -->
    <form method="get" action="<%=contextPath%>/professor/attendancemanage">
        <label>과목 선택:
            <select name="subject_code" required>
                <option value="">-- 선택 --</option>
                <%
                    HashSet<String> processedSubjects = new HashSet<>();
                    for (LectureListVo vo : subjectList) {
                        String code = vo.getSubjectCode();
                        if (processedSubjects.contains(code)) continue;
                        processedSubjects.add(code);
                %>
                    <option value="<%=code%>" <%= code.equals(subjectCode) ? "selected" : "" %>>
                        <%= vo.getSubjectName() %> (<%= vo.getDivision() %>)
                    </option>
                <%
                    }
                %>
            </select>
        </label>
        &nbsp;&nbsp;
        <label>날짜 선택:
            <input type="text" id="datePicker" name="date" value="<%= selectedDate != null ? selectedDate : "" %>" required>
        </label>
        <button type="submit">조회</button>
    </form>

    <script>
        flatpickr("#datePicker", {
            dateFormat: "Y-m-d",
            locale: "ko"
        });
    </script>

    <% if (studentList != null && !studentList.isEmpty()) { %>
        <!-- 출결 통계 박스 -->
        <div class="stat-box">
            📊 <strong><%= selectedDate %></strong> 출결 통계 — 총원: <%= studentList.size() %>명,
            <span class="present">출석: <%= present %></span>명,
            <span class="late">지각: <%= late %></span>명,
            <span class="absent">결석: <%= absent %></span>명
        </div>

        <!-- 출결 수정 폼 -->
        <form method="post" action="<%=contextPath%>/professor/attendanceedit">
            <input type="hidden" name="subject_code" value="<%=subjectCode%>">
            <input type="hidden" name="date" value="<%=selectedDate%>">

            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>학생명</th>
                        <th>출결현황</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int i = 1;
                        for (AttendanceViewVo vo : pageList) {
                            String status = vo.getStatus() != null ? vo.getStatus() : "PRESENT";
                    %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><%= vo.getStudentName() %></td>
                        <td>
                            <label class="present"><input type="radio" name="status_<%=vo.getEnrollmentId()%>" value="PRESENT" <%= "PRESENT".equals(status) ? "checked" : "" %>> 출석</label>
                            <label class="late"><input type="radio" name="status_<%=vo.getEnrollmentId()%>" value="LATE" <%= "LATE".equals(status) ? "checked" : "" %>> 지각</label>
                            <label class="absent"><input type="radio" name="status_<%=vo.getEnrollmentId()%>" value="ABSENT" <%= "ABSENT".equals(status) ? "checked" : "" %>> 결석</label>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <div style="text-align:right; margin-top:10px;">
                <button type="submit">저장</button>
            </div>
        </form>

        <!-- 페이지네이션 -->
		<div class="pagination">
		<%
		    int totalPage = (int)Math.ceil((double)studentList.size() / pageSize);
		    int pageBlock = 5;
		    int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
		    int endPage = startPage + pageBlock - 1;
		    if (endPage > totalPage) endPage = totalPage;
		
		    // ◀ 이전 블록
		    if (startPage > 1) {
		%>
		    <a href="?pageNum=<%=startPage - 1%>&subject_code=<%=subjectCode%>&date=<%=selectedDate%>">&#9664;</a>
		<%
		    }
		
		    // 페이지 번호 출력
		    for (i= startPage; i <= endPage; i++) {
		        if (i == pageNum) {
		%>
		    <strong><%=i%></strong>
		<%
		        } else {
		%>
		    <a href="?pageNum=<%=i%>&subject_code=<%=subjectCode%>&date=<%=selectedDate%>"><%=i%></a>
		<%
		        }
		    }
		
		    // ▶ 다음 블록
		    if (endPage < totalPage) {
		%>
		    <a href="?pageNum=<%=endPage + 1%>&subject_code=<%=subjectCode%>&date=<%=selectedDate%>">&#9654;</a>
		<%
		    }
		%>
		</div>
    <% } else if (subjectCode != null && selectedDate != null) { %>
    <% if (studentList == null) { %>
        <p style="text-align:center; margin-top:20px;">아직 수강신청한 학생이 없습니다.</p>
    <% } else if (studentList.isEmpty()) { %>
        <p style="text-align:center; margin-top:20px;">아직 수강신청한 학생이 없습니다.</p>
    <% } %>
<% } %>

</div>
</body>
</html>
