<%@page import="java.util.List"%>
<%@page import="professorvo.QnaStduentProfessorVo"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Vector" %>
<%@ page import="professorvo.LectureListVo" %>

<%
    Vector<QnaStduentProfessorVo> qnaList = (Vector<QnaStduentProfessorVo>) request.getAttribute("QnaList");
    Vector<LectureListVo> subjectList = (Vector<LectureListVo>) request.getAttribute("subjectList");
    String contextPath = request.getContextPath();
    
    int pageSize = 10; // 한 페이지에 출력할 강의 수
    int pageNum = 1;   // 기본 페이지
    if (request.getParameter("pageNum") != null) {
        pageNum = Integer.parseInt(request.getParameter("pageNum"));
    }

    int startRow = (pageNum - 1) * pageSize;
    // 1페이지: (1 - 1) * 10 = 0 → 인덱스 0부터 시작
	// 2페이지: (2 - 1) * 10 = 10 → 인덱스 10부터 시작
    int endRow = Math.min(startRow + pageSize, qnaList != null ? qnaList.size() : 0);
    List<QnaStduentProfessorVo> pageList = (qnaList != null) ? qnaList.subList(startRow, endRow) : new Vector<QnaStduentProfessorVo>();
    request.setAttribute("pageList", pageList);
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
            margin: 10px auto;
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
		    var _height = 800;
		 
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

		    const params = new URLSearchParams();
		    qnaIds.forEach(id => params.append("qnaIds", id));

		    fetch('<%=contextPath%>/professor/deleteqna', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/x-www-form-urlencoded'
		        },
		        body: params
		    })
		    .then(response => {
		        if (!response.ok) throw new Error("삭제 실패");
		        return response.text();
		    })
		    .then(data => {
		        alert("삭제되었습니다.");
		        location.reload(); // 새로고침으로 목록 갱신
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
			 int index = (pageNum - 1) * pageSize + 1;
		     for (QnaStduentProfessorVo vo : pageList) { %>
		    <tr data-subject="<%= vo.getSubjectCode() %>">
		        <td><input type="checkbox" name="qnaIds" value="<%= vo.getQnaId() %>" /></td>
		        <td><%= index++ %></td>
		        <td><a href="#" onclick="openQnaWindow(<%= vo.getQnaId() %>); return false;">
		            <%= vo.getQuestionerTitle() %></a></td>
		        <td><%= vo.getStudentName() %></td>
		        <td><%= vo.getQuestionTime().toLocalDateTime().toLocalDate() %></td>
		    </tr>
		<%   }
		   } %>
		</tbody>
    </table>
           	<div class="pagination" style="text-align:center; margin-top:20px;">
			<%
			    // list가 null이 아니고 비어있지 않은 경우에만 페이지네이션 처리
			    if (qnaList != null && !qnaList.isEmpty()) {
			        // 전체 페이지 수 계산 (list.size()는 전체 데이터 수)
			        int totalPage = (int)Math.ceil((double)qnaList.size() / pageSize); 
			        int pageBlock = 5; // 한 번에 보여줄 페이지 수
			        // 현재 페이지 번호를 기준으로 시작 페이지 계산
			        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
			        // 끝 페이지 계산 (현재 페이지가 마지막 페이지가 아닐 경우에만 해당)
			        int endPage = startPage + pageBlock - 1;
			        // 만약 endPage가 전체 페이지 수를 초과하면 endPage는 totalPage로 설정
			        if (endPage > totalPage) endPage = totalPage;
			
			        // 시작 페이지가 1보다 클 경우, 이전 페이지로 이동하는 버튼 생성
			        if (startPage > 1) {
			%>
			            <!-- 이전 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=startPage - 1%>">&#9664;</a>
			<%
			        }
			        // startPage부터 endPage까지의 페이지 번호를 출력
			        for (int i = startPage; i <= endPage; i++) {
			            // 현재 페이지가 선택된 페이지인 경우 강조 표시
			            if (i == pageNum) {
			%>
			                <!-- 현재 페이지 번호는 강조 처리 -->
			                <strong><%=i%></strong>
			<%
			            } else {
			%>
			                <!-- 현재 페이지가 아닌 경우 링크로 출력 -->
			                <a href="?pageNum=<%=i%>"><%=i%></a>
			<%
			            }
			        }
			        // endPage가 totalPage보다 작을 경우, 다음 페이지로 이동하는 버튼 생성
			        if (endPage < totalPage) {
			%>
			            <!-- 다음 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=endPage + 1%>">&#9654;</a>
			<%
			        }
			    }
			%>
		</div>
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
