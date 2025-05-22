<%@page import="student.vo.StudentSubjectVO"%>
<%@page import="student.vo.StudentQnaListVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Vector" %>

<%
    List<StudentQnaListVO> qnaList = (ArrayList<StudentQnaListVO>) request.getAttribute("qnaList");
    List<StudentSubjectVO> subjectList = (ArrayList<StudentSubjectVO>) request.getAttribute("subjectList");
    String subjectCode = request.getParameter("subjectCode");
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
    List<StudentQnaListVO> pageList = (qnaList != null) ? qnaList.subList(startRow, endRow) : new ArrayList<StudentQnaListVO>();
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
        .add-btn {
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
	
		    const url = '<%= contextPath %>/student/qnaStudentDetail?qnaId=' + qnaId;
		    window.open(url , 'popup-test', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );
		}
	    function openQnaForm() {
	        document.getElementById("qnaWriteForm").style.display = "block";
	        window.scrollTo({
	            top: document.getElementById("qnaWriteForm").offsetTop - 50,
	            behavior: 'smooth'
	        });
	    }
	    function closeNoticeForm() {
	        document.getElementById("qnaWriteForm").style.display = "none";
	    }
	</script>

</head>
<body>
<div class="container">
    <h2>학생 Q&A 목록</h2>

    <!-- 과목 필터 -->
    <div class="filter">
        과목 선택:
        <select id="subjectFilter" onchange="location.href='<%=contextPath%>/student/qnalistbySubject?subjectCode=' + this.value;">
			<option value="" <%= (subjectCode == null || subjectCode.equals("")) ? "selected" : "" %>>
				-- 전체 과목 --
			</option>
		    <% for (StudentSubjectVO vo : subjectList) { %>
		        <option value="<%= vo.getSubjectCode() %>" 
		            <%= vo.getSubjectCode().equals(subjectCode) ? "selected" : "" %>>
		            <%= vo.getSubjectName() %>
		        </option>
		    <% } %>
        </select>
    </div>

    <!-- Q&A 테이블 -->
    <table id="qnaTable">
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>날짜</th>
        </tr>
        </thead>
		<tbody>
		<% if (pageList.size() == 0) { %>
		    <tr>
		        <td colspan="5">질문이 없습니다.</td>
		    </tr>
		<% } else {
		     int index = qnaList.size() - startRow;
		     for (StudentQnaListVO vo : pageList) { %>
		    <tr data-subject="<%= vo.getSubjectCode() %>">
		        <td><%= index-- %></td>
		        <td><a href="#" onclick="openQnaWindow(<%= vo.getQnaId() %>); return false;">
		            <%= vo.getQuestionerTitle() %></a></td>
		        <td><%= vo.getQuestionerName() %></td>
		        <td><%= vo.getQuestionTime().toLocalDateTime().toLocalDate() %></td>
		    </tr>
		<%   }
		   } %>
		</tbody>
    </table>
           	<div class="pagination" style="text-align:center; margin-top:20px;">
			<%
			    if (qnaList != null && !qnaList.isEmpty()) {
			        int totalPage = (int)Math.ceil((double)qnaList.size() / pageSize); 
			        int pageBlock = 5; // 한 번에 보여줄 페이지 수
			        int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
			        int endPage = startPage + pageBlock - 1;
			        if (endPage > totalPage) endPage = totalPage;
			
			        if (startPage > 1) {
			%>
			            <!-- 이전 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=startPage - 1%>&subjectCode=<%=subjectCode%>">&#9664;</a>
			<%
			        }
			        for (int i = startPage; i <= endPage; i++) {
			            if (i == pageNum) {
			%>
			                <strong><%=i%></strong>
			<%
			            } else {
			%>
			                <a href="?pageNum=<%=i%>"><%=i%></a>
			<%
			            }
			        }
			        if (endPage < totalPage) {
			%>
			            <!-- 다음 페이지로 이동하는 버튼 -->
			            <a href="?pageNum=<%=endPage + 1%>&subjectCode=<%=subjectCode%>">&#9654;</a>
			<%
			        }
			    }
			%>
		</div>
    <div style="text-align: right; margin-top: 10px;">
    	<button type="button" class="add-btn" onclick="openQnaForm()">글쓰기</button>
	</div>
	
	<!-- 글쓰기 폼 -->
	<div id="qnaWriteForm" style="display:none; padding: 20px; background-color: #ffffff; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-top: 20px;">
	    <h3>📝 질문 등록</h3>
	    <form action="<%= contextPath %>/student/insertStudentQ" method="post">
	    	<div style="margin-bottom: 10px;">
	            <label>과목 선택</label><br>
	            <select name="subject_code" required style="width: 100%; padding: 8px;">
	                <option value="">-- 과목 선택 --</option>
	                <% for (StudentSubjectVO vo : subjectList) { %>
	                    <option value="<%= vo.getSubjectCode() %>"><%= vo.getSubjectName() %></option>
	                <% } %>
	            </select>
        	</div>
	        
	        <div style="margin-bottom: 10px;">
	            <label>제목</label><br>
	            <input type="text" name="title" style="width: 100%; padding: 8px;" required>
	        </div>
	        <div style="margin-bottom: 10px;">
	            <label>내용</label><br>
	            <textarea name="content" rows="5" style="width: 100%; padding: 8px;" required></textarea>
	        </div>
	        <div style="text-align: right;">
	            <button type="submit" style="padding: 8px 16px; background-color: #3498db; color: white; border: none; border-radius: 6px;">등록</button>
	            <button type="button" onclick="closeNoticeForm()" style="padding: 8px 16px; margin-left: 8px; border: 1px solid #ccc; border-radius: 6px;">취소</button>
	        </div>
	    </form>
	</div>
</div>
</body>
</html>
