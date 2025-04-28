<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>학사관리시스템</title>
	<!-- 부트스트랩 5.3.3 버전의 스타일시트 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- FullCalendar 라이브러리의 스타일 시트 -->
	<link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
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
					<h4>공지사항</h4>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>공지사항 테스트</td>
								<td>관리자</td>
								<td>2025-04-22</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- QNA 박스 -->
				<div class="qna-box p-3 bg-light border rounded mt-5">
					<h4>질문게시판</h4>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>공지사항 테스트</td>
								<td>관리자</td>
								<td>2025-04-22</td>
							</tr>
						</tbody>
					</table>
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
		
		
	</script>
</body>
</html>
