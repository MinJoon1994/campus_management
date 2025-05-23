<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="professorvo.SubjectVo"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8"); // 한글 처리
	String contextPath = request.getContextPath();
	String professor_id = (String) request.getAttribute("professor_id");
%>
<%
	Vector<SubjectVo> subjectVo = (Vector<SubjectVo>) request.getAttribute("subjectList");
	// 시간표 초기화
	String[][] timetable = new String[5][9]; // [요일][교시]
	String[] days = { "월", "화", "수", "목", "금" };
    
	Map<String, String> subjectColorMap = new HashMap<>();
    
	for(SubjectVo v : subjectVo) {
    	String subjectName = v.getSubjectName();
    	System.out.println("과목명 : " + subjectName);
    	// 월 3-5교시, 화 4-7교시
    	String schedule = v.getSchedule();
    	System.out.println("시간표 : " + schedule);
    	
        // 과목별 고유 색상 만들기
        int hash = Math.abs(subjectName.hashCode()); // 
        String color = String.format("#%06X", (hash & 0xFFFFFF));
        subjectColorMap.put(subjectName, color);

        // 시간표 채우기
        String[] blocks = schedule.split(",");
        for (String block : blocks) {
            block = block.trim();
            // block[0] = 월 3-5교시
           	// block[1] = 화 4-7교시
            for (int i = 0; i < days.length; i++) {
                if (block.startsWith(days[i])) {
                	String timePart = block.replaceAll("[^0-9\\-]", ""); // 3-5
                	String[] range = timePart.split("-"); // range[0] = 3, range[1] = 5
					// 0부터 시작하는 timetable 배열 때문에 -1 처리
                	int start = Integer.parseInt(range[0]) - 1;
                	int end = (range.length > 1) ? Integer.parseInt(range[1]) - 1 : start;
					
                	for (int j = start; j <= end; j++) { // 2~4
                	    if (timetable[i][j] == null) timetable[i][j] = subjectName;
                	    // [월][2], [월][3], [월][4]
                	}
                }
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과목 등록</title>
	<style>
	    body { margin: 0; padding: 0; font-family: Arial, sans-serif; }
	
	    .main-wrapper { display: flex; justify-content: center; align-items: flex-start; gap: 10px;
	                    width: 90%; margin: 0 auto; margin-top: 20px; padding-top: 10px; }
	
	    .left-box, .right-box { max-width: 700px; }
	
	    .left-box { flex: 6; max-width: 700px; }
	
	    .right-box { margin-top: 100px; flex: 4; max-width: 1000px; }
	
	    .left-box table { margin: 0; width: 100%; border-collapse: collapse; background-color: #f9f9f9; }
	
	    .right-box table { margin: 0; width: 80%; border-collapse: collapse; background-color: #f9f9f9; table-layout: fixed;}
	    th, td { padding: 12px; border: 1px solid #ddd; text-align: center;}
	
	    th { background-color: #2c3e50; color: white; }
	
	    input[type="text"], input[type="number"], select {
	        padding: 8px; margin-top: 5px; width: 95%;
	    }
	
	    .day-time-row { display: flex; align-items: center; margin-bottom: 8px; }
	
	    .day-time-row select { margin-right: 10px; padding: 5px; width: 150px; }
	
	    .day-time-row button { margin-left: 10px; padding: 8px 16px; height: 40px;
	                           cursor: pointer; background-color: #e74c3c; color: white;
	                           border: none; border-radius: 4px; }
	
	    .add-button { margin-top: 10px; padding: 8px 16px; background-color: #3498db;
	                  color: white; border: none; border-radius: 4px; cursor: pointer; }
	
	    input[type="submit"] { padding: 10px 20px; margin-top: 20px; cursor: pointer; }
	
	    .input-with-button { display: flex; align-items: center; }
	
	    .input-with-button input { flex: 1; }
	
	    .input-with-button button { margin-left: 10px; padding: 8px 16px; cursor: pointer; }
	
	    .timetable-container { width: 90%; margin: 40px auto; padding-top: 20px; }
	
	    .timetable-container table { width: 80%; border-collapse: collapse; }
	
	    .timetable-container th, .timetable-container td {
	        width: 100px; height: 50px; text-align: center; border: 1px solid #ccc;
	    }
	
	    .full-fill-img { height: 100px; width: 100%; object-fit: cover; display: block; }
	</style>
	<script>
        let isSubjectCodeChecked = false;
        let isProfessorIdChecked = false;

        // 과목코드 중복체크
        function checkSubjectCode() {
            const subjectCode = document.getElementById('subject_code').value.trim();
            if (subjectCode === "") {
                alert("과목 코드를 입력하세요.");
                return;
            }
            fetch('<%=contextPath%>/professor/checkSubjectcodeDulicate?subject_code=' + encodeURIComponent(subjectCode))
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
            const currentCount = container.getElementsByClassName('day-time-row').length;

            if (currentCount >= 2) {
                alert("요일/시간은 최대 2개까지만 추가할 수 있습니다.");
                return;
            }
           
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
        // 과목 등록 전 유효성 검사
        // 1. 과목 코드 중복 체크 여부
        // 2. 교수 ID 확인 여부
        // 3. 요일/시간 입력값의 유효성 검사
        //     - 요일/시간 최소 1개 이상 추가
        // 4. 요일/시간 중복 체크 여부
        async function validateForm() {
            if (!isSubjectCodeChecked) {
                alert("과목 코드 중복 체크를 완료하세요!");
                return false;
            }
            if (!isProfessorIdChecked) {
                alert("교수 ID 일치 여부를 확인하세요!");
                return false;
            }
            // 요일/시간 최소 1개 이상 추가
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
				
                // 시작, 종료 교시 선택했는지 검사
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
		            alert((i + 1) + "번째 입력 " + day + " (" + start + "~" + (end) + "교시)는 이미 등록한 강의와 겹칩니다.");
		            return false;
		        }
		    }

		    return true;
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
<form id="lectureForm" action="<%=contextPath%>/professor/lecturecreate" 
	  method="post" onsubmit="return false;">
	<div class="main-wrapper">
		<div class="left-box">
				<table>
					<tr>
						<th>항목</th>
						<th>입력</th>
					</tr>
		
					<tr>
						<td>과목 코드</td>
						<td>
							<div class="input-with-button">
								<!-- oninput ="isSubjectCodeChecked=false;" 
									 사용자가 텍스트를 입력할 때마다 isSubjectCodeChecked라는 변수의 값을 false 로 변경 -->
								<input type="text" id="subject_code" name="subject_code" required oninput="isSubjectCodeChecked=false;">
								<button type="button" onclick="checkSubjectCode()">중복 체크</button>
							</div>
						</td>
					</tr>
					<tr>
						<td>과목 이름</td>
						<td><input type="text" name="subject_name"  maxlength="7" required></td>
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
							<div class="center_button" style="text-align: center;">
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
		</div>
		<div class="right-box">
			<table align="center">
			    <colgroup>
			        <col style="width:12.5%;"> <!-- 시간/요일 -->
			        <col style="width:17.5%;"> <!-- 월 -->
			        <col style="width:17.5%;"> <!-- 화 -->
			        <col style="width:17.5%;"> <!-- 수 -->
			        <col style="width:17.5%;"> <!-- 목 -->
			        <col style="width:17.5%;"> <!-- 금 -->
			    </colgroup>
			    <tr>
			        <th>시간/요일</th>
			        <th>월</th>
			        <th>화</th>
			        <th>수</th>
			        <th>목</th>
			        <th>금</th>
			    </tr>
			    <%
			        for (int i = 0; i < 9; i++) {
			    %>
					    <tr>
					        <td><%= (i+1) %>교시</td>
					        <%
					            for (int j = 0; j < 5; j++) {
					                String cell = timetable[j][i];
					                String bgColor = (cell != null && subjectColorMap.containsKey(cell)) ? subjectColorMap.get(cell) : "";
					        %>
					            <td style="background-color:<%= bgColor %>;">
					                <%= (cell != null ? cell : "") %>
					            </td>
					        <% 
					        	} 
					        %>
					    </tr>
			    <% 
			    	} 
			    %>
			</table>
			<input type="hidden" name="current_enrollment" value="0"> 
			<input type="submit" value="과목 등록" onclick="handleSubmit()" style="margin-top:30px; margin-left: 350px; width: 150px; height: 50px;">
		</div>
	</div>
</form>	
	
</body>
</html>
