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
    <title>성적 수정</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 90%;
            margin: 0 auto;
            padding-top: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #2c3e50;
            color: white;
        }
        input[type="number"] {
            width: 60px;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #007bff;
        }
        .pagination strong {
            margin: 0 5px;
            font-weight: bold;
            color: black;
        }
    </style>

    <script>
        const gradeList = <%= gradeJson %>;
        let currentPage = 1;
        const pageSize = 10;

        function calculateScore(att, assign, mid, fin) {
            return att * 0.1 + assign * 0.3 + mid * 0.3 + fin * 0.3;
        }

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

        function searchGrades(page = 1) {
            currentPage = page;

            const subjectCode = document.getElementById("subject_code").value;
            const openGrade = document.getElementById("open_grade").value;
            const studentName = document.getElementById("student_name").value.trim();
            const tbody = document.getElementById("gradeBody");
            const paginationDiv = document.getElementById("pagination");

            tbody.innerHTML = "";
            paginationDiv.innerHTML = "";

            const filtered = gradeList.filter(vo => {
                const matchSubject = !subjectCode || vo.subjectCode === subjectCode;
                const matchGrade = !openGrade || vo.openGrade == openGrade;
                const matchName = !studentName || vo.studentName.includes(studentName);
                return matchSubject && matchGrade && matchName;
            });

            if (filtered.length === 0) {
                tbody.innerHTML = "<tr><td colspan='13'>검색 결과 없음</td></tr>";
                return;
            }

            const totalPage = Math.ceil(filtered.length / pageSize);
            const startIndex = (page - 1) * pageSize;
            const endIndex = Math.min(startIndex + pageSize, filtered.length);
            const pagedList = filtered.slice(startIndex, endIndex);

            pagedList.forEach(vo => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>\${vo.subjectCode}</td>
                    <td>\${vo.subjectName}</td>
                    <td>\${vo.openGrade}</td>
                    <td>\${vo.studentNumber}</td>
                    <td>\${vo.studentName}</td>
                    <td>\${vo.department}</td>
                    <td><input type="number" name="attendance" value="0" /></td>
                    <td><input type="number" name="assignment" value="0" /></td>
                    <td><input type="number" name="midterm" value="0" /></td>
                    <td><input type="number" name="finalExam" value="0" /></td>
                    <td class="totalScore">\${vo.score != null ? vo.score : "-"}</td>
                    <td class="grade">\${vo.grade != null ? vo.grade : "-"}</td>
                    <td><button type="button" onclick="submitUpdate(this)">수정하기</button></td>
                `;
                tbody.appendChild(tr);

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

            const pageBlock = 5;
            const blockStart = Math.floor((currentPage - 1) / pageBlock) * pageBlock + 1;
            const blockEnd = Math.min(blockStart + pageBlock - 1, totalPage);

            if (blockStart > 1) {
                paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${blockStart - 1})">&#9664;</a>`;
            }
            for (let i = blockStart; i <= blockEnd; i++) {
                if (i === currentPage) {
                    paginationDiv.innerHTML += `<strong>\${i}</strong>`;
                } else {
                    paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${i})">\${i}</a>`;
                }
            }
            if (blockEnd < totalPage) {
                paginationDiv.innerHTML += `<a href="#" onclick="searchGrades(\${blockEnd + 1})">&#9654;</a>`;
            }
        }

        function submitUpdate(button) {
            const tr = button.closest("tr");
            const att = parseFloat(tr.querySelector("input[name='attendance']").value) || 0;
            const assign = parseFloat(tr.querySelector("input[name='assignment']").value) || 0;
            const mid = parseFloat(tr.querySelector("input[name='midterm']").value) || 0;
            const fin = parseFloat(tr.querySelector("input[name='finalExam']").value) || 0;

            if ([att, assign, mid, fin].some(score => score < 0 || score > 100)) {
                alert("각 점수는 0 이상 100 이하로 입력해야 합니다.");
                return;
            }

            const total = calculateScore(att, assign, mid, fin).toFixed(2);
            const grade = getGrade(total);

            const rowData = {
                subjectCode: tr.children[0].innerText,
                studentNumber: tr.children[3].innerText,
                totalScore: parseFloat(total),
                grade: grade
            };

            fetch("<%=contextPath%>/professor/gradesupdate.do", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(rowData)
            })
            .then(res => res.text())
            .then(msg => alert("성적을 수정하였습니다."))
            .catch(err => alert("요청 실패: " + err));
        }

        window.onload = () => searchGrades();
    </script>
</head>
<body>
<div class="container">
    <h2 style="text-align: center;">성적 조회 및 수정</h2>
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

    <div id="pagination" class="pagination"></div>
</div>
</body>
</html>
