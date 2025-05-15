<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../top.jsp" %>

<div style="width: 70%; margin: 30px auto;">
  <h2 style="text-align: center;">질문 상세 보기</h2>

  <table style="width: 100%; border-collapse: collapse; margin-top: 30px;">
    <tr>
      <th>과목</th>
      <td>${qna.subject_name}</td>
    </tr>
    <tr>
      <th>제목</th>
      <td>${qna.questioner_title}</td>
    </tr>
    <tr>
      <th>작성일</th>
      <td><fmt:formatDate value="${qna.question_time}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
    <tr>
      <th>내용</th>
      <td style="padding-top: 10px;">${qna.question}</td>
    </tr>
  </table>

  <div style="margin-top: 20px; text-align: center;">
    <a href="${pageContext.request.contextPath}/qna/list">← 목록으로</a>
  </div>
</div>

<%@ include file="../bottom.jsp" %>
