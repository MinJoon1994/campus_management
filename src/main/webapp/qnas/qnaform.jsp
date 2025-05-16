<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<div style="width: 70%; margin: 30px auto;">
  <h2 style="text-align: center;">질문 작성</h2>

  <form action="${pageContext.request.contextPath}/student/qnaclass" method="post">
    <label>과목 선택</label><br>
    <select name="subject_code" required>
  <option value="">-- 과목 선택 --</option>
  <c:forEach var="subject" items="${list}">
    <option value="${subject.subject_code}">${subject.subject_name}</option>
  </c:forEach>
</select>

    <br><br>
    <label>제목</label><br>
    <input type="text" name="questioner_title" style="width: 100%;" required>

    <br><br>
    <label>질문 내용</label><br>
    <textarea name="question" rows="6" style="width: 100%;" required></textarea>

    <br><br>
    <div style="text-align: center;">
      <button type="submit">질문 등록</button>
    </div>
  </form>
</div>

<%@ include file="../bottom.jsp" %>