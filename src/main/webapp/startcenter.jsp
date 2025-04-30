<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title>학사관리시스템</title>
	<!-- 부트스트랩 5.3.3 버전의 스타일시트 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- FullCalendar 라이브러리의 스타일 시트 -->
	<link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
	<style>
		.page_btn{
		    background-color: rgb(44, 62, 80);
            color: white;
            border: none;
            padding: 5px 10px;
            margin: 0 2px;
            cursor: pointer;
		}
		
		.notice_title{	
		    color: rgb(44, 62, 80);
            text-decoration: none;
            font-weight: bold;
        }
	</style>
	
	<!-- jQuery 라이브러리 -->
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body style="background-image: url('../images/background4.png'); background-size: cover; background-repeat: no-repeat; background-position: center;">
	<div class="container mt-4">
		<!-- 슬라이드 영역 -->
		<div class="row mb-4">
			<div class="col-md-12">
				<div class="p-5 bg-light rounded-3">
					<div id="imageCarousel" class="carousel slide" data-bs-ride="carousel" style="max-height: 300px; overflow: hidden;">
						<div class="carousel-inner">
							<div class="carousel-item active">
								<div class="d-block w-100" style="height: 300px; background-image: url('../images/background1.png'); background-size: cover; background-position: center; background-repeat: no-repeat;"></div>
							</div>
							<div class="carousel-item">
								<div class="d-block w-100" style="height: 300px; background-image: url('../images/background2.png'); background-size: cover; background-position: center; background-repeat: no-repeat;"></div>
							</div>
							<div class="carousel-item">
								<div class="d-block w-100" style="height: 300px; background-image: url('../images/background3.png'); background-size: cover; background-position: center; background-repeat: no-repeat;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 학사일정 + 로그인/공지사항 영역 -->
		<div class="row mt-4 mb-4 ms-1" >
			<div class="col-md-7 p-4 bg-light border rounded">
				<!-- 달력 코드 영역 -->
				<div id="calendar"></div>
			</div>
			<div class="col-md-5">
				<!-- 공지사항 박스 -->
				<div class="notice-box p-3 bg-light border rounded">
					<h4><a class="notice_title" href="${contextPath}/notice/list">공지사항</a></h4>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody id="noticeTableBody"></tbody>
					</table>
					    <!-- 여기에 페이징 버튼 들어감 -->
					    <div id="noticePaging" class="mt-3 text-center">
					        <!-- 페이징 버튼을 동적으로 만듦 -->
					    </div>
				</div>
				<!-- QNA 박스 -->
				<div class="qna-box p-3 bg-light border rounded mt-2">
					<h4><a class="notice_title" href="${contextPath}/qna/list">질문게시판</a></h4>
					
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody id="qnaTableBody"></tbody>
					</table>
					
				    <div id="qnaPaging" class="mt-3 text-center">
				        <!-- 페이징 버튼을 동적으로 만듦 -->
				    </div>
				</div>
				
			</div>
		</div>
	</div>

	<!-- FullCalendar의 JavaScript 파일을 불러온다. -->
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
	<!-- 부트스트랩의 JavaScript 기능을 불러온다. -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- 달력 스크립트 -->
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			
			//공지사항 첫페이지 불러오기
			loadNoticePage(1);
			
			//질문게시판 첫페이지 불러오기
			loadQnaPage(1);
			
			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				initialView: 'dayGridMonth',
				locale: 'ko', // 한국어 설정
				 // ✅ 여기 추가
		        events: function(info, successCallback, failureCallback) {
		            fetch('${pageContext.request.contextPath}/campus/calendarEvent')
		                .then(response => response.json())
		                .then(data => {
		                    successCallback(data);
		                })
		                .catch(error => {
		                    console.error('Error fetching calendar events:', error);
		                    failureCallback(error);
		                });
		        },
		        // (선택) 일정 클릭했을 때 팝업
		        eventClick: function(info) {
		            alert('제목: ' + info.event.title + '\n내용: ' + (info.event.extendedProps.description || ''));
		        }
			});
			calendar.render();
		});
		
		
		function loadNoticePage(page){
			$.ajax({
		        url: "${pageContext.request.contextPath}/campus/noticePage",
		        type: "GET",
		        data: { noticepage: page },
		        success: function(response) {
		            var result = JSON.parse(response);

		            // 1. 공지사항 리스트
		            var list = result.noticeList;
		            var html = "";
		            list.forEach(function(notice, index) {
		                html += "<tr>";
		                html += "<td>" + notice.noticeId + "</td>";
		                html += "<td><a class='notice_title' href='#'>" + notice.title + "</a></td>";
		                html += "<td>관리자</td>"; // 작성자
		                html += "<td>" + notice.createdAt + "</td>";
		                html += "</tr>";
		            });
		            
		            $("#noticeTableBody").html(html);

		            // 2. 페이징 버튼
		            var pageHtml = "";
		            var currentPage = result.currentPage;
		            var totalPage = result.totalPage;

		            var startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
		            var endPage = startPage + 4;
		            if (endPage > totalPage) endPage = totalPage;

		            // 이전 버튼
		            if (startPage > 1) {
		                pageHtml += '<button class="page_btn" onclick="loadNoticePage(' + (startPage - 1) + ')">◀</button>';
		            }

		            // 페이지 번호 버튼
		            for (var i = startPage; i <= endPage; i++) {
		                if (i == currentPage) {
		                    pageHtml += '<button class="page_btn" disabled style="font-weight:bold;">' + i + '</button>';
		                } else {
		                    pageHtml += '<button class="page_btn" onclick="loadNoticePage(' + i + ')">' + i + '</button>';
		                }
		            }

		            // 다음 버튼
		            if (endPage < totalPage) {
		                pageHtml += '<button class="page_btn" onclick="loadNoticePage(' + (endPage + 1) + ')">▶</button>';
		            }

		            $("#noticePaging").html(pageHtml);
		        },
		        error: function(xhr, status, error) {
		            console.error("공지사항 로딩 실패:", error);
		        }
		    });
		}
		
		
		function loadQnaPage(page){
			$.ajax({
                url: "${pageContext.request.contextPath}/campus/qnaPage",
                type: "GET",
                data: { qnapage: page },
                success: function(response) {
                    var result = JSON.parse(response);

                    // 1. QNA 리스트
                    var list = result.qnaList;
                    var html = "";
                    list.forEach(function(qna, index) {
                        html += "<tr>";
                        html += "<td>" + qna.qnaId + "</td>";
                        html += "<td><a class='notice_title' href='#'>" + qna.title + "</a></td>";
                        html += "<td>" + qna.questioner+ "</td>"; // 작성자
                        html += "<td>" + qna.questiontime + "</td>";
                        html += "</tr>";
                    });
                    
                    $("#qnaTableBody").html(html);

                    // 2. 페이징 버튼
                    var pageHtml = "";
                    var currentPage = result.currentPage;
                    var totalPage = result.totalPage;

                    var startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
                    var endPage = startPage + 4;
                    if (endPage > totalPage) endPage = totalPage;

                    // 이전 버튼
                    if (startPage > 1) {
                        pageHtml += '<button class="page_btn" onclick="loadQnaPage(' + (startPage - 1) + ')">◀</button>';
                    }

                    // 페이지 번호 버튼
                    for (var i = startPage; i <= endPage; i++) {
                        if (i == currentPage) {
                            pageHtml += '<button class="page_btn" disabled style="font-weight:bold;">' + i + '</button>';
                        } else {
                            pageHtml += '<button class="page_btn" onclick="loadQnaPage(' + i + ')">' + i + '</button>';
                        }
                    }

                    // 다음 버튼
                    if (endPage < totalPage) {
                        pageHtml += '<button class="page_btn" onclick="loadQnaPage(' + (endPage + 1) + ')">▶</button>';
                    }

                    $("#qnaPaging").html(pageHtml);
                },
                error: function(xhr, status, error) {
                    console.error("QNA 로딩 실패:", error);
                }
			});
		}
		
	</script>
</body>
</html>
