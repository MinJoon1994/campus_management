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
	/* 공통 */
	body {
		background-color: #f2f4f8;
		font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		color: #333;
	}

	#board-container {
		max-width: 1000px;
		margin: 40px auto;
		background: #ffffff;
		padding: 40px 30px;
		border-radius: 10px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	}

	#board-title {
		font-size: 30px;
		font-weight: bold;
		color: #003366;
		text-align: center;
		margin-bottom: 30px;
		border-bottom: 2px solid #003366;
		padding-bottom: 10px;
	}

	/* 테이블 */
	#board-table {
		width: 100%;
		border-collapse: collapse;
	}

	#board-table th, #board-table td {
		border: 1px solid #d9d9d9;
		padding: 14px;
		text-align: center;
		font-size: 15px;
	}

	#board-table th {
		background-color: #003366;
		color: #ffffff;
		font-weight: 600;
	}

	#board-table tr:nth-child(even) {
		background-color: #f8f9fa;
	}

	#board-table tr:hover {
		background-color: #e2ecf5;
	}

	.article-title a {
		text-decoration: none;
		color: #003366;
		font-weight: 500;
	}

	.article-title a:hover {
		text-decoration: underline;
		color: #00509e;
	}

	.reply-indent {
		display: inline-block;
		width: 1.5rem;
	}

	/* 페이지네이션 */
	.pagination-wrapper {
		display: flex;
		justify-content: center;
		margin-top: 30px;
	}

	.pagination .page-link {
		color: #003366;
		border: 1px solid #003366;
	}

	.pagination .page-item.active .page-link {
		background-color: #003366;
		border-color: #003366;
		color: #fff;
	}

	.pagination .page-link:hover {
		background-color: #00509e;
		color: white;
	}

	/* 버튼 */
	.btn-primary {
		background-color: #003366;
		border: none;
		padding: 10px 30px;
		font-size: 16px;
	}

	.btn-primary:hover {
		background-color: #00509e;
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