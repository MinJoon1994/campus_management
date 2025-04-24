<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	/* 전체 배경 */
body {
    background-color: #f2f4f8;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* 로그인 컨테이너 */
.container {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

/* 제목 */
.container h1 {
    text-align: center;
    margin-bottom: 30px;
    font-size: 2rem;
    color: #333;
}

/* 라벨 */
.container label {
    display: block;
    margin-top: 15px;
    font-weight: bold;
    color: #555;
}

/* 입력창 */
.container input[type="text"],
.container input[type="password"] {
    width: 100%;
    padding: 10px 12px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 8px;
    transition: border 0.3s ease;
}

.container input[type="text"]:focus,
.container input[type="password"]:focus {
    border-color: #007bff;
    outline: none;
}

/* 버튼 */
.container button {
    width: 100%;
    padding: 12px;
    margin-top: 25px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.container button:hover {
    background-color: #0056b3;
}

/* 회원가입 링크 */
.container a {
    display: block;
    width: 100%;
    padding: 12px;
    margin-top: 15px;
    text-align: center;
    border: 1px solid #ccc;
    border-radius: 8px;
    font-weight: bold;
    color: #007bff;
    background-color: white;
    text-decoration: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); /* 아주 얇은 그림자 */
    transition: all 0.3s ease;
}

.container a:hover {
    background-color: #f0f8ff;
    border-color: #007bff;
    color: #0056b3;
}
</style>
</head>
<body>
	<div class="container w-50 mx-auto mt-5 p-5">
		<h1>로그인</h1>
        <form action="${contextPath}/member/login" method="post">
            <label for="id">이메일</label>
            <input type="text" id="email" name="email">
            <br>
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password">
            <br>
            <button type="submit">로그인</button>
        </form>
        
        <a class="btn btn-" href="${contextPath}/member/register">회원가입</a>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
		    const form = document.querySelector("form");
		
		    form.addEventListener("submit", function(e) {
		        const email = document.getElementById("email").value.trim();
		        const password = document.getElementById("password").value.trim();
		
		        // 이메일 비어 있음
		        if (email === "") {
		            alert("이메일을 입력해주세요.");
		            document.getElementById("email").focus();
		            e.preventDefault(); // 전송 막기
		            return;
		        }
		
		        // 이메일 형식 확인 (간단 버전)
		        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		        if (!emailRegex.test(email)) {
		            alert("올바른 이메일 형식을 입력해주세요. 예: example@domain.com");
		            document.getElementById("email").focus();
		            e.preventDefault();
		            return;
		        }
		
		        // 비밀번호 비어 있음
		        if (password === "") {
		            alert("비밀번호를 입력해주세요.");
		            document.getElementById("password").focus();
		            e.preventDefault();
		            return;
		        }
		    });
		});
	</script>
</body>
</html>