<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .student-profile-box {
    max-width: 430px;
    margin: 40px auto 0 auto;
    padding: 28px 36px;
    background: #f5f7fa;
    border-radius: 14px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.09);
    font-size: 1.14em;
    animation: fadein 0.6s;
  }
  @keyframes fadein {
    from { opacity: 0; transform: translateY(22px);}
    to { opacity: 1; transform: translateY(0);}
  }
  .student-profile-box h2 {
    text-align: center; margin: 0 0 15px 0; color: #002147;
  }
  .student-info-ul {
    margin: 16px 0 30px 0; padding: 0; list-style: none;
  }
  .student-info-ul li {
    margin-bottom: 8px;
    font-size: 1em;
  }
  .student-btn-group {
    width: 100%; text-align: center; margin-top: 14px;
  }
  .student-action-btn {
    display: inline-block;
    margin: 0 10px;
    padding: 10px 28px;
    background: #002147;
    color: #fff !important;
    border-radius: 7px;
    border: none;
    font-size: 1.04em;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
    transition: background 0.17s;
  }
  .student-action-btn:hover {
    background: #004080;
  }
</style>



<div class="student-profile-box">
  <h2>학생 정보</h2>
  <ul class="student-info-ul">
    <li><b>이름</b>: ${studentVO.name}</li>
    <li><b>학번</b>: ${studentVO.student_id}</li>
    <li><b>학년</b>: ${studentVO.grade}</li>
    <li><b>전공</b>: ${studentVO.department}</li>
  </ul>
  <div class="student-btn-group">
    <a class="student-action-btn"
       href="${pageContext.request.contextPath}/student/grades">
      전체 성적 조회
    </a>
    <a class="student-action-btn"
       href="${pageContext.request.contextPath}/student/gradesdetail">
      학기별 성적 조회
    </a>
  </div>
</div>
