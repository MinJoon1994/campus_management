<%@page import="java.text.SimpleDateFormat"%>
<%@page import="member.vo.UserVO"%>
<%@page import="qna.vo.QnaVO"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>

<% 
	String contextPath = request.getContextPath();

	String qnaID = request.getParameter("qnaID");

    // 컨트롤러에서 준비한 'qnadetail'라는 게시글 상세 정보를 받아옵니다.
	HashMap<String, Object> detail = (HashMap<String, Object>)request.getAttribute("qnadetail");
	
	QnaVO qnaVO = null;
	if (detail != null) {
    
		qnaVO = (QnaVO)detail.get("vo");
		UserVO memberVO = (UserVO)detail.get("member");
	}
	qnaID = (String) session.getAttribute("qnaID");
%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>관리자에게 문의하는 내용 읽기</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<link rel="stylesheet" href="<%=contextPath%>/css/qna/qnadetail.css">
</head>

<body>
	<div id="qna-container">
		<div id="qna-header" align="center">
			<p class="qna_p1">Questions And Answers</p>
			<p class="qna_p2">관리자 문의 게시판</p>
			<p>관리자에게 문의할 글을 작성하세요</p>
		</div>
		
		<div id="qna-body">
			<table width="100%">
				<tr>
					<td colspan="4">
						<div class="qna_no">
							<%= qnaVO.getQnaID()%>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="qna_title">
							<%= qnaVO.getTitle() %>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="qna_wtitername">
							<%= qnaVO.getWriterName() %>
						</div>
					</td>
				</tr>
								<tr>
					<td colspan="4">
						<div class="qna_contents">
							<%= qnaVO.getQuestion() %>
						</div>
					</td>
				</tr>
				
				<tr>
					<td>
						<div class="qna_date">
							<span><%=qnaVO.getQuestionTime()%></span>
<%-- 							<span>&nbsp;조회 <%= communityVO.getViews()%></span> --%>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="qna_contents">
							<%= qnaVO.getCategory() %>
						</div>
					</td>
				</tr>
					<tr>
					    <td colspan="4" align="right">
					        <div class="qna-button-area">
					            <input type="button" value="목록" onclick="onListButton()">
					            <% if (qnaID != null && qnaVO != null && qnaID.equals(qnaVO.getQnaID())) { %>
					                <input type="button" value="수정" onclick="onUpdateButton()">
					                <input type="button" value="삭제" onclick="onDeleteButton()">
					            <% } %>
					        </div>
					    </td>
					</tr>
			</table>
		</div>
	</div>
	
	<form name="frmUpdate" method="post">
		<input type="hidden" name="qnaID">
		<input type="hidden" name="title">
		<input type="hidden" name="contents">
	</form>
	
	<script>
		function onListButton() {
			location.href = '<%= contextPath %>/qna/list';
		}
	
		function onUpdateButton() {
		    document.frmUpdate.action = "<%= contextPath %>/qna/update";

		    document.frmUpdate.qnaID.value = "<%= qnaVO.getQnaID() %>";
		    document.frmUpdate.title.value = "<%= qnaVO.getTitle() %>";
		    document.frmUpdate.contents.value = "<%= qnaVO.getQuestion() %>";

		    document.frmUpdate.submit();
		}
		
		function onDeleteButton() {
			$.ajax({
				url: "<%= contextPath%>/qna/deletePro",
				type: "post",
				data : {
					no: <%= qnaVO.getQnaID()%>
				},
				dataType: "text",
				success: function(responsedData){
					
					if(responsedData == "삭제 완료"){
						location.href ='<%= contextPath %>/qna/list';
					}
					else {
						alert('삭제되지 않았습니다.');
					}
				},
				error: function(error){
					console.log(error);
				}				
			});
		}
	</script>
</body>

</html>
