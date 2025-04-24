<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<meta charset="UTF-8">
<title>회원가입</title>
  <style>
  <style>
     form {
      width: 400px;
      margin: 50px auto;
      font-family: Arial, sans-serif;
    }
    label {
      display: block;
      margin: 10px 0 5px;
      font-weight: bold;
    }
    input, select {
      width: 100%;
      padding: 8px;
      box-sizing: border-box;
    }
    button {
      margin-top: 20px;
      padding: 10px;
      width: 100%;
      background-color: #2c3e50;
      color: white;
      border: none;
      cursor: pointer;
      font-size: 16px;
    }
    button:hover {
      background-color: #34495e;
    }
    .hidden { display: none; }
    
    .button-wrapper {
	    display: flex;
	    flex-direction: column;
	    justify-content: flex-end;
	    height: 100px; /* 원하는 높이로 조절 */
	}
    
    .check_btn{
    	background-color: #2c3e50;
    	margin-bottom: 15px;
    	margin-right: 10px;
    }
  </style>
  <script>
	  function onRoleChange(select) {
		    const studentFields = document.getElementById('student-fields');
		    const professorFields = document.getElementById('professor-fields');
	
		    // 일단 전부 숨기기
		    studentFields.classList.add('hidden');
		    professorFields.classList.add('hidden');
	
		    // 선택된 역할에 따라 보여주기
		    if (select.value === 'student') {
		      studentFields.classList.remove('hidden');
		    } else if (select.value === 'professor') {
		      professorFields.classList.remove('hidden');
		    }
		    // staff는 아무 것도 안 보임
		  }
	  
	  function checkEmail() {
		  
		  	$.ajax(
  				{
  				  url: "http://localhost:8090/campus_management/member/checkEmail",
  				  type: "post", //요청 방식 POST로 설정
  				  async: true, //true는 비동기 요청, false는 동기 요청  중 하나 
  				  data: { email : $("#email").val() }, //MemberController서버페이지에 요청할 값 설정	
  				  dataType: "text", //MemberController서버페이지로 부터  예상 응답받을 데이터 종류 설정
  				  //요청 통신에 성공했을때
  				  //success속성에 설정된 콜백함수가 자동으로 호출되어
  				  //data매개변수로 MemberContoller서버페이지가 응답한 데이터가 넘어 옴
  				  success:function(data,textSatus){
  					  			//"not_usable" 또는 "useable" 둘중  하나를 
  					  			//data매개변수로 전달 받는다.
  					  //MemberController서버페이지에서 전송된 아이디 중복?인지 아닌지 판단하여
  					  //현재 join.jsp중앙화면에 보여 주는 구문 처리 
  					  if(data == 'usable'){ //아이디가  DB에 없으면?
  						  
  						  $("#idInput").text("사용할수 있는 email입니다.").css("color","blue");
  						  
  					  }else{//아이디가 DB에 있으면?(입력한 아이디 중복)
  						  
  						  $("#idInput").text("이미 사용중인 email입니다.").css("color","red");
  						  
  					  }	    					    	    					  
  				  }    					
  				}
  			  );
          }
  </script>
</head>
<body>
    <div class="container w-50 mx-auto mt-5 p-5 border border-1 rounded">
	<form action="${pageContext.request.contextPath}/member/new" method="post">
	  <h2>회원가입</h2>
		
	  <div class="d-flex justify-content-between">
		  <div class="w-75">
		      <label for="email">이메일</label>
			  <input type="email" id="email" name="email">
			  <span id="idInput"></span>
		  </div>
		  <div class="button-wrapper">
		  	  <button type="button" class="check_btn" onclick="checkEmail()">중복확인</button>
		  </div>
	  </div>
	
	  <label for="password">비밀번호</label>
	  <input type="password" id="password" name="password" required>
	
	  <label for="name">이름</label>
	  <input type="text" id="name" name="name">
	
	  <label for="role">역할</label>
	  <select id="role" name="role" required onchange="onRoleChange(this)">
	    <option value="">선택</option>
	    <option value="student">학생</option>
	    <option value="professor">교수</option>
	  </select>
	
	  <!-- 🎓 학생 추가 정보 -->
	  <div id="student-fields" class="hidden">
	    <label for="student_department">학과</label>
	    <input type="text" id="student_department" name="student_department">
	
	    <label for="grade">학년</label>
	    <select id="grade" name="grade">
	      <option value="1">1학년</option>
	      <option value="2">2학년</option>
	      <option value="3">3학년</option>
	      <option value="4">4학년</option>
	    </select>
	
	    <label for="status">재학 상태</label>
	    <select id="status" name="status">
	      <option value="재학">재학</option>
	      <option value="휴학">휴학</option>
	      <option value="졸업">졸업</option>
	    </select>
	  </div>
	
	  <!-- 👨‍🏫 교수 추가 정보 -->
	  <div id="professor-fields" class="hidden">
	    <label for="professor_department">학과</label>
	    <input type="text" id="professor_department" name="professor_department">
	  </div>
	
	  <button type="submit">회원가입</button>
	</form>
	</div>
</body>
</html>