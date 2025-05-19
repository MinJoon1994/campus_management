<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.GradeVo"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Vector<GradeVo> gradeList = (Vector<GradeVo>) request.getAttribute("gradeList");
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    Gson gson = new Gson();
    String gradeJson = gson.toJson(gradeList);
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>성적 입력</title>
    <style>
        body { font-family: Arial; margin: 0; padding: 0; }
        .container { width: 90%; margin: 0 auto; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        th { background-color: #2c3e50; color: white; }
        input[type="number"] { width: 60px; }
    </style>
    
	<script>
	    // 서버에서 전달받은 전체 성적 리스트 (JSON 형태)
	    const gradeList = <%= gradeJson %>;
	    console.log(gradeList);
	    /*
	    [
	        {
	            "subjectCode": "4343",
	            "subjectName": "성적입력테스트",
	            "studentId": 1008,
	            "studentName": "학생1008",
	            "studentNumber": "S1008",
	            "department": "컴퓨터공학과",
	            "enrollmentId": 20232,
	            "openGrade": 1,
	            "score": 0
	        }
	    ]	    
	    */
	    
	    // 총점 계산 함수 (출석 10%, 과제 30%, 중간 30%, 기말 30%)
	    function calculateScore(att, assign, mid, fin) {
	        return att * 0.1 + assign * 0.3 + mid * 0.3 + fin * 0.3;
	    }
	
	    // 점수에 따른 등급 계산 함수
	    function getGrade(score) {
	        if (score >= 95) return 'A+';
	        if (score >= 90) return 'A';
	        if (score >= 85) return 'B+';
	        if (score >= 80) return 'B';
	        if (score >= 75) return 'C+';
	        if (score >= 70) return 'C';
	        if (score >= 65) return 'D+';
	        if (score >= 60) return 'D';
	        return 'F';
	    }
	
	    // 성적 리스트에서 필터 조건(과목/학년/이름)에 맞는 학생들을 화면에 렌더링
	    function searchGrades(page = 1) {
    currentPage = page;

    const subjectCode = document.getElementById("subject_code").value;
    const openGrade = document.getElementById("open_grade").value;
    const studentName = document.getElementById("student_name").value.trim();
    const tbody = document.getElementById("gradeBody");
    const paginationDiv = document.getElementById("pagination");

    tbody.innerHTML = "";           // 기존 목록 초기화
    paginationDiv.innerHTML = "";  // 페이지네이션 영역 초기화

    // 필터 조건에 맞는 데이터만 필터링
    const filtered = gradeList.filter(vo => {
        const matchSubject = !subjectCode || vo.subjectCode === subjectCode;
        const matchGrade = !openGrade || vo.openGrade == openGrade;
        const matchName = !studentName || vo.studentName.includes(studentName);
        return matchSubject && matchGrade && matchName;
    });

    // 결과 없을 경우 메시지 출력
    if (filtered.length === 0) {
        tbody.innerHTML = "<tr><td colspan='13'>검색 결과 없음</td></tr>";
        return;
    }

    // 페이징 처리
    const pageSize = 10;
    const totalPage = Math.ceil(filtered.length / pageSize);
    const startIndex = (page - 1) * pageSize;
    const endIndex = startIndex + pageSize;
    const pagedList = filtered.slice(startIndex, endIndex);

    // 출력
    pagedList.forEach(vo => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>\${vo.subjectCode}</td>
            <td>\${vo.subjectName}</td>
            <td>\${vo.openGrade}</td>
            <td>\${vo.studentNumber}</td>
            <td>\${vo.studentName}</td>
            <td>\${vo.department}</td>
            <td><input type="number" name="attendance" value="0"/></td>
            <td><input type="number" name="assignment" value="0"/></td>
            <td><input type="number" name="midterm" value="0"/></td>
            <td><input type="number" name="finalExam" value="0"/></td>
            <td class="totalScore">-</td>
            <td class="grade">-</td>
            <td><button type="button" onclick="submitUpdate(this)">등록하기</button></td>
        `;
        tr.dataset.enrollmentId = vo.enrollmentId;
        tr.dataset.professorId = "<%= session.getAttribute("id") %>";
        tbody.appendChild(tr);

        // 실시간 총점/등급 계산
        tr.querySelectorAll("input[type='number']").forEach(input => {
            input.addEventListener("input", () => {
                const att = parseFloat(tr.querySelector("input[name='attendance']").value) || 0;
                const assign = parseFloat(tr.querySelector("input[name='assignment']").value) || 0;
                const mid = parseFloat(tr.querySelector("input[name='midterm']").value) || 0;
                const fin = parseFloat(tr.querySelector("input[name='finalExam']").value) || 0;
                const total = calculateScore(att, assign, mid, fin);
                tr.querySelector(".totalScore").innerText = total.toFixed(2);
                tr.querySelector(".grade").innerText = getGrade(total);
            });
        });
    });

    // 페이지네이션 버튼 생성
    for (let i = 1; i <= totalPage; i++) {
        const pageBtn = document.createElement("button");
        pageBtn.textContent = i;
        pageBtn.onclick = () => searchGrades(i);
        if (i === currentPage) {
            pageBtn.style.fontWeight = "bold";
            pageBtn.style.backgroundColor = "#ddd";
        }
        paginationDiv.appendChild(pageBtn);
    }
}
	
	    // '등록하기' 버튼 클릭 시 해당 행의 총점과 등급을 서버로 fetch로 전송
		function submitUpdate(button) {
		    const tr = button.closest("tr");
		
		    // 입력값 정수로 파싱
		    const att = parseFloat(tr.querySelector("input[name='attendance']").value) || 0;
		    const assign = parseFloat(tr.querySelector("input[name='assignment']").value) || 0;
		    const mid = parseFloat(tr.querySelector("input[name='midterm']").value) || 0;
		    const fin = parseFloat(tr.querySelector("input[name='finalExam']").value) || 0;
		
		    // 유효성 검사 (0~100 범위)
		    const invalid = [att, assign, mid, fin].some(score => score < 0 || score > 100);
		    if (invalid) {
		        alert("각 점수는 0 이상 100 이하의 수로 입력해야 합니다.");
		        return;
		    }
		
		    // 총점 계산
		    const total = parseFloat((att * 0.1 + assign * 0.3 + mid * 0.3 + fin * 0.3).toFixed(2));
		    const grade = getGrade(total);
		
		    // 서버로 전송할 데이터
		    const rowData = {
	    	    enrollmentId: parseInt(tr.dataset.enrollmentId),
	    	    registeredBy: parseInt(tr.dataset.professorId),
	    	    score: total,
	    	    grade: grade
		    };
		
		    fetch("<%=contextPath%>/professor/gradesinsert.do", {
		        method: "POST",
		        headers: { "Content-Type": "application/json" },
		        body: JSON.stringify(rowData)
		    })
		    .then(res => {
		        if (!res.ok) throw new Error("서버 응답 오류");
		        return res.text();
		    })
		    .then(msg => alert("성적을 등록하였습니다."))
		    .catch(err => alert("요청 실패 : " + err));
		}


	</script>

</head>
<body>
<div class="container">
    <h2 style="text-align: center;">성적 조회 및 입력</h2>
    <form onsubmit="return false;">
        <label>과목 선택:
            <select id="subject_code">
                <option value="">-- 전체 과목 --</option>
                <% for (LectureListVo vo : subjectList) { %>
                    <option value="<%=vo.getSubjectCode()%>"><%=vo.getSubjectName()%></option>
                <% } %>
            </select>
        </label>
        &nbsp;&nbsp;
        <label>개설 학년:
            <select id="open_grade">
                <option value="">-- 전체 학년 --</option>
                <option value="1">1학년</option>
                <option value="2">2학년</option>
                <option value="3">3학년</option>
                <option value="4">4학년</option>
            </select>
        </label>
        &nbsp;&nbsp;
        <label>학생 이름: <input type="text" id="student_name" /></label>
        <button type="button" onclick="searchGrades()">검색</button>
    </form>

    <table>
        <thead>
            <tr>
                <th>과목코드</th>
                <th>과목명</th>
                <th>학년</th>
                <th>학번</th>
                <th>이름</th>
                <th>학과</th>
                <th>출석</th>
                <th>과제</th>
                <th>중간</th>
                <th>기말</th>
                <th>총점</th>
                <th>등급</th>
                <th>수정</th>
            </tr>
        </thead>
        <tbody id="gradeBody">
            <tr><td colspan="13" style="text-align:center;">검색 결과 없음</td></tr>
        </tbody>
    </table>
    <div id="pagination" style="text-align:center; margin-top: 20px;"></div>
</div>
</body>
</html>
