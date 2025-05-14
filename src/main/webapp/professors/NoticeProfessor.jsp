<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>교수 공지사항 목록</title>
    <style>
		body {
		    margin: 0;
		    padding: 0;
		    font-family: Arial, sans-serif;
		    background-color: #f0f2f5; 
		}
		
		.container {
		    width: 90%;
		    margin: 30px auto;
		    border-radius: 12px;
		}
		
		h2 {
			margin-top: 40px;
		    background-color: #2c3e50;
		    color: white;
		    padding: 16px;
		    text-align: center;
		    border-radius: 8px;
		    margin-top: 0;
		    margin-bottom: 30px;
		}
		
		table {
		    width: 100%;
		    height: 600px;
		    border-collapse: collapse;
		    background-color: #fafafa;
		    border-radius: 8px;
		    overflow: hidden;
		}
		
		th, td {
		    text-align: center;
		    padding: 12px;
		    border-bottom: 1px solid #ddd;
		}
		
		th {
		    background-color: #34495e;
		    color: white;
		}
		
		tr:hover {
		    background-color: #f1f1f1;
		}
		
		.no-data {
		    height: 600px;
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    color: #888;
		    background-color: #f9f9f9;
		    border-radius: 8px;
		}
		
		.button-area {
		    text-align: right;
		    margin-top: 20px;
		}
		
		.append-btn {
		    background-color: #3498db;
		    color: white;
		    border: none;
		    padding: 10px 20px;
		    border-radius: 6px;
		    cursor: pointer;
		    font-size: 14px;
		}
		
		.append-btn:hover {
		    background-color: #2980b9;
		}
    </style>
    <script>
	    function openNoticeForm() {
	        document.getElementById("noticeWriteForm").style.display = "block";
	        window.scrollTo({
	            top: document.getElementById("noticeWriteForm").offsetTop - 50,
	            behavior: 'smooth'
	        });
	    }
	
	    function closeNoticeForm() {
	        document.getElementById("noticeWriteForm").style.display = "none";
	    }
	    // 선택한 공지사항 게시글 삭제
		function deleteSelectedNotice() {
		    const checkedBoxes = document.querySelectorAll('input[name="noticeIds"]:checked');
		    const noticeIds = Array.from(checkedBoxes).map(cb => cb.value);

		    if (noticeIds.length === 0) {
		        alert("삭제할 질문을 선택하세요.");
		        return;
		    }
		    
		    const params = new URLSearchParams();
		    noticeIds.forEach(id => params.append("noticeIds", id));
		    
		    fetch('${contextPath}/professor/deletenotice.do', {
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
		        location.reload(); // 현재 페이지 다시 로드하여 리스트 갱신
		    })
		    .catch(error => {
		        alert("삭제 중 오류 발생: " + error.message);
		    });
		}
		// 행 클릭 시 해당 공지사항 상세 보기
		function openNoticeDetails(noticeId) {
			console.log("contextPath:", contextPath);
		    console.log("Received noticeId:", noticeId);  // Received noticeId: 9
		    const url = `${contextPath}/professor/noticedetail?noticeId=${noticeId}`;
		    console.log("Generated URL:", url);  // Generated URL: /campus_management/professor/noticedetail?noticeId=
		    window.open(url, '공지사항 확인', 'width=800,height=600');
		}
	</script>
    
</head>
<body>

<div class="container">
    <h2 style="text-align: center; padding-bottom: 20px;">📢 교수 공지사항</h2>

    <c:choose>
        <c:when test="${not empty noticeVo}">
            <table>
                <thead>
                    <tr>
                        <th style="width: 10%;">선택</th>
                        <th style="width: 10%;">번호</th>
                        <th style="width: 60%;">제목</th>
                        <th style="width: 30%;">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="notice" items="${noticeVo}" varStatus="status">
                        <tr onclick="openNoticeDetails(${notice.noticeId})">
                        	<td>
                        		<input type="checkbox" name="noticeIds" value="${notice.noticeId}"/>
                        	</td>
                            <td>${status.count}</td>
                            <td>${notice.title}</td>
                            <td>${notice.createdAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">등록된 공지사항이 없습니다.</div>
        </c:otherwise>
    </c:choose>
    <div style="text-align: right; margin-top: 10px;">
   		<button type="button" class="append-btn" onclick="openNoticeForm()">글쓰기</button>
   		<button type="button" class="append-btn" onclick="deleteSelectedNotice()">삭제하기</button>
	</div>
	
	<!-- 글쓰기 폼 -->
	<div id="noticeWriteForm" style="display:none; padding: 20px; background-color: #ffffff; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin-top: 20px;">
	    <h3>📝 공지사항 등록</h3>
	    <form action="${contextPath}/professor/noticeinsert.do" method="post" enctype="multipart/form-data">
	        <div style="margin-bottom: 10px;">
	            <label>제목</label><br>
	            <input type="text" name="title" style="width: 100%; padding: 8px;" required>
	        </div>
	        <div style="margin-bottom: 10px;">
	            <label>내용</label><br>
	            <textarea name="content" rows="5" style="width: 100%; padding: 8px;" required></textarea>
	        </div>
	        <div style="margin-bottom: 10px;">
	            <label>첨부파일</label><br>
	            <input type="file" name="uploadFile" style="padding: 5px;">
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