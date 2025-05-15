<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../top.jsp" %>

<div style="width: 90%; margin: 30px auto;">
  <h2 style="text-align: center;">내 질문 목록</h2>

  <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead style="background-color: #f2f2f2;">
      <tr>
        <th>질문 번호</th>
        <th>과목명</th>
        <th>제목</th>
        <th>작성일</th>
        <th>상세보기</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="qna" items="${list}">
        <tr>
          <td>${qna.qna_id}</td>
          <td>${qna.subject_name}</td>
          <td>${qna.questioner_title}</td>
          <td><fmt:formatDate value="${qna.question_time}" pattern="yyyy-MM-dd" /></td>
          <td>
            <a href="${pageContext.request.contextPath}/qna/detail?qna_id=${qna.qna_id}">보기</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="../bottom.jsp" %>
