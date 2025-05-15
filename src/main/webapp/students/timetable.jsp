<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<div style="width: 90%; margin: 30px auto;">
  <h2 style="text-align:center;">내 시간표</h2>

  <table border="1" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead style="background-color: #f2f2f2;">
      <tr>
        <th>과목코드</th>
        <th>과목명</th>
        <th>담당교수</th>
        <th>학점</th>
        <th>요일/시간</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="s" items="${list}">
        <tr>
          <td>${s.subjectCode}</td>
          <td>${s.subjectName}</td>
          <td>${s.professorName}</td>
          <td>${s.credit}</td>
          <td>${s.schedule}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="../bottom.jsp" %>
