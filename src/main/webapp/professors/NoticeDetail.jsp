<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="professorvo.NoticeProfessorVo" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 상세</title>
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
            font-size: 24px;  /* 글자 크기 키움 */
        }
        .notice-details {
            margin: 20px 0;
        }
        .notice-details label {
            font-weight: bold;
            font-size: 16px;  /* 글자 크기 키움 */
            margin-bottom: 10px;
            display: block;
            color: #333;
        }
        .notice-details p {
            margin: 15px 0;
            font-size: 15px;  /* 글자 크기 키움 */
            color: #555;
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
    <h2>📄 공지사항 상세</h2>

    <!-- NoticeProfessorVo 객체를 JSTL로 사용 -->
    <c:if test="${not empty noticeVo}">
        <div class="notice-details">
            <label>제목:</label>
            <p>${noticeVo.title}</p>

            <label>내용:</label>
            <p>${noticeVo.content}</p>

            <label>작성일:</label>
            <p>${noticeVo.createdAt}</p>

            <label>첨부파일:</label>
            <p><a href="#" onclick="downloadFile('${noticeVo.fileName}')">${noticeVo.fileName}</a></p>
        </div>

        <!-- 수정 버튼을 클릭하면 폼으로 데이터 전송 -->
        <form action="${contextPath}/professors/NoticeEdit.jsp" method="get">
            <!-- hidden으로 noticeVo의 값을 넘겨줌 -->
            <input type="hidden" name="noticeId" value="${noticeVo.noticeId}" />
            <input type="hidden" name="title" value="${noticeVo.title}" />
            <input type="hidden" name="content" value="${noticeVo.content}" />
            <input type="hidden" name="createdAt" value="${noticeVo.createdAt}" />
            <input type="hidden" name="fileName" value="${noticeVo.fileName}" />

            <div class="button-area">
                <button type="submit">수정</button>
            </div>
        </form>
    </c:if>

</div>

</body>
</html>
