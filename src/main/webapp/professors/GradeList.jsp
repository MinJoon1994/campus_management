<%@page import="com.google.gson.Gson"%>
<%@page import="professorvo.GradeVo"%>
<%@page import="professorvo.LectureListVo"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>성적 조회</title>

    <script>
        // gradeList는 JSP 페이지에서 전달된 성적 데이터 리스트입니다. 이를 JavaScript 객체로 변환합니다.
        const gradeList = <%= gradeJson %>;

        // 현재 페이지를 추적하는 변수
        let currentPage = 1;

        // 한 페이지에 보여줄 항목의 수
        const pageSize = 10;

        function searchGrades(page = 1) {
            currentPage = page;  // 현재 페이지 업데이트

            // 필터링된 데이터를 가져오기 위해 사용자가 선택한 필터 값들을 가져옵니다.
            const subjectCode = document.getElementById("subject_code").value;  // 선택된 과목 코드
            const openGrade = document.getElementById("open_grade").value;  // 선택된 개설 학년
            const studentName = document.getElementById("student_name").value.trim();  // 입력된 학생 이름

            // tbody와 paginationDiv는 데이터를 출력할 DOM 요소입니다.
            const tbody = document.getElementById("gradeBody");
            const paginationDiv = document.getElementById("pagination");

            // 테이블을 새로 갱신하기 위해 기존 내용을 지웁니다.
            tbody.innerHTML = "";
            paginationDiv.innerHTML = "";

            // 성적 리스트에서 필터링을 합니다.
            const filtered = gradeList.filter(vo => {
                const matchSubject = !subjectCode || vo.subjectCode === subjectCode;  // 과목 코드 필터링
                const matchGrade = !openGrade || vo.openGrade == openGrade;  // 학년 필터링
                const matchName = !studentName || vo.studentName.includes(studentName);  // 학생 이름 필터링
                return matchSubject && matchGrade && matchName;  // 필터 조건을 만족하는 항목을 반환
            });

            // 필터링된 결과가 없으면 "검색 결과 없음" 메시지를 표시합니다.
            if (filtered.length === 0) {
                tbody.innerHTML = "<tr><td colspan='10' style='text-align:center;'>검색 결과 없음</td></tr>";
                return;  // 더 이상 처리하지 않고 함수 종료
            }

            // 🔥 페이지네이션 처리
            const totalPage = Math.ceil(filtered.length / pageSize); // 전체 페이지 수 계산
            const startIdx = (currentPage - 1) * pageSize;
            const endIdx = Math.min(startIdx + pageSize, filtered.length);
            const pagedList = filtered.slice(startIdx, endIdx); // 현재 페이지에 해당하는 리스트를 잘라냅니다.
			console.log("totalPage : "+totalPage);
			console.log("pageList : "+pagedList);
            
            // 필터링된 데이터를 테이블에 출력합니다.
            pagedList.forEach(vo => {
                const tr = document.createElement("tr");  // 새로운 tr 요소를 생성합니다.
                tr.innerHTML = `
                    <td>\${vo.subjectCode}</td>
                    <td>\${vo.subjectName}</td>
                    <td>\${vo.openGrade}</td>
                    <td>\${vo.studentNumber}</td>
                    <td>\${vo.studentName}</td>
                    <td>\${vo.department}</td>
                    <td>\${vo.score != null ? vo.score : '-'}</td>
                    <td>\${vo.grade != null ? vo.grade : '-'}</td>
                `;
                tbody.appendChild(tr);  // 테이블에 tr을 추가합니다.
            });

            // 🔄 페이지네이션 처리
            const pageBlock = 5;  // 한 번에 보여줄 페이지 블록 크기
            const blockStart = Math.floor((currentPage - 1) / pageBlock) * pageBlock + 1;
            const blockEnd = Math.min(blockStart + pageBlock - 1, totalPage);
			console.log("blockStart : "+blockStart);
			console.log("blockEnd : "+blockEnd);
			
            // ◀ 이전 블록 버튼
            if (blockStart > 1) {
                paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${blockStart - 1})">&#9664;</a>`;
            }

            // 페이지 번호들 출력
            for (let i = blockStart; i <= blockEnd; i++) {
                if (i === currentPage) {
                    paginationDiv.innerHTML += `<strong>\${i}</strong>`; // 현재 페이지는 강조
                } else {
                    paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${i})">\${i}</a>`; // 클릭 가능한 페이지 번호
                }
            }

            // ▶ 다음 블록 버튼
            if (blockEnd < totalPage) {
                paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${blockEnd + 1})">&#9654;</a>`;
            }
        }

        window.onload = function () {
            searchGrades();
        };
    </script>

    <style>
        body {
            margin: 0;  /* 화면을 꽉 차게 하기위한 기본설정 */
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 90%;         /* 전체 폭의 70% 사용 (양쪽 15% 여백) */
            margin: 0 auto;     /* 중앙 정렬 */
            padding-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
        }

        .pagination strong {
            margin: 0 5px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 style="text-align: center; padding-bottom: 20px;">성적 조회</h2>

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

        <br />

        <table border="1" cellpadding="10" cellspacing="0" width="100%">
            <thead style="background-color: #2c3e50; color: white;">
                <tr>
                    <th>과목코드</th>
                    <th>과목명</th>
                    <th>학년</th>
                    <th>학번</th>
                    <th>이름</th>
                    <th>학과</th>
                    <th>점수</th>
                    <th>등급</th>
                </tr>
            </thead>
            <tbody id="gradeBody">
                <tr><td colspan="10" style="text-align:center;">검색 결과 없음</td></tr>
            </tbody>
        </table>

        <div id="pagination" class="pagination" style="text-align:center; margin-top:20px;"></div>
    </div>
</body>
</html>
