<%@page import="java.util.HashSet"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="professorvo.AttendanceViewVo"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
	// 승인된 교수의 과목
	// subject_code, subject_name, subject_type, open_grade, division, credit, professor_id, professor_name, schedule, current_enrollment, capacity, is_available
	// SUBJ001	     과목1	       전공	         2	         A	       3  	   16	         홍교수	         월 1-2교시	0	               30	     1
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    // 옵션에서 선택한 과목코드와 날자
	
    Vector<AttendanceViewVo> studentList = (Vector<AttendanceViewVo>) request.getAttribute("studentList");
    String subjectCode = request.getParameter("subject_code");
    String selectedDate = request.getParameter("date");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>출결 관리</title>

    <!-- ✅ flatpickr 캘린더 라이브러리 CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

    <style>
        body { margin: 0; padding: 0; font-family: Arial, sans-serif; margin: 20px; }
        .container {width: 90%; margin: 0 auto; padding-top: 20px;}
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background-color: #f0f0f0; }
        .stat-box {
            margin: 15px 0;
            padding: 10px;
            background-color: #f9f9f9;
            border-left: 5px solid #3498db;
        }
        .absent { color: red; font-weight: bold; }
        .late { color: orange; font-weight: bold; }
        .present { color: green; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
	<h2>출결 관리</h2>
	
	<!-- ✅ 과목 및 날짜 선택 폼 -->
	<form method="get" action="<%=contextPath%>/professor/attendancemanage">
		<label>과목 선택:
		    <select name="subject_code" required>
		        <option value="">-- 선택 --</option>
		        <%
		            // HashSet을 이용해 중복을 제거
		            HashSet<String> processedSubjects = new HashSet<>();
		            for (LectureListVo vo : subjectList) {
		                String code = vo.getSubjectCode();
		                if (processedSubjects.contains(code)) continue; // 중복 제거
		                processedSubjects.add(code);
		        %>
		            <!-- 각 과목 코드와 과목명을 동적으로 출력 -->
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
	
	<%
	    if (studentList != null && !studentList.isEmpty()) {
	        int total = studentList.size();
	        int present = 0, late = 0, absent = 0;
	
	        for (AttendanceViewVo vo : studentList) {
	            if ("PRESENT".equals(vo.getStatus())) present++;
	            else if ("LATE".equals(vo.getStatus())) late++;
	            else if ("ABSENT".equals(vo.getStatus())) absent++;
	        }
	%>
	
	<!-- ✅ 출결 통계 박스 -->
	<div class="stat-box">
	    📊 <strong><%= selectedDate %></strong> 출결 통계 — 총원: <%= total %>명,
	    <span class="present">출석: <%= present %></span>명,
	    <span class="late">지각: <%= late %></span>명,
	    <span class="absent">결석: <%= absent %></span>명
	</div>
	
	<!-- ✅ 출결표 폼 -->
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
	            for (AttendanceViewVo vo : studentList) {
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
	
	<% } %>
</div>
</body>
</html>
