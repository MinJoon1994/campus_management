<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
    String noticeId = request.getParameter("noticeId");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String createdAt = request.getParameter("createdAt");
    String fileName = request.getParameter("fileName");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f6f9;
        }
        .container {
            width: 60%;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }
        .form-group {
            margin: 15px 0;
        }
        .form-group label {
            font-weight: bold;
            font-size: 16px;
            margin-bottom: 5px;
            display: block;
            color: #333;
        }
        .form-group input, .form-group textarea, .form-group select {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 150px;
        }
        .button-area {
            text-align: right;
            margin-top: 20px;
        }
        .button-area button {
            padding: 12px 25px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            margin: 0 10px;
        }
        .button-area button:hover {
            background-color: #2980b9;
        }
        .button-area button[type="button"] {
            background-color: #ccc;
        }
        .button-area button[type="button"]:hover {
            background-color: #999;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>📄 공지사항 수정</h2>
    <form action="${contextPath}/professor/noticeinsertorupdate.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="noticeId" value="<%= noticeId %>" />

        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="<%= title %>" required />
        </div>

        <div class="form-group">
            <label for="content">내용:</label>
            <textarea id="content" name="content" required><%= content %></textarea>
        </div>

        <div class="form-group">
            <label for="fileName">첨부파일:</label>
            <input type="file" id="fileName" name="uploadFile" />
            <c:if test="${not empty fileName}">
                <p>현재 파일: ${fileName}</p>
            </c:if>
        </div>

        <div class="button-area">
            <button type="button" onclick="window.history.back()">뒤로가기</button>
            <button type="submit">수정</button>
        </div>
    </form>
</div>

</body>
</html>
