<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL core 라이브러리 사용 선언 (변수 설정, 조건문, 반복문 등) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- JSTL formatting 라이브러리 사용 선언 (날짜, 숫자 포맷 등) - 현재 코드에서는 사용되지 않지만 필요할 수 있어 유지 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
    1. 센터 페이지 경로 설정:
    - Controller에서 request 객체에 "center"라는 이름으로 담아 보낸 값을 가져와 'center' 변수에 저장합니다.
    - 이 값은 메인 컨텐츠 영역에 포함될 JSP 파일의 경로입니다. (예: "board/fileboardlist.jsp")
--%>
<c:set var="center" value="${requestScope.center}"/>

<c:if test="${empty center}"> <%-- 'center == null' 대신 'empty center'를 사용하는 것이 더 안전합니다 (null 또는 빈 문자열 체크) --%>
    <c:set var="center" value="Center.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="ko"> <%-- 언어 속성을 한국어(ko)로 지정 --%>
<head>
    <meta charset="UTF-8">
    <%-- 3. 뷰포트 설정: 모바일 기기 등 다양한 화면 크기에서 웹페이지가 올바르게 보이도록 설정합니다. --%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Campus Main</title> <%-- 페이지 제목 설정 (프로젝트에 맞게 변경) --%>
	
	<!-- ✅ Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
      /* Tailwind CSS 클래스로 처리하기 어려운 아주 세부적인 스타일은 여기에 추가할 수 있습니다. */
      body {
        font-family: 'Inter', sans-serif; /* 기본 폰트 설정 (Inter 폰트 사용 권장) */
      }
    </style>
    <%-- 웹 폰트 로드 (예: Google Fonts - Inter) --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>

<body class="flex flex-col min-h-screen bg-gray-100">

    <header class="bg-white shadow-md">
        <jsp:include page="Top.jsp"/>
    </header>

    <main class="flex-grow w-full p-4 md:p-6">
        <div class="bg-white rounded-lg shadow-lg p-4 md:p-6 min-h-[500px]">
            <jsp:include page="${center}"/>
        </div>
    </main>

    <footer class="bg-gray-800 p-4 text-center mt-auto">
        <jsp:include page="Bottom.jsp"/>
    </footer>

</body>
</html>
