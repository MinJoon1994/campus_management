<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title>학사관리시스템</title>
	<!-- 부트스트랩 5.3.3 버전의 스타일시트 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<style>

	</style>
	
	<!-- jQuery 라이브러리 -->
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <div class="container mt-5 mb-5">
    <div class="card shadow" style="border: none;">
        <div class="card-header text-white" style="background-color: rgb(44, 62, 80);">
            <h3 class="mb-0">학사일정 상세보기</h3>
        </div>
        <div class="card-body" style="background-color: #f8f9fa;">
        	<div class="d-flex gap-3 mb-1">
	             <div class="mb-3">
	                <label class="form-label fw-bold">시작일</label>
	                <div class="form-control bg-white">${calendarVO.start}</div>
	            </div>
	            <c:choose>
	            	<c:when test="${calendarVO.end != null}">
	            	   <div class="mb-3">
			                <label class="form-label fw-bold">종료일</label>
			                <div class="form-control bg-white">${calendarVO.end}</div>
	            		</div>
	            	</c:when>
	            </c:choose>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold">캘린더 ID</label>
                <div class="form-control bg-white">${calendarVO.calendarId}</div>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold">제목</label>
                <div class="form-control bg-white">${calendarVO.title}</div>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold">내용</label>
                <textarea class="form-control bg-white" rows="1">${calendarVO.description}</textarea>
            </div>
            <div class="text-end">
                <a href="${contextPath}/campus/main" class="btn text-white" style="background-color: rgb(44, 62, 80);">목록으로</a>
            </div>
        </div>
    </div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const textarea = document.querySelector("textarea");
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
});
</script>
</body>
</html>
