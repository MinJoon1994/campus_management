<%@page import="member.vo.UserVO"%>
<%@page import="qna.vo.QnaVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%-- JSTL 중에서 core 태그를 사용하기 위해 주소를 import --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 한글 인코딩 --%>
<% request.setCharacterEncoding("UTF-8"); %>

<% 


	QnaVO qnavo = null;

	// 현재 웹 애플리케이션(프로젝트)의 기본 경로를 가져옵니다.
	// 예를 들어, 프로젝트 주소가 "http://localhost:8090/Campus_management2/qna/listadmin"이라면 "/myapp"이 됩니다.
	String contextPath = request.getContextPath();

	// 컨트롤러에서 준비한 'communities'라는 게시글 목록 데이터를 받아옵니다.
	// 이 데이터는 ArrayList 안에 HashMap 형태로 여러 개의 게시글 정보와 작성자 정보를 담고 있습니다.
	// 쉽게 말해, 여러 게시글 정보를 리스트 형태로 받는다고 생각하면 됩니다.
	ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>)request.getAttribute("qnalist");

	//out.println("list : " + list.size());
	
	// 로그인한 사용자 Email를 세션에서 가져옵니다.
	// 로그인 후에 저장된 사용자 정보 중 'email'를 꺼내는 것이라 로그인 상태인지 확인할 때 사용합니다.
	String email = (String) session.getAttribute("qndID");

	// 받아온 게시글 전체 개수를 저장합니다.
	// 예를 들어, 총 53개의 글이 있다면 totalRecord는 53입니다.
	int totalRecord = list.size();
	
	
	// 한 페이지에 보여줄 게시글 개수를 정합니다.
	// 예를 들어 10으로 하면, 한 화면에 최대 10개 글을 보여줍니다.
	int numPerPage = 10;

	// 전체 페이지 수를 저장할 변수 초기화 (예: 6페이지)
	int totalPage = 0; 

	// 현재 보고 있는 페이지 번호 (예: 0페이지는 1페이지라고 생각)
	int nowPage = 0; 

	// 현재 페이지에서 보여줄 게시글의 시작 인덱스를 저장하는 변수 (예: 0, 10, 20 ...)
	// 글 리스트 중 몇 번째 글부터 보여줄지 계산할 때 씁니다.
	int beginPerPage = 0; 

	// 한 번에 보여줄 페이지 번호의 개수를 정합니다. (예: 5개씩 보여주는 블럭)
	// 페이지가 너무 많을 때 페이지 번호도 나눠서 보여주기 위해 씁니다.
	int pagePerBlock = 5; 

	// 전체 블럭 수 (예: 페이지가 12개면, 블럭은 3개(1~5, 6~10, 11~12))
	int totalBlock = 0; 

	// 현재 보고 있는 페이지 블럭 번호 (0부터 시작)
	int nowBlock = 0; 

	// 클라이언트가 'nowPage' (현재 페이지 번호)를 보내왔다면 받아서 저장
	// 예: 사용자가 3페이지를 눌렀으면 2가 들어옵니다 (0부터 시작해서)
	if (request.getAttribute("nowPage") != null) {
		// 받아온 문자열을 숫자(int)로 변환하여 nowPage에 저장
		nowPage = Integer.parseInt(request.getAttribute("nowPage").toString());
	}

	// 클라이언트가 'nowBlock' (현재 페이지 블럭 번호)를 보내왔다면 받아서 저장
	if (request.getAttribute("nowBlock") != null) {
		nowBlock = Integer.parseInt(request.getAttribute("nowBlock").toString());
	}

	// 현재 페이지에 보여줄 게시글의 시작 번호 계산
	// 예를 들어 3페이지(인덱스 2)면 2 * 10 = 20부터 글을 보여줌 (21번째 글부터)
	beginPerPage = nowPage * numPerPage;

	// 전체 페이지 수 계산
	// 게시글 수(totalRecord)를 한 페이지에 보여줄 글 수(numPerPage)로 나누고,
	// 소수점이 있으면 올림 처리해서 정확한 페이지 수를 구합니다.
	// 예: 53개 글 / 10개 = 5.3 → 6페이지가 필요함
	totalPage = (int) Math.ceil((double) totalRecord / numPerPage);

	// 전체 페이지를 기준으로, 페이지 번호 블럭 개수 계산
	// 한 블럭에 pagePerBlock 만큼 페이지 번호를 보여줄 때,
	// 전체 페이지(totalPage)를 페이지 번호 블럭 크기로 나누어 계산합니다.
	// 예: 6페이지 / 5개 = 1.2 → 2개의 블럭이 필요함 (1~5, 6)
	totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 문의 사항 게시판</title>

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
<style>
@charset "UTF-8";

@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap');

body {
  font-family: 'Noto Sans KR', sans-serif;
  background-color: #f4f6f9;
  color: #333;
}

a:link, a:visited, a:hover, a:active {
  color: inherit;
  text-decoration: none;
}

/* 전체 레이아웃 */
#qna-container {
  margin: 0 auto;
  padding-top: 40px;
}

/* 헤더 영역 */
#qna-header {
  text-align: center;
  color: #003366;
  margin-bottom: 30px;
}

.qna_p1 {
  font-size: 3rem;
  font-weight: bold;
  color: #003366;
}

.qna_p2 {
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 8px;
}

/* 테이블 설정 */
.table-list {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 8px;
  background-color: white;
}

.table-list tr {
  border-radius: 8px;
  overflow: hidden;
}

.table-list td {
  padding: 14px 12px;
  font-size: 15px;
  border-bottom: 1px solid #dee2e6;
}

.table-list tr:first-child {
  background-color: #003366;
  color: white;
  font-weight: 600;
}

/* 검색, 버튼 영역 */
.qna-table-bottom {
  position: relative;
  margin-top: 30px;
}

.qna-search-area input {
  border: 1px solid #ced4da;
  border-radius: 5px;
  padding: 8px 12px;
  font-size: 1rem;
  color: #495057;
  width: 250px;
  outline: none;
}

.select-button select {
  border: 1px solid #ced4da;
  border-radius: 5px;
  padding: 8px;
  font-size: 1rem;
  background-color: #f8f9fa;
  color: #333;
  cursor: pointer;
}

.qna-search-button input,
.qna-write-button input {
  background-color: #003366;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  font-size: 1rem;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.qna-search-button input:hover,
.qna-write-button input:hover {
  background-color: #00509e;
}

.qna-write-button {
  position: absolute;
  top: 0;
  right: 0;
}

/* 페이지네이션 */
.page_number > td {
  padding: 20px 0;
}

.page_number > td > a {
  padding: 8px 14px;
  margin: 0 3px;
  font-size: 15px;
  color: #003366;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.page_number > td > a:hover {
  background-color: #003366;
  color: white;
  border-color: #003366;
}
</style>


</head>
<body>
	<!-- 전체 커뮤니티 영역을 감싸는 div 시작 -->
	<div id="qna-container">

		<!-- 커뮤니티 상단 제목과 설명 영역 -->
		<div id="qna-header" align="center">
			<!-- 큰 제목 -->
			<p class="qna_p1">Questions And Answers</p>
			<!-- 중간 제목 -->
			<p class="qna_p2">관리자 문의 게시판</p>
			<!-- 부가 설명 -->
			<p>관리자에게 문의 할 글을 작성하세요</p>
		</div>
	</div>
	
	<!-- 커뮤니티 본문 영역 시작 -->
	<div id="qna-body">

		<!-- 게시글 목록을 보여줄 테이블 시작 -->
		<table class="table-list" width="100%">
			
			<!-- 테이블 헤더(컬럼 제목 줄) -->
			<tr align="center" bgcolor="#e9ecef"> <!-- 가운데 정렬, 배경색 회색 -->
				<td class="qna-id" width="15%">번호</td>        <!-- 게시글 번호 -->
				<td class="qna-title" width="30%">제목</td>        <!-- 게시글 제목 -->
				<td class="qna-writeName" width="15%">작성자</td>      <!-- 글 작성한 사람 -->
				<td class="qna-category" width="15%">분류</td>     <!-- 글 분류  사용자->관리자 -->
				<td class="qna-questionTime" width="20%">작성 날짜</td>      <!-- 글 작성 날짜일 -->
			</tr>
			
			<%
			// 게시글 데이터가 없을 경우를 체크
			// communities가 null이거나, 리스트 안에 아무 글도 없을 경우
			if (list == null || list.size() == 0){				
			%>
				<!-- 게시글이 하나도 없을 경우, 안내 메시지 출력 -->
				<tr align="center">
					<td colspan="5">등록된 글이 없습니다.</td>
				</tr>
			<%
			} else {
				// 게시글이 존재할 경우, 해당 목록을 화면에 출력
				// beginPerPage: 현재 페이지에서 보여줄 시작 글 번호 (예: 0, 10, 20 등)
				// numPerPage: 한 페이지에 보여줄 글 개수 (예: 10개씩)
				for (int i = beginPerPage; i < beginPerPage + numPerPage; i++) {
					
					// 전체 게시글 수보다 인덱스가 크거나 같아지면 반복 종료 (예외 방지)
					if (i == totalRecord) {
						break;
					}
					
					// 리스트에서 i번째 데이터 가져오기
					// Map 형태로 community와 member라는 객체가 저장되어 있음
					qnavo = (QnaVO)list.get(i).get("vo"); // 게시글 객체
					UserVO member = (UserVO)list.get(i).get("member");             // 작성자 정보 객체
			%>			
					<!-- 실제 게시글 한 줄(row) 출력 -->
					<tr align="center">
						<!-- 글번호: 최신글이 위로 가도록 역순으로 출력 -->
						<td><%= totalRecord - i %></td>

						<!-- 제목: 좌측 정렬, 클릭 시 게시글 상세보기로 이동 -->
						<td align="left">
							<a href="<%= contextPath %>/qna/detail?qnaID=<%= qnavo.getQnaID() %>">
								<%=qnavo.getTitle()%> <!-- 글 제목 -->
							</a>
						</td>

						<!-- 작성자 이름출력 -->
						<td><%=qnavo.getWriterName() %></td>

						<!-- 사용자→관리자 -->
						<td><%=qnavo.getCategory()%></td>

						<!-- 작성 날짜 출력 -->
						<td><%=qnavo.getQuestionTime()%></td>
					</tr>
			<%
				} // for문 끝
			} // if~else 끝
			%>

			<%--페이징 처리 시작 ------------------------------------------------------------------------------- --%>
			<tr class="page_number"> <!-- 페이지 번호를 보여주는 테이블 행(row)의 시작 -->
			
				<td colspan="5" align="center"> 
					<!-- 이 셀(td)은 테이블 열 5개를 하나로 합쳐서 페이지 번호를 중앙에 정렬해서 보여줍니다 -->
			
				<%
				// 게시글이 1개 이상 있을 경우에만 페이지 번호를 보여줄 필요가 있습니다.
				if (totalRecord > 0) {
			
					// 이전 블록으로 이동하는 '<' 버튼을 보여줄지 검사
					// nowBlock이 0보다 크다는 것은 지금 보고 있는 페이지 그룹(블록)이 2번째 이상이라는 뜻입니다.
					// 예: 페이지가 1~5, 6~10, 11~15 이렇게 나뉘면, 현재 블록이 6~10이라면 이전 블록인 1~5로 가는 '<' 버튼이 필요함
					if (nowBlock > 0) {
				%>
					<!-- '<' 버튼: 이전 블록으로 이동하는 링크입니다. -->
					<!-- 클릭하면 이전 블록의 첫 번째 페이지로 이동합니다. -->
					<a href="<%= contextPath %>/qna/list?nowBlock=<%= nowBlock - 1 %>&nowPage=<%= (nowBlock - 1) * pagePerBlock %>">
						< <!-- 브라우저에 '<' 기호로 보이게 됨 -->
					</a>
				<%
					}
			
					// 현재 블록에서 보여줄 페이지 번호들을 반복문으로 하나씩 출력
					// 예를 들어 현재 블록이 0이고 한 블록당 5페이지라면 1, 2, 3, 4, 5 페이지 링크 생성
					for (int i = 0; i < pagePerBlock; i++) {
			
						// 전체 페이지 수를 넘지 않도록 체크
						// 예: 전체 페이지가 12개인데, 현재 블록에서 13번째 페이지를 보여주면 안 됨
						if ((nowBlock * pagePerBlock) + i == totalPage) break;
				%>
						<!-- 각각의 페이지 번호에 해당하는 링크 생성 -->
						<a href="<%= contextPath %>/qna/list?nowBlock=<%= nowBlock %>&nowPage=<%= (nowBlock * pagePerBlock) + i %>">
							<%= (nowBlock * pagePerBlock) + i + 1 %>
							<!-- 실제 화면에 보이는 숫자: 페이지 번호는 0부터 시작하므로 +1 -->
						</a>
				<%
					}
			
					// 다음 블록으로 이동하는 '>' 버튼을 보여줄지 검사
					// 현재 블록 번호 + 1이 전체 블록 수보다 작다는 건 아직 보여줄 다음 블록이 있다는 뜻
					if (nowBlock + 1 < totalBlock) {
				%>
					<!-- '>' 버튼: 다음 블록으로 이동하는 링크입니다. -->
					<!-- 클릭 시 다음 블록의 첫 번째 페이지로 이동 -->
					<a href="<%= contextPath %>/qna/list?nowBlock=<%= nowBlock + 1 %>&nowPage=<%= (nowBlock + 1) * pagePerBlock %>">
						> <!-- 브라우저에 '>' 기호로 보이게 됨 -->
					</a>
				<%
					}
				} // 전체 게시글 수가 0 이하라면 아무 페이지 버튼도 보여주지 않음
				%>
			
				</td> <!-- 이 셀의 끝: 페이지 링크들을 다 이 안에서 보여주고 있음 -->
			
			</tr> <!-- 페이지 번호가 출력되는 행의 끝 -->

			<%--페이징 처리  끝------------------------------------------------------------------------------- --%>

			<tr>
				<td colspan="5" align="center">
					<div class="qna-table-bottom">
						<form action="<%=contextPath%>/qna/searchList" method="post" 
							name="frmSearch" onsubmit="fnSearch(); return false;">
							<span class="select-button">
								<select name="key">
									<option value="titleContent">제목+내용</option>								
									<option value="writerContent">작성자</option>								
								</select>
							</span>
							<span class="qna-search-area">
								<input type="text" name="word" id="word" placeholder="검색어를 입력해주세요">
							</span>
							<span class="qna-search-button">
								<input type="submit" value="검색"/>
							</span>
						</form>
						<%
						if(email != null && email.length()!= 0){
							%>
							<div class="qna-write-button">
								<input type="button" value="글쓰기" onclick="onWriteButton(event)">
							</div>	
							<%
						}
						%>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	
	<script>
		function fnSearch(){
			var word = document.getElementById("word").value;
			
			if(word == null || word == ""){
				alert("검색어를 입력하세요.");
				document.getElementById("word").focus();
				
				return false;
			}
			else{
				
				document.frmSearch.submit();
			}
		}
	
		function onWriteButton(event) {
			event.preventDefault();
			
			location.href='<%=contextPath%>/qna/write';
		}
		
		function frmSearch(){
			var word = document.getElementById("word").value;
			
			if(word == null || word == ""){
				alert("검색어를 입력해주세요");
				
				document.getElementById("word").focus();
				
				return false;
			}
		}
	</script>
</body>
</html>