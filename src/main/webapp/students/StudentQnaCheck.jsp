<%@page import="student.vo.StudentQnaWithRelpyVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	StudentQnaWithRelpyVO qrv = (StudentQnaWithRelpyVO) request.getAttribute("qnavo");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q&A 상세</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .question-box, .answer-box {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 30px;
        }
        .question-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .question-meta {
            font-size: 12px;
            color: gray;
            margin-bottom: 15px;
        }
        .question-content, .answer-content {
            white-space: pre-wrap;
            font-size: 14px;
        }
        .answer-box {
            background-color: #f9f9f9;
        }
        textarea {
            width: 100%;
            height: 150px;
            font-size: 14px;
        }
        .btn-area {
            margin-top: 10px;
            text-align: right;
        }
        .btn-area button {
            padding: 8px 16px;
            margin-left: 10px;
        }
    </style>
    
	<script>
		function submitReply(actionUrl) {
		    const qnaId = document.querySelector('input[name="qnaId"]').value;
		    const replyContent = document.querySelector('textarea[name="replyContent"]').value;
		
		    fetch(actionUrl, {
		        method: 'POST',
		        headers: { 'Content-Type': 'application/json' },
		        body: JSON.stringify({ qnaId: parseInt(qnaId), replyContent: replyContent })
		    })
		    .then(res => res.json())
		    .then(data => {
		        if (data.success) {
		            alert(data.message);
		            location.reload(); // 새로고침
		        } else {
		            alert("오류 발생: " + data.message);
		        }
		    })
		    .catch(err => {
		        alert("요청 실패: " + err);
		    });
		}
		
		function deleteReply(qnaId) {
		    if (!confirm("정말 삭제하시겠습니까?")) return;
		
		    fetch("<%=contextPath%>/professor/qnaprofessor/delete.do", {
		        method: 'POST',
		        headers: { 'Content-Type': 'application/json' },
		        body: JSON.stringify({ qnaId: parseInt(qnaId) })
		    })
		    .then(res => res.json())
		    .then(data => {
		        if (data.success) {
		            alert(data.message);
		            location.reload();
		        } else {
		            alert("삭제 실패: " + data.message);
		        }
		    });
		}
	</script>
</head>
<body>

<!-- 질문 영역 -->
<div class="question-box">
    <div class="question-title"><%= qrv.getQuestionerTitle() %></div>
    <div class="question-meta">질문일시: <%= qrv.getQuestionTime() %></div>
    <div class="question-content"><%= qrv.getQuestion() %></div>
</div>

<!-- 답변 영역 -->
<div class="answer-box">
    <form action="<%= contextPath %>/professor/qnareply.do" method="post">
        <input type="hidden" name="qnaId" value="<%= qrv.getQnaId() %>" />
        <div class="question-meta">
            <% if (qrv.getReplyTime() != null) { %>
                답변일시: <%= qrv.getReplyTime() %>
            <% } else { %>
                답변이 등록되지 않았습니다..
            <% } %>
        </div>
            <% if (qrv.getReplyTime() != null) { %>
            <textarea name="replyContent"><%= qrv.getReplyContent()%></textarea>
    		<%}%>
    </form>
</div>

</body>
</html>
