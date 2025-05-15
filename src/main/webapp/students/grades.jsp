<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<%-- 날짜 표시용 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div style="width: 90%; margin: 30px auto;">
    <h2 style="text-align:center;">나의 성적 목록</h2>

    <table border="1" style="width:100%; border-collapse:collapse; margin-top:20px;">
        <thead style="background-color:#f2f2f2;">
            <tr>
                <th>과목코드</th>
                <th>과목명</th>
                <th>담당 교수</th>
                <th>학점</th>
                <th>점수</th>
                <th>등급</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="grade" items="${list}">
                <tr>
                    <td>${grade.subject_code}</td>
                    <td>${grade.subject_name}</td>
                    <td>${grade.professor_name}</td>
                    <td>${grade.credit}</td>
                    <td>${grade.score}</td>
                    <td>${grade.grade}</td>
                    <td><fmt:formatDate value="${grade.reg_date}" pattern="yyyy-MM-dd"/></td>
                    <td>
  						<a href="${pageContext.request.contextPath}/student/gradesDetail?subject_code=${grade.subject_code}">
    					${grade.subject_name}</a>
					</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../bottom.jsp" %>
