<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8"); // 한글 처리
	String contextPath = request.getContextPath();
	String professor_id = (String) request.getAttribute("professor_id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과목 등록</title>
	<style>
		body {
			margin: 0;  /* 화면을 꽉 차게 하기위한 기본설정 */
	        padding: 0;
			font-family: Arial, sans-serif;
		}
		.container {
	        width: 90%;         /* 전체 폭의 90% 사용 (양쪽 5% 여백) */
	        margin: 0 auto;     /* 중앙 정렬 */
	        padding-top: 20px;
    	}
		table {
			width: 800px;
			border-collapse: collapse;
			margin: 0 auto;
			background-color: #f9f9f9;
		}
		
		th, td {
			padding: 12px;
			border: 1px solid #ddd;
		}
		
		th {
			background-color: #2c3e50;
			color: white;
			text-align: center;
		}
		
		input[type="text"], input[type="number"], select {
			padding: 8px;
			margin-top: 5px;
			width: 95%;
		}
		
		.day-time-row {
			display: flex;
			align-items: center;
			margin-bottom: 8px;
		}
		
		.day-time-row select {
			margin-right: 10px;
			padding: 5px;
			width: 150px;
		}
		
		.day-time-row button {
			margin-left: 10px;
			padding: 8px 16px;
			height: 40px;
			cursor: pointer;
			background-color: #e74c3c;
			color: white;
			border: none;
			border-radius: 4px;
		}
		
		.add-button {
			margin-top: 10px;
			padding: 8px 16px;
			background-color: #3498db;
			color: white;
			border: none;
			border-radius: 4px;
			cursor: pointer;
		}
		
		input[type="submit"] {
			padding: 10px 20px;
			margin-top: 20px;
			cursor: pointer;
		}
		
		.center_button {
			text-align: center;
		}
		.input-with-button {
		    display: flex;
		    align-items: center;
		}
		
		.input-with-button input {
		    flex: 1; /* input은 남은 영역 모두 차지 */
		}
		
		.input-with-button button {
		    margin-left: 10px; /* 버튼과 input 사이 약간의 간격 */
		    padding: 8px 16px;
		    cursor: pointer;
		}
	</style>
	<script>
        let isSubjectCodeChecked = false;
        let isProfessorIdChecked = false;

        async function validateForm() {
            if (!isSubjectCodeChecked) {
                alert("과목 코드 중복 체크를 완료하세요!");
                return false;
            }
            if (!isProfessorIdChecked) {
                alert("교수 ID 일치 여부를 확인하세요!");
                return false;
            }
            
            const dayInputs = document.getElementsByName("day[]");
            if (dayInputs.length === 0) {
                alert("개설 요일/시간을 최소 1개 이상 추가해야 합니다!");
                return false;
            }
            // 요일/시간 체크
            const days = document.getElementsByName("day[]"); // ex 월, 월, 수
            const startTimes = document.getElementsByName("start_time[]"); // ex 1, 2, 5
            const endTimes = document.getElementsByName("end_time[]"); // ex 3, 3, 6
			
            let tempDay = {"월": [], "화": [], "수": [], "목": [], "금": []};
            let promises = [];
            
            for (let i = 0; i < startTimes.length; i++) {
            	// i = 0 일때 월 1 ~ 3
            	// i = 1 일때 월 2 ~ 3
            	// i = 2 일때 수 5 ~ 6
            	const day = days[i].value;
                const start = parseInt(startTimes[i].value);
                const end = parseInt(endTimes[i].value);
				
                if (isNaN(start) || isNaN(end)) {
                    alert((i+1) + "번째 요일/시간이 비어 있습니다.");
                    return false;
                }
                // 시작 교시가 종료 교시보다 늦을 때
                if (start > end) {
                    alert((i+1) + "번째 시작 교시가 종료 교시보다 늦습니다.");
                    return false;
                }
                // 겹치는지 검사
		        for (let t = start; t <= end; t++) {
		            if (tempDay[day].includes(t)) {
		                alert((i + 1)+"번째 입력 ("+day+" "+start+"~"+end+"교시)가 이전 입력과 겹칩니다.");
		                return false;
		            }
		        }
		        
		        // 겹치지 않으면 tempDay에 시간 등록
		        for (let t = start; t <= end; t++) {
		            tempDay[day].push(t);
		        }
		     
		        // 중복 체크 fetch (await로 바로 응답 확인)
		        const res = await fetch("<%=contextPath%>/professors/check_schedule_overlap.jsp", {
		            method: "POST",
		            headers: { "Content-Type": "application/json" },
		            body: JSON.stringify({ professorId: "<%=professor_id%>", day, start, end })
		        });
		        const result = await res.text();

		        if (result.trim() === "DUPLICATE") {
		            alert(`${i + 1}번째 입력 (${day} ${start}~${end}교시)는 DB에 이미 존재하는 강의와 겹칩니다.`);
		            return false;
		        }
		    }

		    return true;
        }

        // 과목코드 중복체크
        function checkSubjectCode() {
            const subjectCode = document.getElementById('subject_code').value.trim();
            if (subjectCode === "") {
                alert("과목 코드를 입력하세요.");
                return;
            }
            fetch('<%=contextPath%>/professors/check_subject_code.jsp?subject_code=' + encodeURIComponent(subjectCode))
                .then(response => response.text())
                .then(result => {
                    if (result.trim() === "OK") {
                        alert("사용 가능한 과목 코드입니다!");
                        isSubjectCodeChecked = true;
                    } else if (result.trim() === "DUPLICATE") {
                        alert("이미 존재하는 과목 코드입니다.");
                        isSubjectCodeChecked = false;
                    } else {
                        alert("서버 오류가 발생했습니다.");
                        isSubjectCodeChecked = false;
                    }
                })
                .catch(error => {
                    console.error("에러 발생:", error);
                    alert("과목 코드 중복 체크 실패!");
                    isSubjectCodeChecked = false;
                });
        }
		// 담당교수 ID가 유효한지(DB에 있는 아이디인지)
        function checkProfessorId() {
			const professorId = <%=professor_id%>;
            const professorInput = document.getElementById('professor_id').value.trim();
            if (professorInput === "") {
                alert("교수 ID를 입력하세요.");
                return;
            } else if (professorId === Number(professorInput)) {
            	alert("교수 ID 일치합니다!");
            	isProfessorIdChecked = true;
            } else {
            	alert("교수 ID가 불일치합니다!");
            	return;
            }
        }
		// 수업 요일/시작 교시/종료 교시 입력란 한 세트를 동적으로 추가
        function addDayTimeRow() {
            const container = document.getElementById('dayTimeContainer');
            const row = document.createElement('div');
            row.className = 'day-time-row';

            row.innerHTML = `
                <select name="day[]" required>
                    <option value="">요일 선택</option>
                    <option value="월">월</option>
                    <option value="화">화</option>
                    <option value="수">수</option>
                    <option value="목">목</option>
                    <option value="금">금</option>
                </select>
                <select name="start_time[]" required>
                    <option value="">시작 교시</option>
                    <option value="1">1교시</option>
                    <option value="2">2교시</option>
                    <option value="3">3교시</option>
                    <option value="4">4교시</option>
                    <option value="5">5교시</option>
                    <option value="6">6교시</option>
                    <option value="7">7교시</option>
                    <option value="8">8교시</option>
                    <option value="9">9교시</option>
                </select>
                <select name="end_time[]" required>
                    <option value="">종료 교시</option>
                    <option value="1">1교시</option>
                    <option value="2">2교시</option>
                    <option value="3">3교시</option>
                    <option value="4">4교시</option>
                    <option value="5">5교시</option>
                    <option value="6">6교시</option>
                    <option value="7">7교시</option>
                    <option value="8">8교시</option>
                    <option value="9">9교시</option>
                </select>
                <button type="button" onclick="removeDayTimeRow(this)">삭제</button>
            `;
            container.appendChild(row);
        }

        function removeDayTimeRow(button) {
            if (confirm('정말 삭제하시겠습니까?')) {
                button.parentElement.remove();
            }
        }
        // 제출 버튼 처리
        async function handleSubmit() {
            const isValid = await validateForm();
            if (isValid) {
                document.getElementById("lectureForm").submit(); // 수동 제출
            }
        }
    </script>
</head>

<body>
	<div class="container">
		<h2 style="text-align: center; padding-bottom: 20px;">과목 등록</h2>
		<form action="<%=contextPath%>/professor/lecturecreate" method="post"
			onsubmit="return false;">
			<table>
				<tr>
					<th>항목</th>
					<th>입력</th>
				</tr>
	
				<tr>
					<td>과목 코드</td>
					<td>
						<div class="input-with-button">
							<input type="text" id="subject_code" name="subject_code" required oninput="isSubjectCodeChecked=false;">
							<button type="button" onclick="checkSubjectCode()">중복 체크</button>
						</div>
					</td>
				</tr>
				<tr>
					<td>과목 이름</td>
					<td><input type="text" name="subject_name" required></td>
				</tr>
				<tr>
					<td>과목 유형</td>
					<td><select name="subject_type" required>
							<option value="전공">전공</option>
							<option value="교양">교양</option>
					</select></td>
				</tr>
				<tr>
					<td>개설 학년</td>
					<td><select name="open_grade" required>
							<option value="1">1학년</option>
							<option value="2">2학년</option>
							<option value="3">3학년</option>
							<option value="4">4학년</option>
					</select></td>
				</tr>
				<tr>
					<td>분반</td>
					<td><input type="text" name="division" required></td>
				</tr>
				<tr>
					<td>학점</td>
					<td><input type="number" name="credit" required></td>
				</tr>
				<tr>
					<td>담당 교수 ID</td>
					<td>
						<div class="input-with-button">
							<input type="number" id="professor_id" name="professor_id"
								required oninput="isProfessorIdChecked=false;">
							<button type="button" onclick="checkProfessorId()">교수 확인</button>
						</div>
					</td>
				</tr>
				<tr>
					<td>담당 교수 이름</td>
					<td><input type="text" name="professor_name" required></td>
				</tr>
	
				<tr>
					<td>개설 요일/시간</td>
					<td>
						<div id="dayTimeContainer"></div>
						<div class="center_button">
							<button type="button" class="add-button" onclick="addDayTimeRow()">
							요일/시간 추가
							</button>
						</div>
					</td>
				</tr>
	
				<tr>
					<td>수강 정원</td>
					<td><input type="number" name="capacity" required></td>
				</tr>
			</table>
	
			<div class="center_button">
				<input type="hidden" name="current_enrollment" value="0"> 
				<input type="submit" value="과목 등록" onclick="handleSubmit()">
			</div>
		</form>
	</div>
</body>
</html>
