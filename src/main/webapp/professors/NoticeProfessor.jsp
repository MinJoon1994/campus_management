<%@page import="java.util.List"%>
<%@page import="professorvo.NoticeProfessorVo"%>
<%@page import="java.util.Vector"%>
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
		
		#table_notice {
		    width: 100%;
		    height: 500px;
		    border-collapse: collapse;
		    background-color: #fafafa;
		    border-radius: 8px;
		    overflow: hidden;
		}
		th {
		    background-color: #34495e;
		    color: white;
		}
		th, td {
		    text-align: center;
		    padding: 12px;
		    border-bottom: 1px solid #ddd;
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
		.pagination a {
            margin: 0 5px;
            text-decoration: none;
        }

        .pagination strong {
            margin: 0 5px;
            font-weight: bold;
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
		    const contextPath = "${contextPath}";
		    console.log("typeof noticeId:", typeof noticeId);
		    console.log("contextPath:", contextPath);
		    console.log("Received noticeId:", noticeId);
		
		    const url = contextPath + "/professor/noticedetail?noticeId=" + noticeId;
		    console.log("Generated URL:", url);
		
		    window.open(url, '공지사항 확인', 'width=800,height=600');
		}
	</script>
    
</head>
<body>

<div class="container">
    <h2 style="text-align: center; padding-bottom: 20px;">📢 교수 공지사항</h2>
    <%
         Vector<NoticeProfessorVo> noticeVo = (Vector<NoticeProfessorVo>) request.getAttribute("noticeVo");

         int pageSize = 10; // 한 페이지에 출력할 강의 수
         int pageNum = 1;   // 기본 페이지
         if (request.getParameter("pageNum") != null) {
             pageNum = Integer.parseInt(request.getParameter("pageNum"));
         }

         int startRow = (pageNum - 1) * pageSize;
         // 1페이지: (1 - 1) * 10 = 0 → 인덱스 0부터 시작
		 // 2페이지: (2 - 1) * 10 = 10 → 인덱스 10부터 시작
         int endRow = Math.min(startRow + pageSize, noticeVo != null ? noticeVo.size() : 0);
         List<NoticeProfessorVo> pageList = (noticeVo != null) ? noticeVo.subList(startRow, endRow) : new Vector<NoticeProfessorVo>();
         request.setAttribute("pageList", pageList);
     %>
    <c:choose>
        <c:when test="${not empty noticeVo}">
            <table id="table_notice">
                <thead>
                    <tr id="th_notice">
                        <th style="width: 10%;">선택</th>
                        <th style="width: 10%;">번호</th>
                        <th style="width: 60%;">제목</th>
                        <th style="width: 30%;">작성일</th>
                    </tr>
                </thead>
                <tbody id="tbody_notice">
                    <c:forEach var="notice" items="${requestScope.pageList}" varStatus="status">
                        <tr id="td_notice" onclick='openNoticeDetails("${notice.noticeId}")'>
                        	<td>
                        		<input type="checkbox" name="noticeIds" value="${notice.noticeId}" onclick="event.stopPropagation();"/>
                        	</td>
                            <td>${status.count}</td>
                            <td>${notice.title}</td>
                            <td>${notice.createdAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                   	<div class="pagination" style="text-align:center; margin-top:20px;">
			<%
			    // list가 null이 아니고 비어있지 않은 경우에만 페이지네이션 처리
			    if (noticeVo != null && !noticeVo.isEmpty()) {
			        // 전체 페이지 수 계산 (list.size()는 전체 데이터 수)
			        int totalPage = (int)Math.ceil((double)noticeVo.size() / pageSize); 
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