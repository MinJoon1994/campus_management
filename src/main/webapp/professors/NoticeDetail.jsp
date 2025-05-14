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
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: 30px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        .notice-details {
            margin: 20px 0;
        }
        .notice-details label {
            font-weight: bold;
        }
        .notice-details p {
            margin: 10px 0;
        }
        .button-area {
            text-align: right;
            margin-top: 20px;
        }
        .button-area button {
            padding: 8px 16px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .button-area button:hover {
            background-color: #2980b9;
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
            <p><a href="${contextPath}/uploads/${noticeVo.fileName}" download>파일 다운로드</a></p>
        </div>

        <div class="button-area">
            <button onclick="location.href='${contextPath}/professor/editNotice.do?noticeId=${noticeVo.noticeId}'">수정</button>
            <button onclick="deleteNotice(${noticeVo.noticeId})">삭제</button>
        </div>
    </c:if>

</div>

<script>
    // 공지사항 삭제 기능
    function deleteNotice(noticeId) {
        if (confirm('정말로 이 공지사항을 삭제하시겠습니까?')) {
            fetch('${contextPath}/professor/deleteNotice.do', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'noticeId=' + noticeId
            })
            .then(response => {
                if (response.ok) {
                    alert('공지사항이 삭제되었습니다.');
                    window.close(); // 창 닫기
                } else {
                    alert('삭제 실패');
                }
            });
        }
    }
</script>

</body>
</html>
