<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사관리시스템</title>
<style>

.top_box{
	background-color:rgb(44, 62, 80);
	height:100px;
}

.top_box div img{
	height:75px;
	width:75px;
}

.top_box div a{
	color:white;
	text-decoration:none;
	cursor:pointer;
	font-size:25px;
}

.top_box div a p{
	color:white;
	text-decoration:none;
	cursor:pointer;
	font-size:40px;
	margin:0;
}
</style>
</head>
<div class="top_box d-flex justify-content-between">
	<div class="d-flex justify-content-center align-items-center logo_box">
		<img class="ms-3" src="../images/logo.png">
		<a class="me-3 ms-4" href="/campus/main"><p>OO 대학교</p></a>
	</div>
	<div class="d-flex justify-content-center align-items-center">
		<a class="me-5 ms-2" href="#">로그인</a>
		<a class="me-5 ms-2" href="#">회원가입</a>	
	</div>
</div>
</html>