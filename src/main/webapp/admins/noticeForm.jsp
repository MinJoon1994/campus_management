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
.member-section {
	max-width: 700px;
	margin: 50px auto;
	padding: 30px;
	background-color: #f9f9f9;
	border: 1px solid #ddd;
	border-radius: 12px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.member-section h2 {
	text-align: center;
	color: #2c3e50;
	margin-bottom: 30px;
}

.member-section table {
	width: 100%;
	border-collapse: collapse;
}

.member-section td {
	padding: 10px;
	vertical-align: top;
}

.member-section input[type="text"],
.member-section textarea {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 6px;
	font-size: 16px;
}

.member-section textarea {
	resize: vertical;
}

.form-buttons {
	text-align: center;
	padding-top: 20px;
}

.form-buttons input[type="submit"],
.form-buttons input[type="reset"] {
	padding: 10px 30px;
	font-size: 16px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	margin: 0 10px;
	background-color: #2c3e50;
	color: white;
	transition: background-color 0.3s;
}

.form-buttons input[type="submit"]:hover,
.form-buttons input[type="reset"]:hover {
	background-color: #3e556f;
}

</style>
</head>
<body>
	
	<form action="${contextPath}/admin/notice" method="post">
		<div class="member-section">
            <h2>공지사항 등록</h2>
            <table>
                <tr>
                    <td>제목</td>
                    <td><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea name="content" rows="10" cols="50" required></textarea></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="등록">
                        <input type="reset" value="취소">
                    </td>
                </tr>
            </table>
        </div>
	</form>
	
</body>
</html>