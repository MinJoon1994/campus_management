<%@page import="professorvo.SubjectVo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>수정되지 않은 강의 목록</title>
    <style>
        body {
            margin: 0;  /* 화면을 꽉 차게 하기위한 기본설정 */
        	padding: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 90%;
            margin: 0 auto;
            padding-top: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #2c3e50;
            color: white;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
        }

        .pagination strong {
            margin: 0 5px;
            font-weight: bold;
        }

    </style>
</head>
<body>

<div class="container">
    <h2 style="text-align: center; padding-bottom: 20px;">개설 요청한 강의 목록</h2>

    <!-- subjectList가 requestScope에 있는지 확인하고 출력 -->
    <c:if test="${not empty requestScope.subjectList}">
    
        <%
            Vector<SubjectVo> subjectList = (Vector<SubjectVo>) request.getAttribute("subjectList");

            int pageSize = 10; // 한 페이지에 출력할 강의 수
            int pageNum = 1;   // 기본 페이지
            if (request.getParameter("pageNum") != null) {
                pageNum = Integer.parseInt(request.getParameter("pageNum"));
            }

            int startRow = (pageNum - 1) * pageSize;
            // 1페이지: (1 - 1) * 10 = 0 → 인덱스 0부터 시작
			// 2페이지: (2 - 1) * 10 = 10 → 인덱스 10부터 시작
            int endRow = Math.min(startRow + pageSize, subjectList != null ? subjectList.size() : 0);
            List<SubjectVo> pageList = (subjectList != null) ? subjectList.subList(startRow, endRow) : new Vector<SubjectVo>();
            request.setAttribute("pageList", pageList);
        %>
        <table>
            <thead>
                <tr>
                    <th>과목 코드</th>
                    <th>과목 이름</th>
                    <th>과목 유형</th>
                    <th>개설 학년</th>
                    <th>분반</th>
                    <th>학점</th>
                    <th>교수명</th>
                    <th>현재 수강 인원</th>
                    <th>수강 가능 여부</th>
                </tr>
            </thead>
            <tbody>
                <!-- subjectList 반복 출력 -->
                <c:forEach var="subject" items="${requestScope.pageList}">
                    <tr>
                        <td>${subject.subjectCode}</td>  <!-- VO 기준으로 subjectCode 출력 -->
                        <td>${subject.subjectName}</td>
                        <td>${subject.subjectType}</td>
                        <td>${subject.openGrade}</td>
                        <td>${subject.division}</td>
                        <td>${subject.credit}</td>
                        <td>${subject.professorName}</td>
                        <td>${subject.currentEnrollment}</td>
                        
                        <!-- 신청 승인 여부 처리 -->
						<td>
						    <c:choose>
						        <c:when test="${subject.isAvailable == true}">
						            승인
						        </c:when>
						        <c:otherwise>
						            대기중
						        </c:otherwise>
						    </c:choose>
						</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
       	<div class="pagination" style="text-align:center; margin-top:20px;">
			<%
			    // list가 null이 아니고 비어있지 않은 경우에만 페이지네이션 처리
			    if (subjectList != null && !subjectList.isEmpty()) {
			        // 전체 페이지 수 계산 (list.size()는 전체 데이터 수)
			        int totalPage = (int)Math.ceil((double)subjectList.size() / pageSize); 
			        int pageBlock = 5; // 한 번에 보여줄 페이지 수
			        // 현재 페이지 번호를 기준으로 시작 페이지 계산
			        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
			        // 끝 페이지 계산 (현재 페이지가 마지막 페이지가 아닐 경우에만 해당)
			        int endPage = startPage + pageBlock - 1;
			        // 만약 endPage가 전체 페이지 수를 초과하면 endPage는 totalPage로 설정
			        if (endPage > totalPage) endPage = totalPage;
			
			        // 시작 페이지가 1보다 클 경우, 이전 페이지로 이동하는 버튼 생성
			        if (startPage > 1) {
			%>
			            <!-- 이전 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=startPage - 1%>">&#9664;</a>
			<%
			        }
			        // startPage부터 endPage까지의 페이지 번호를 출력
			        for (int i = startPage; i <= endPage; i++) {
			            // 현재 페이지가 선택된 페이지인 경우 강조 표시
			            if (i == pageNum) {
			%>
			                <!-- 현재 페이지 번호는 강조 처리 -->
			                <strong><%=i%></strong>
			<%
			            } else {
			%>
			                <!-- 현재 페이지가 아닌 경우 링크로 출력 -->
			                <a href="?pageNum=<%=i%>"><%=i%></a>
			<%
			            }
			        }
			        // endPage가 totalPage보다 작을 경우, 다음 페이지로 이동하는 버튼 생성
			        if (endPage < totalPage) {
			%>
			            <!-- 다음 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=endPage + 1%>">&#9654;</a>
			<%
			        }
			    }
			%>
		</div>
    </c:if>

    <!-- subjectList가 비어 있거나 null일 경우 -->
    <c:if test="${empty requestScope.subjectList}">
        <p>수정되지 않은 과목이 없습니다.</p>
    </c:if>
</div>

</body>
</html>
