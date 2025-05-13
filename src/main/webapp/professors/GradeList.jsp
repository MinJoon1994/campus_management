<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.GradeVo"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Vector<GradeVo> gradeList = (Vector<GradeVo>) request.getAttribute("gradeList");
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    Gson gson = new Gson();
    String gradeJson = gson.toJson(gradeList);
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script>
	    const gradeList = <%= gradeJson %>;
	    console.log(gradeList);
		/*
			{
			    "subjectCode": "SUBJ002",
			    "subjectName": "과목2",
			    "studentId": 1001,
			    "studentName": "학생1001",
			    "studentNumber": "S1001",
			    "department": "컴퓨터공학과",
			    "enrollmentId": 2001,
			    "openGrade": 2,
			    "score": 95.12,
			    "grade": "A+"
			}
		*/
	    function searchGrades() {
	        const subjectCode = document.getElementById("subject_code").value;
	        const openGrade = document.getElementById("open_grade").value;
	        const studentName = document.getElementById("student_name").value.trim();
	
	        const tbody = document.getElementById("gradeBody");
	        tbody.innerHTML = ""; // 초기화
	
	        const filtered = gradeList.filter(vo => {
	            const matchSubject = !subjectCode || vo.subjectCode === subjectCode;
	            const matchGrade = !openGrade || vo.openGrade == openGrade;
	            const matchName = !studentName || vo.studentName.includes(studentName);
	            return matchSubject && matchGrade && matchName;
	        });
	
	        if (filtered.length === 0) {
	            tbody.innerHTML = "<tr><td colspan='10' style='text-align:center;'>검색 결과 없음</td></tr>";
	            return;
	        }
	
	        filtered.forEach(vo => {
	        	const tr = document.createElement("tr");
	        	const row = `
	        	    <tr>
	        	        <td>\${vo.subjectCode}</td>
	        	        <td>\${vo.subjectName}</td>
	        	        <td>\${vo.openGrade}</td>
	        	        <td>\${vo.studentNumber}</td>
	        	        <td>\${vo.studentName}</td>
	        	        <td>\${vo.department}</td>
	        	        <td>\${vo.score != null ? vo.score : '-'}</td>
	        	        <td>\${vo.grade != null ? vo.grade : '-'}</td>
	        	    </tr>
	        	`;
	
	        	tr.innerHTML = row;
	        	tbody.appendChild(tr);
	        });
	    }
	</script>
	
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
	</style>
</head>
<body>
	<div class="container">
		<div class="container" style="padding: 30px;">
		    <h2 style="text-align: center; padding-bottom: 20px;">성적 조회</h2>
		
		    <form onsubmit="return false;">
		        <label>과목 선택:
		            <select id="subject_code">
		                <option value="">-- 전체 과목 --</option>
		                <% for (LectureListVo vo : subjectList) { %>
		                    <option value="<%=vo.getSubjectCode()%>"><%=vo.getSubjectName()%></option>
		                <% } %>
		            </select>
		        </label>
		        &nbsp;&nbsp;
		
		        <label>개설 학년:
		            <select id="open_grade">
		                <option value="">-- 전체 학년 --</option>
		                <option value="1">1학년</option>
		                <option value="2">2학년</option>
		                <option value="3">3학년</option>
		                <option value="4">4학년</option>
		            </select>
		        </label>
		        &nbsp;&nbsp;
		
		        <label>학생 이름: <input type="text" id="student_name" /></label>
		
		        <button type="button" onclick="searchGrades()">검색</button>
		    </form>
		
		    <br />
		
		    <table border="1" cellpadding="10" cellspacing="0" width="100%">
		        <thead style="background-color: #2c3e50; color: white;">
		            <tr>
		                <th>과목코드</th>
		                <th>과목명</th>
		                <th>학년</th>
		                <th>학번</th>
		                <th>이름</th>
		                <th>학과</th>
		                <th>점수</th>
		                <th>등급</th>
		            </tr>
		        </thead>
		        <tbody id="gradeBody">
		            <tr><td colspan="10" style="text-align:center;">검색 결과 없음</td></tr>
		        </tbody>
		    </table>
		</div>
	</div>
</body>
</html>


