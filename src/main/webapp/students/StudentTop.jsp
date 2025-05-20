<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>학생 학사관리 시스템</title>
  <style>
    html, body {
      margin: 0;
      padding: 0;
      height: 100%;
      font-family: '맑은 고딕', sans-serif;
    }

    /* 부모 영역에서 .navbar를 정확히 꽉 채우기 위한 설정 */
    .navbar {
      background-color: #002147;
      height: 100%; /* 🔥 부모의 높이 100% 채움 */
      width: 100%;
      display: flex;
      justify-content: space-around;
      align-items: center;
      position: relative;
      z-index: 100;
    }

    .nav-item {
      position: relative;
    }

    .nav-link {
      color: white;
      padding: 14px 20px;
      text-decoration: none;
      display: block;
      font-weight: bold;
      cursor: pointer;
    }

    .nav-link:hover {
      background-color: #004080;
    }

    /* 드롭다운 메뉴 */
    .dropdown {
      display: none;
      position: absolute;
      top: 100%; /* 메뉴바 아래 */
      left: 0;
      background-color: white;
      min-width: 200px;
      box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
      z-index: 1000;
    }

    .dropdown a {
      color: #002147;
      padding: 12px 16px;
      text-decoration: none;
      display: block;
    }

    .dropdown a:hover {
      background-color: #f1f1f1;
    }

    .nav-item.active .dropdown {
      display: block;
    }
  </style>
</head>
<body>

  <div class="navbar">
    <div class="nav-item">
      <div class="nav-link">수강관리</div>
      <div class="dropdown">
        <a href="${pageContext.request.contextPath}/professor/lectureform">강의 개설 요청</a>
        <a href="${pageContext.request.contextPath}/professor/lecturerequest">나의 요청 강의 확인</a>
        <a href="${pageContext.request.contextPath}/professor/lectures">나의 강의목록 조회</a>
        <a href="${pageContext.request.contextPath}/professor/timetable">나의 시간표 조회</a>
      </div>
    </div>
    <div class="nav-item">
      <div class="nav-link">성적 관리</div>
      <div class="dropdown">
        <a href="${pageContext.request.contextPath}/professor/enrolledstudent">수강신청 학생명단 확인</a>
        <a href="${pageContext.request.contextPath}/student/profile">학생 정보 조회</a>
      </div>
    </div>
    <div class="nav-item">
      <div class="nav-link">시간표</div>
      <div class="dropdown">
        <a href="#">개인 시간표 확인</a>
      </div>
    </div>
        <div class="nav-item">
      <div class="nav-link">개인정보</div>
      <div class="dropdown">
        <a href="#">학적 변경 요청</a>
        <a href="#">비밀번호 변경</a>
        <a href="#">개인정보 수정</a>
      </div>
    </div>
    <div class="nav-item">
      <div class="nav-link">문의하기</div>
      <div class="dropdown">
        <a href="#">강의 관련 문의하기</a>
        <a href="#">학교 관련 문의하기</a>
      </div>
    </div>
  </div>

  <script>
    // 드롭다운 열고 닫기
    document.querySelectorAll('.nav-link').forEach(link => {
      link.addEventListener('click', function (e) {
        const parent = this.parentElement;
        const isActive = parent.classList.contains('active');

        // 모든 드롭다운 닫기
        document.querySelectorAll('.nav-item').forEach(item => {
          item.classList.remove('active');
        });

        // 현재만 열기
        if (!isActive) {
          parent.classList.add('active');
        }
      });
    });

    // 외부 클릭 시 닫기
    document.addEventListener('click', function (e) {
      if (!e.target.closest('.nav-item')) {
        document.querySelectorAll('.nav-item').forEach(item => {
          item.classList.remove('active');
        });
      }
    });
  </script>

</body>
</html>
