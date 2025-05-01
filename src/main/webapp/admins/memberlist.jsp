<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<style>
  a{
  	text-decoration:none;
  }

  .tab-buttons {
    display: flex;
    justify-content: center;
    margin-top: 40px;
  }

  .tab-button {
    padding: 10px 30px;
    border: none;
    background-color: #eee;
    cursor: pointer;
    font-size: 16px;
    border-radius: 5px 5px 0 0;
    margin-right: 5px;
    transition: background-color 0.2s;
  }

  .tab-button.active {
    background-color: rgb(44, 62, 80);
    color: white;
    font-weight: bold;
  }

  .tab-content {
    display: none;
    padding: 20px;
    border: 0px solid #ccc;
    border-top: none;
    background-color: #fff;
  }

  .tab-content.active {
    display: block;
  }

  .member-section {
    max-width: 1000px;
    margin: 0 auto;
    font-family: '맑은 고딕', sans-serif;
  }

  h3 {
    margin:0;
    margin-bottom:10px;
    color: rgb(44, 62, 80);
    border-left: 5px solid rgb(44, 62, 80);
    padding-left: 10px;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 30px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  }

  thead {
    background-color: rgb(44, 62, 80);
    color: white;
  }

  th, td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
  }

  tbody tr:nth-child(even) {
    background-color: #f9f9f9;
  }

  tbody tr:hover {
    background-color: #f1f1f1;
  }
  
  .page_btn {
	background-color: rgb(44, 62, 80);
	color: white;
	border: none;
	padding: 5px 10px;
	margin: 0 2px;
	cursor: pointer;
	border-radius: 3px;
  }
  
  .page_btn.active {
	font-weight: bold;
	text-decoration: underline;
	background-color: #2c3e50;
  }
</style>
</head>
<body>
	<div class="tab-buttons">
  		<button class="tab-button ${activeTab eq 'student' or activeTab == null ? 'active' : ''}" onclick="showTab('student')">학생 목록</button>
		<button class="tab-button ${activeTab eq 'professor' ? 'active' : ''}" onclick="showTab('professor')">교수 목록</button>
	</div>

<div class="member-section">

  <div id="student" class="tab-content ${activeTab eq 'student' or activeTab == null ? 'active' : ''}">
  	<div class="d-flex justify-content-between">
  		<div class="d-flex justify-content-center align-items-center"><h3>학생 목록</h3></div>
    	<div class="d-flex justify-content-center align-items-center gap-1" style="margin-bottom:10px;">
    		<select id="gradeFilter">
    			<option value="">학년</option>
    			<option value="1">1학년</option>
    			<option value="2">2학년</option>
    			<option value="3">3학년</option>
    			<option value="4">4학년</option>
    		</select>
    		<select id="statusFilter">
    			<option value="">전체</option>
    			<option value="재학">재학</option>
    			<option value="휴학">휴학</option>
    		</select>
    	</div>
    </div>
    <table>
      <thead>
        <tr>
          <th>사용자번호</th>
          <th>이메일</th>
          <th>이름</th>
          <th>비밀번호</th>
          <th>학번</th>
          <th>학과</th>
          <th>학년</th>
          <th>상태</th>
        </tr>
      </thead>
      <tbody id="student-table-body">
        <c:forEach var="student" items="${studentlist}">
          <tr>
            <td>${student.user_id}</td>
            <td>${student.email}</td>
            <td>${student.name}</td>
            <td>${student.password}</td>
            <td>${student.student_number}</td>
            <td>${student.department}</td>
            <td>${student.grade}</td>
            <td>${student.status}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    
    <!-- 기본 학생 페이징 버튼 -->
    <div id="default-pagination" style="text-align: center; margin-top: 20px;">
	  <c:forEach begin="1" end="${studentTotalPage}" var="i">
	    <a href="${pageContext.request.contextPath}/admin/memberlist?studentPage=${i}&professorPage=${professorPage}&activeTab=student">
	      <button class="page_btn ${i == studentPage ? 'active' : ''}">${i}</button>
	    </a>
	  </c:forEach>
	</div>
	
    <!-- Ajax 학생 페이징 버튼 -->
    <div style="text-align: center; margin-top: 20px;">
		<div id="student-pagination" style="text-align: center; margin-top: 20px; display: none;"></div>
	</div>
  </div>

  <div id="professor" class="tab-content ${activeTab eq 'professor' ? 'active' : ''}">
    <h3>교수 목록</h3>
    <table>
      <thead>
        <tr>
          <th>사용자번호</th>
          <th>이메일</th>
          <th>이름</th>
          <th>비밀번호</th>
          <th>교수번호</th>
          <th>학과</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="professor" items="${professorlist}">
          <tr>
            <td>${professor.user_id}</td>
            <td>${professor.email}</td>
            <td>${professor.name}</td>
            <td>${professor.password}</td>
            <td>${professor.professor_number}</td>
            <td>${professor.department}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  	<!-- 교수 페이징 버튼 -->
  	<div style="text-align: center; margin-top: 20px;">
	  <c:forEach begin="1" end="${professorTotalPage}" var="i">
		<a href="${pageContext.request.contextPath}/admin/memberlist?studentPage=${studentPage}&professorPage=${i}&activeTab=professor">
	      <button class="page_btn ${i == professorPage ? 'active' : ''}">${i}</button>
	    </a>
	  </c:forEach>
	</div>
  </div>

</div>

<script>
	console.log("테이블 tbody 존재 여부:", document.getElementById("student-table-body"));
  function showTab(tabId) {
    const allTabs = document.querySelectorAll(".tab-content");
    const allButtons = document.querySelectorAll(".tab-button");

    allTabs.forEach(tab => tab.classList.remove("active"));
    allButtons.forEach(btn => btn.classList.remove("active"));

    document.getElementById(tabId).classList.add("active");
    event.target.classList.add("active");
  }
  
  function loadFilteredStudents(page = 1) {
	    const grade = $('#gradeFilter').val();
	    const status = $('#statusFilter').val();
	    const pageSize = 10;

	    $.ajax({
	      url: '${pageContext.request.contextPath}/admin/filterStudent',
	      type: 'GET',
	      data: {
	        grade: grade,
	        status: status,
	        page: page,
	        pageSize: pageSize
	      },
	      success: function(response) {
	        let tbody = '';
	        response.studentList.forEach(function(s) {
	          tbody += "<tr>" +
	            "<td>" + s.user_id + "</td>" +
	            "<td>" + s.email + "</td>" +
	            "<td>" + s.name + "</td>" +
	            "<td>" + s.password + "</td>" +
	            "<td>" + s.student_number + "</td>" +
	            "<td>" + s.department + "</td>" +
	            "<td>" + s.grade + "</td>" +
	            "<td>" + s.status + "</td>" +
	          "</tr>";
	        });
	        $('#student-table-body').html(tbody);

	        const totalPage = Math.ceil(response.totalCount / pageSize);
	        let paginationHtml = '';
	        for (let i = 1; i <= totalPage; i++) {
	          paginationHtml += "<button class='page_btn " + (i === page ? "active" : "") +
	            "' onclick='loadFilteredStudents(" + i + ")'>" + i + "</button>";
	        }
	        $('#student-pagination').html(paginationHtml);
	        
	        $('#default-pagination').hide();       // 서버용 페이징 숨김
	        $('#student-pagination').show();      // Ajax 페이징 보여줌
	      },
	      error: function() {
	        alert("학생 목록 불러오기 실패");
	      }
	    });
	  }

	  $('#gradeFilter, #statusFilter').change(function() {
	    loadFilteredStudents(1);
	  });
</script>
</body>
</html>