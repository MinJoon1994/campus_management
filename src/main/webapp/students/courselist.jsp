<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.course-list-box {
  max-width: 880px;
  margin: 40px auto 0 auto;
  padding: 34px 38px;
  background: #f5f7fa;
  border-radius: 14px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.08);
  font-size: 1.09em;
  animation: fadein 0.5s;
}
@keyframes fadein {
  from { opacity: 0; transform: translateY(16px);}
  to { opacity: 1; transform: translateY(0);}
}
.course-list-box h2 {
  text-align: center; margin: 0 0 22px 0; color: #002147;
  font-weight: bold;
}
.coursetbl {
  width: 100%; border-collapse: collapse; background: #fff;
}
.coursetbl th, .coursetbl td {
  padding: 13px 10px;
  border-bottom: 1px solid #e5e7ea;
  text-align: center;
}
.coursetbl th {
  background: #002147; color: #fff; font-size: 1.08em;
}
.coursetbl tr:last-child td {
  border-bottom: none;
}
.coursetbl tr:hover td {
  background: #e6f0ff;
}
.course-cancel-btn {
  padding: 6px 18px;
  background: #ff5757;
  color: #fff !important;
  border: none;
  border-radius: 6px;
  font-size: 0.98em;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.18s;
}
.course-cancel-btn:hover {
  background: #cc1f1f;
}
.no-course-row td {
  color: #666; padding: 36px 0; text-align: center; background: #f8f8f9;
}
</style>

<div class="course-list-box">
  <h2>수강신청 내역</h2>
  <table class="coursetbl">
    <tr>
      <th>과목명</th>
      <th>교수명</th>
      <th>학점</th>
      <th>학기</th>
      <th>신청 취소</th>
    </tr>
    <c:choose>
      <c:when test="${empty list}">
        <tr class="no-course-row"><td colspan="5">수강신청 내역이 없습니다.</td></tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="item" items="${list}">
          <tr>
            <td>${item.subjectName}</td>
            <td>${item.professorName}</td>
            <td>${item.credit}</td>
            <td>${item.openGrade}</td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/student/enrolldelete" style="margin:0;">
                <input type="hidden" name="enrollmentId" value="${item.enrollmentId}" />
                <button type="submit" class="course-cancel-btn" onclick="return confirm('정말 취소하시겠습니까?');">취소</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </table>
</div>
