<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.enroll-list-box {
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
.enroll-list-box h2 {
  text-align: center; margin: 0 0 22px 0; color: #002147;
  font-weight: bold;
}
.enrolltbl {
  width: 100%; border-collapse: collapse; background: #fff;
}
.enrolltbl th, .enrolltbl td {
  padding: 13px 10px;
  border-bottom: 1px solid #e5e7ea;
  text-align: center;
}
.enrolltbl th {
  background: #002147; color: #fff; font-size: 1.08em;
}
.enrolltbl tr:last-child td {
  border-bottom: none;
}
.enrolltbl tr:hover td {
  background: #e6f0ff;
}
.enroll-btn {
  padding: 7px 22px;
  background: #225cff;
  color: #fff !important;
  border: none;
  border-radius: 6px;
  font-size: 1em;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.18s;
}
.enroll-btn:disabled {
  background: #c9d2e3;
  color: #888 !important;
  cursor: not-allowed;
}
</style>

<div class="enroll-list-box">
  <h2>수강신청 가능한 과목 목록</h2>
  <table class="enrolltbl">
    <tr>
      <th>과목명</th>
      <th>교수명</th>
      <th>학점</th>
      <th>학기</th>
      <th>신청</th>
    </tr>
    <c:choose>
      <c:when test="${empty list}">
        <tr><td colspan="5" style="color:#666;padding:38px 0;">수강신청 가능한 과목이 없습니다.</td></tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="item" items="${list}">
          <tr>
            <td>${item.subjectName}</td>
            <td>${item.professorName}</td>
            <td>${item.credit}</td>
            <td>${item.openGrade}</td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/student/enroll" style="margin:0;">
                <input type="hidden" name="subjectCode" value="${item.subjectCode}" />
                <button type="submit" class="enroll-btn">신청</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </table>
</div>
