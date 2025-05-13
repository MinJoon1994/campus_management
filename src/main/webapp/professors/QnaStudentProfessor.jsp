<%@page import="professorvo.QnaStduentProfessorVo"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Vector" %>
<%@ page import="professorvo.LectureListVo" %>

<%
    Vector<QnaStduentProfessorVo> qnaList = (Vector<QnaStduentProfessorVo>) request.getAttribute("QnaList");
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>질문 목록</title>
    <style>
        body {
            font-family: Arial;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: 30px auto;
        }
        h2 {
            text-align: center;
        }
        .filter {
            margin-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f5f5f5;
        }
        .status-complete {
            color: white;
            background-color: red;
            padding: 2px 6px;
            border-radius: 4px;
        }
        .status-pending {
            color: black;
        }
        .delete-btn {
            float: right;
            margin-top: 10px;
            padding: 6px 12px;
        }
    </style>
	<script>
		function openQnaWindow(qnaId) {
		    var _width = 650;
		    var _height = 380;
		 
		    // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
		    var _left = Math.ceil(( window.screen.width - _width)/2);
		    var _top = Math.ceil(( window.screen.height - _height )/2); 
	
		    const url = '<%= contextPath %>/professor/qnadetail?qnaId=' + qnaId;
		    window.open(url , 'popup-test', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );
		}
		function deleteSelectedQna() {
		    const checkedBoxes = document.querySelectorAll('input[name="qnaIds"]:checked');
		    const qnaIds = Array.from(checkedBoxes).map(cb => cb.value);

		    if (qnaIds.length === 0) {
		        alert("삭제할 질문을 선택하세요.");
		        return;
		    }

		    fetch('<%=contextPath%>/professor/deleteqna', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/x-www-form-urlencoded'
		        },
		        body: new URLSearchParams({
		            qnaIds: qnaIds // 서버에서 request.getParameterValues("qnaIds")로 받을 수 있음
		        })
		    })
		    .then(response => {
		        if (!response.ok) throw new Error("삭제 실패");
		        return response.text();
		    })
		    .then(data => {
		        alert("삭제되었습니다.");
		        location.reload(); // 현재 페이지 다시 로드하여 리스트 갱신
		    })
		    .catch(error => {
		        alert("삭제 중 오류 발생: " + error.message);
		    });
		}
	</script>

</head>
<body>
<div class="container">
    <h2>학생 Q&A 목록</h2>

    <!-- 과목 필터 -->
    <div class="filter">
        과목 선택:
        <select id="subjectFilter" onchange="filterTable()">
            <option value="">-- 전체 과목 --</option>
            <% for (LectureListVo vo : subjectList) { %>
                <option value="<%= vo.getSubjectCode() %>"><%= vo.getSubjectName() %></option>
            <% } %>
        </select>
    </div>

    <!-- Q&A 테이블 -->
    <table id="qnaTable">
        <thead>
        <tr>
            <th>선택</th>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>날짜</th>
        </tr>
        </thead>
		<tbody>
		<% if (qnaList.size() == 0) { %>
		    <tr>
		        <td colspan="5">질문이 없습니다.</td>
		    </tr>
		<% } else {
		     int index = qnaList.size();
		     for (QnaStduentProfessorVo vo : qnaList) { %>
		    <tr data-subject="<%= vo.getSubjectCode() %>">
		        <td><input type="checkbox" name="qnaIds" value="<%= vo.getQnaId() %>" /></td>
		        <td><%= index-- %></td>
		        <td><a href="#" onclick="openQnaWindow(<%= vo.getQnaId() %>); return false;">
		            <%= vo.getQuestionerTitle() %></a></td>
		        <td><%= vo.getStudentName() %></td>
		        <td><%= vo.getQuestionTime().toLocalDateTime().toLocalDate() %></td>
		    </tr>
		<%   }
		   } %>
		</tbody>
    </table>
    <div style="text-align: right; margin-top: 10px;">
    <button type="button" class="delete-btn" onclick="deleteSelectedQna()">삭제</button>
</div>
</div>

<script>
    function toggleAll(checkbox) {
        const boxes = document.querySelectorAll('input[name="qnaIds"]');
        boxes.forEach(cb => cb.checked = checkbox.checked);
    }

    function filterTable() {
        const selectedSubject = document.getElementById("subjectFilter").value;
        const rows = document.querySelectorAll("#qnaTable tbody tr");
        rows.forEach(row => {
            const subjectCode = row.getAttribute("data-subject");
            row.style.display = (!selectedSubject || subjectCode === selectedSubject) ? "" : "none";
        });
    }
</script>
</body>
</html>
