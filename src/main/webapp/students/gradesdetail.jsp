<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .grade-table-wrap {
    width: 80%;
    margin: 40px auto 0 auto;
  }
  .grade-table {
    width: 100%;
    border-collapse: collapse;
    background: #fff;
    box-shadow: 0 2px 8px rgba(0,0,0,0.04);
    font-size: 1.05em;
  }
  .grade-table th, .grade-table td {
    border: 1px solid #d1d5db;
    padding: 12px 8px;
    text-align: center;
  }
  .grade-table th {
    background: #002147;
    color: #fff;
    font-weight: bold;
  }
  .grade-table tr:nth-child(even) {
    background: #f5f7fa;
  }
  .grade-table tr:hover {
    background: #dde6f4;
  }
  .grade-back-link {
    display: inline-block;
    margin: 20px 0 0 0;
    padding: 7px 20px;
    background: #002147;
    color: #fff !important;
    border-radius: 4px;
    text-decoration: none;
    font-size: 0.98em;
    transition: background 0.18s;
  }
  .grade-back-link:hover {
    background: #004080;
  }
  h2 {
    text-align: center;
    margin-top: 36px;
    color: #002147;
    letter-spacing: 0.03em;
  }
</style>

<h2>과목별 성적 상세 조회</h2>
<div class="grade-table-wrap">
  <table class="grade-table">
    <tr>
      <th>과목명</th>
      <th>교수명</th>
      <th>학점</th>
      <th>점수</th>
      <th>등급</th>
    </tr>
    <c:forEach var="item" items="${list}">
      <tr>
        <td>${item.subjectName}</td>
        <td>${item.professorName}</td>
        <td>${item.credits}</td>
        <td>
          <c:choose>
            <c:when test="${item.score != null}">${item.score}</c:when>
            <c:otherwise>-</c:otherwise>
          </c:choose>
        </td>
        <td>
          <c:choose>
            <c:when test="${not empty item.grade}">${item.grade}</c:when>
            <c:otherwise>-</c:otherwise>
          </c:choose>
        </td>
      </tr>
    </c:forEach>
  </table>
  <div style="text-align:center;">
    <a class="grade-back-link"
       href="${pageContext.request.contextPath}/student/grades">[학기별 성적으로 돌아가기]</a>
  </div>
</div>
