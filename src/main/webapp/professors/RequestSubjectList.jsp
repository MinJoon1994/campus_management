<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>수정되지 않은 강의 목록</title>
    <style>
        body {
            margin: 0;  /* 화면을 꽉 차게 하기위한 기본설정 */
        	padding: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 90%;
            margin: 0 auto;
            padding-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #2c3e50;
            color: white;
        }

    </style>
</head>
<body>

<div class="container">
    <h2 style="text-align: center; padding-bottom: 20px;">개설 요청한 강의 목록</h2>

    <!-- subjectList가 requestScope에 있는지 확인하고 출력 -->
    <c:if test="${not empty requestScope.subjectList}">
        <table>
            <thead>
                <tr>
                    <th>과목 코드</th>
                    <th>과목 이름</th>
                    <th>과목 유형</th>
                    <th>개설 학년</th>
                    <th>분반</th>
                    <th>학점</th>
                    <th>교수명</th>
                    <th>현재 수강 인원</th>
                    <th>수강 가능 여부</th>
                </tr>
            </thead>
            <tbody>
                <!-- subjectList 반복 출력 -->
                <c:forEach var="subject" items="${requestScope.subjectList}">
                    <tr>
                        <td>${subject.subjectCode}</td>  <!-- VO 기준으로 subjectCode 출력 -->
                        <td>${subject.subjectName}</td>
                        <td>${subject.subjectType}</td>
                        <td>${subject.openGrade}</td>
                        <td>${subject.division}</td>
                        <td>${subject.credit}</td>
                        <td>${subject.professorName}</td>
                        <td>${subject.currentEnrollment}</td>
                        
                        <!-- 신청 승인 여부 처리 -->
						<td>
						    <c:choose>
						        <c:when test="${subject.isAvailable == true}">
						            승인
						        </c:when>
						        <c:otherwise>
						            대기중
						        </c:otherwise>
						    </c:choose>
						</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- subjectList가 비어 있거나 null일 경우 -->
    <c:if test="${empty requestScope.subjectList}">
        <p>수정되지 않은 과목이 없습니다.</p>
    </c:if>
</div>

</body>
</html>
