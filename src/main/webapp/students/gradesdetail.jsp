<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../top.jsp" %>

<div style="width: 90%; margin: 30px auto;">
  <c:choose>
    <c:when test="${not empty list}">
      <h2 style="text-align: center;">
        ${list[0].subject_name} (${param.subject_code}) 성적 상세
      </h2>
    </c:when>
    <c:otherwise>
      <h2 style="text-align: center;">성적 상세 조회 (${param.subject_code})</h2>
    </c:otherwise>
  </c:choose>

  <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead style="background-color: #f2f2f2;">
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
      <c:forEach var="g" items="${list}">
        <tr>
          <td>${g.subject_code}</td>
          <td>${g.subject_name}</td>
          <td>${g.professor_name}</td>
          <td>${g.credit}</td>
          <td>${g.score}</td>
          <td>${g.grade}</td>
          <td><fmt:formatDate value="${g.reg_date}" pattern="yyyy-MM-dd" /></td>
        </tr>
      </c:forEach>

      <c:if test="${empty list}">
        <tr>
          <td colspan="7" style="text-align: center;">해당 과목의 성적 정보가 없습니다.</td>
        </tr>
      </c:if>
    </tbody>
  </table>

  <div style="margin-top: 20px; text-align: center;">
    <a href="${pageContext.request.contextPath}/student/grades">← 돌아가기</a>
  </div>
</div>

<%@ include file="../bottom.jsp" %>