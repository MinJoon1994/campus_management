<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String contextPath = request.getContextPath();
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>게시글 목록</title>
		<%-- Bootstrap 5 CSS --%>
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
			rel="stylesheet">
		<style>
		/* 필요한 경우 추가적인 사용자 정의 스타일 */
		.article-title a {
			text-decoration: none;
			color: inherit; /* 링크 색상 기본값 사용 */
		}
		
		.article-title a:hover {
			text-decoration: underline;
		}
		
		.reply-indent {
			display: inline-block; /* 인라인 블록으로 너비 적용 */
			width: 1.5rem; /* 들여쓰기 너비 */
		}
		/* 페이지네이션 가운데 정렬 */
		.pagination-wrapper {
			display: flex;
			justify-content: center;
			margin-top: 2rem;
		}
		
		#board-container {
			max-width: 1000px;
			margin: 40px auto;
			background: white;
			padding: 30px;
			border-radius: 12px;
			box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
		}
		
		#board-title {
			font-size: 32px;
			color: #4a90e2;
			text-align: center;
			margin-bottom: 30px;
		}
		
		#board-table {
			width: 100%;
			border-collapse: collapse;
		}
		
		#board-table th, #board-table td {
			border: 1px solid #ccc;
			padding: 12px;
			text-align: center;
		}
		
		#board-table th {
			background-color: #4a90e2;
			color: white;
		}
		
		#board-table tr:nth-child(even) {
			background-color: #f9f9f9;
		}
		
		#board-table tr:hover {
			background-color: #f1f1f1;
		}
		</style>
	</head>
	<body>
	
		<div class="container mt-4">
			<h2 id="board-title" class="text-center mb-4">공 지 사 항</h2>
	
			<%-- 글 목록 테이블 (반응형 적용) --%>
			<div class="table-responsive">
				<table id="board-table"
					class="table table-hover align-middle text-center">
					<thead class="table-light">
						<tr>
							<th scope="col" style="width: 10%;"><strong>번호</strong></th>
							<th scope="col"><strong>제목</strong></th>
							<th scope="col" style="width: 15%;"><strong>작성자</strong></th>
							<th scope="col" style="width: 20%;">작성일</th>
						</tr>
					</thead>
					<tbody>
						<%-- 글 목록 표시 --%>
						<c:choose>
							<c:when test="${empty noticelist}">
								<tr>
									<td colspan="4" class="text-center py-5">등록된 글이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="notice" items="${noticelist}">
									<tr>
										<td>${notice.noticeID}</td>
										<td class="text-start article-title">
											<%-- 제목은 왼쪽 정렬 --%> <%-- 들여쓰기 처리 --%> <c:if
												test="${article.level > 1}">
												<c:forEach begin="1" end="${article.level - 1}">
													<span class="reply-indent"></span>
													<%-- CSS로 들여쓰기 --%>
												</c:forEach>
												<span class="badge bg-secondary me-1">Re:</span>
											</c:if> <%-- 글 제목 링크 --%> <a
											href="${contextPath}/notice/detail?noticeID=${notice.noticeID}">
												<c:out value="${notice.title}" /> <%-- XSS 방지를 위해 c:out 사용 권장 --%>
										</a>
										</td>
										<td>${notice.adminName}</td>
										<td><fmt:formatDate value="${notice.createdAt}"
												pattern="yyyy-MM-dd" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
	
			<%-- 페이지네이션 --%>
			<div class="pagination-wrapper">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<%-- 이전 섹션 이동 버튼 --%>
						<c:if test="${section > 1}">
							<li class="page-item"><a class="page-link"
								href="${contextPath}/notice/list?section=${section-1}&pageNum=1"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:if>
	
						<%-- 페이지 번호 표시 --%>
						<c:forEach var="i" begin="1" end="10">
							<c:set var="page" value="${(section-1)*10 + i}" />
							<c:if test="${page <= totalPage}">
								<li class="page-item ${pageNum == i ? 'active' : ''}"><a
									class="page-link"
									href="${contextPath}/notice/list?section=${section}&pageNum=${i}">${page}</a>
								</li>
							</c:if>
						</c:forEach>
	
						<%-- 다음 섹션 이동 버튼 --%>
						<c:if test="${section < totalSection}">
							<li class="page-item"><a class="page-link"
								href="${contextPath}/notice/list?section=${section+1}&pageNum=1"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:if>
					</ul>
				</nav>
			</div>
	
			<%-- 글쓰기 버튼 --%>
			<div class="text-center mt-4 mb-5">
				<a href="${contextPath}/notice/noticeForm.do"
					class="btn btn-primary">글쓰기</a>
			</div>
	
		</div>
		<%-- /.container --%>
	
		<%-- Bootstrap 5 JS Bundle --%>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>