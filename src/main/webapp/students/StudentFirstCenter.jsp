<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강의 등록 신청 완료</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #FDEED4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* 중앙 정렬을 위한 전체 높이 */
        }

        .register-success-img {
            max-width: 100%;
            height: auto;
            fit-content: cover;
        }
    </style>
    <script>
        window.onload = function () {
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
                alert("<%= message %>");
            <% } %>
        }
    </script>
</head>
<body>
    <img class="register-success-img" alt="이미지 오류"
         src="${pageContext.request.contextPath}/images/StudentFirstVIew.png">
</body>
</html>
