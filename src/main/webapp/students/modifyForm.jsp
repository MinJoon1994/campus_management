<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 개인정보 수정</title>
<style>
.modify-box {
			max-width: 600px;
			margin: 40px auto;
			padding: 30px;
			background-color: #f9f9f9;
			border-radius: 12px;
			box-shadow: 0 2px 8px rgba(0,0,0,0.1);
		}
		.modify-box h2 {
			text-align: center;
			margin-bottom: 20px;
		}
		.modify-box label {
			display: block;
			margin-top: 15px;
			font-weight: bold;
		}
		.modify-box input, .modify-box select {
			width: 100%;
			padding: 10px;
			margin-top: 5px;
			border: 1px solid #ccc;
			border-radius: 6px;
		}
		.modify-box button {
			margin-top: 25px;
			width: 100%;
			padding: 12px;
			background-color: #002147;
			color: white;
			border: none;
			border-radius: 8px;
			font-size: 16px;
			cursor: pointer;
		}
		.modify-box button:hover {
			background-color: #003566;
		}
</style>
</head>
<body>
	<div class="modify-box">
	<h2>개인정보 수정</h2>

	<form method="post" action="${pageContext.request.contextPath}/student/modify">
		<!-- 이름 -->
		<label for="name">이름</label>
		<input type="text" id="name" name="name" value="${vo.name}" required readonly/>

		<!-- 이메일 -->
		<label for="email">이메일</label>
		<input type="email" id="email" name="email" value="${vo.email}" required />

		<!-- 비밀번호 -->
		<label for="password">비밀번호</label>
		<input type="password" id="password" name="password" value="${vo.password}" required />

		<!-- 학번 -->
		<label for="student_number">학번</label>
		<input type="text" id="student_number" name="student_number" value="${vo.studentVO.student_id}" readonly />

		<!-- 학과 -->
		<label for="department">학과</label>
		<input type="text" id="department" name="department" value="${vo.studentVO.department}" required readonly/>

		<!-- 학년 -->
		<label for="grade">학년</label>
		<input type="text" id="grade" name="grade" value="${vo.studentVO.grade}" readonly />

		<!-- 학적 상태 -->
		<label for="status">학적 상태</label>
		<select name="status" id="status">
			<option value="재학" ${vo.studentVO.status == '재학' ? 'selected' : ''}>재학</option>
			<option value="휴학" ${vo.studentVO.status == '휴학' ? 'selected' : ''}>휴학</option>
		</select>

		<!-- 제출 -->
		<button type="submit">수정 완료</button>
	</form>
</div>
</body>
</html>