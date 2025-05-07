<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.LecturePlanVo"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
    
    String jsonStr = request.getParameter("subjectList");
	System.out.println("전달받은 강의목록 객체 : " + jsonStr);
	// {"subjectCode":"RH1001","subjectName":"자료구조","subjectType":"전공","openGrade":2,"division":"A","credit":3,"professor":"홍길동","schedule":"월 1,2 / 공학관 101호","enrollment":"25/30"}
    
	LecturePlanVo lecturePlanVo = (LecturePlanVo) request.getAttribute("lecturePlanVo");
	
	Gson gson = new Gson();
	String jsonlecturePlanVo = gson.toJson(lecturePlanVo); // 자바 객체 -> JSON 문자열
	System.out.println("자바 객체 -> JSON 문자열 : " + jsonlecturePlanVo);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>학원 강의계획서</title>
  <style>
    body {
      font-family: '맑은 고딕', sans-serif;
      padding: 20px;
    }

    .plan-container {
      width: 600px;
      border: 1px solid #000;
      padding: 20px;
    }

    h2 {
      text-align: center;
      margin-bottom: 20px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }

    td {
      border: 1px solid #000;
      padding: 8px;
      vertical-align: top;
    }

    .label {
      width: 25%;
      background-color: #f5f5f5;
      font-weight: bold;
    }

    .section {
      height: 120px;
    }

    .bottom-buttons {
      text-align: center;
      margin-top: 20px;
    }

    .bottom-buttons button {
      padding: 10px 20px;
      font-size: 16px;
      margin: 0 10px;
      cursor: pointer;
    }

    input[type="text"], textarea {
      width: 100%;
      box-sizing: border-box;
      font-family: '맑은 고딕', sans-serif;
      font-size: 14px;
    }

    textarea {
      height: 100px;
      resize: none;
    }
  </style>
</head>
<body>
<script>
  document.addEventListener("DOMContentLoaded", function () {
	  console.log("페이지 로딩됨");
    // JSON 문자열을 JavaScript 객체로 변환
	const subject = JSON.parse('<%= jsonStr %>');
    console.log(subject);
    const lP = JSON.parse('<%= jsonlecturePlanVo %>');
    console.log(lP);
    document.getElementById("subjectCode").value = subject.subjectCode;
    document.getElementById("subjectName").value = subject.subjectName;
    document.getElementById("professor").value = subject.professor;
    document.getElementById("professorId").value = subject.professorId;
    document.getElementById("open_grade").value = subject.openGrade + "학년";
    
    document.getElementById("lecture_period").value = lP.lecturePeriod || "";
    document.getElementById("main_content").value = lP.mainContent || "";
    document.getElementById("goal").value = lP.goal;
    document.getElementById("method").value = lP.method;
    document.getElementById("content").value = lP.content;
    document.getElementById("evaluation").value = lP.evaluation || "";
  });
</script>

<div class="plan-container">
  <h2>강의계획서</h2>
  <form action="<%=contextPath%>/Professor/LecturePlanAdd.do" method="post">
    <input type="hidden" name="professorId" id="professorId">

    <table>
      <tr>
        <td class="label">교과명</td>
        <td><input type="text" id="subjectName" name="subjectName" readonly /></td>
      </tr>
      <tr>
        <td class="label">과목코드</td>
        <td><input type="text" id="subjectCode" name="subjectCode" readonly /></td>
      </tr>
      <tr>
        <td class="label">강사명</td>
        <td><input type="text" id="professor" name="professor" readonly /></td>
      </tr>
      <tr>
        <td class="label">강의기간</td>
        <td><input type="text" name="lecturePeriod" id="lecture_period"/></td>
      </tr>
      <tr>
        <td class="label">수강대상</td>
        <td><input type="text" id="open_grade" name="open_grade" readonly /></td>
      </tr>
      <tr>
        <td class="label">주요내용</td>
        <td><input type="text" name="mainContent" id="main_content"/></td>
      </tr>
      <tr>
        <td class="label">강의목표</td>
        <td class="section"><textarea name="goal" id="goal"></textarea></td>
      </tr>
      <tr>
        <td class="label">강의방법</td>
        <td class="section"><textarea name="method" id="method"></textarea></td>
      </tr>
      <tr>
        <td class="label">강의내용</td>
        <td class="section"><textarea name="content" id="content"></textarea></td>
      </tr>
      <tr>
        <td class="label">평가방법</td>
        <td class="section"><textarea name="evaluation" id="evaluation"></textarea></td>
      </tr>
    </table>

    <div class="bottom-buttons">
      <button type="submit" formaction="<%=contextPath%>/professor/lectures/lectureplanadd.do">등록하기</button>
      <button type="submit" formaction="<%=contextPath%>/professor/lectures/lectureplanupdate.do">수정하기</button>
      <button type="submit" formaction="<%=contextPath%>/professor/lectures/lectureplandelete.do">삭제하기</button>
    </div>
  </form>
</div>
</body>
</html>
