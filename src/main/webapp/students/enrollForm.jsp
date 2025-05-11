<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<div style="width:90%; margin: 30px auto;">
  <h2 style="text-align:center;">수강 신청</h2>

  <table border="1" style="width:100%; border-collapse: collapse; margin-top:20px;">
    <thead style="background-color: #f2f2f2;">
      <tr>
        <th>강의번호</th>
        <th>과목명</th>
        <th>담당교수</th>
        <th>시간</th>
        <th>학점</th>
        <th>정원</th>
        <th>현재인원</th>
        <th>신청</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="lecture" items="${list}">
        <tr>
          <td>${lecture.lecture_id}</td>
          <td>${lecture.subject_name}</td>
          <td>${lecture.professor_name}</td>
          <td>${lecture.schedule}</td>
          <td>${lecture.credit}</td>
          <td>${lecture.max_students}</td>
          <td>${lecture.current_students}</td>
          <td>
            <form action="${pageContext.request.contextPath}/student/enroll" method="post">
              <input type="hidden" name="lecture_id" value="${lecture.lecture_id}">
              <button type="submit" onclick="return confirm('이 강의를 신청하시겠습니까?')">신청</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="../bottom.jsp" %>
