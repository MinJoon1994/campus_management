<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<style>

.bottom_box{
	background-color:rgba(44, 62, 80,0.7);
	height:200px;
	text-align: center;
}

.bottom_box div img{
	height:100px;
	width:100px;
	margin-right:10px;
}

.bottom_box div h1{
	color:white;
}

.footer-links {
  display:flex;
  justify-content:center;
  align-items:center;
  margin-bottom: 10px;
}

.footer-links a {
  color: rgb(255, 255, 255);
  text-decoration: none;
  margin: 0 8px;
  font-size:24px;
}

.footer-links span{
  color: rgb(255, 255, 255);
  text-decoration: none;
  margin: 0 8px;
}

.footer-links a:hover {
  text-decoration: underline;
}

.footer-bottom{
	margin-left:30px;
}

.footer-bottom p {
  display:flex;
  justify-content:center;
  align-items:center;
  color:white;
  margin:0;
  font-size:24px;
}

</style>
</head>
<body>
	<div class="bottom_box d-flex justify-content-center">
		<div class="d-flex justify-content-center align-items-center me-5">
			<img class="ms-3" src="../images/logo.png">
			<h1>OO 대학교</h1>
		</div>
	
	    <div class="footer-links">
			<a href="#">강남 캠퍼스</a>
			<span>|</span>
			<a href="#">인천 캠퍼스</a>
			<span>|</span>
			<a href="#">마포 캠퍼스</a>
			<div class="footer-bottom">
	        	<p>© 2025 OO UNIVERSITY ALL RIGHTS RESERVED.</p>
			</div>
	    </div>
    </div>
</body>
</html>