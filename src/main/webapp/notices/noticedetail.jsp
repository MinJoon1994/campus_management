<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>게시글 상세보기</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<style>
		.container { padding-bottom: 80px; }
		#tr_btn_modify { display: none; }
		img#preview { max-width: 100%; height: auto; display: block; }
		textarea.form-control { min-height: 150px; height: auto; }
		.button-group-wrapper { margin-top: 1.5rem; }
	</style>
	<%-- *** JavaScript 수정: console.log 추가 *** --%>
	<script type="text/javascript">
        function backToList(form) {
            console.log("backToList called"); // 로그 추가
            form.action = "${contextPath}/notice/list";
            form.method = "get";
            form.enctype = "";
            const tempForm = document.createElement('form');
            tempForm.method = 'get';
            tempForm.action = "${contextPath}/notice/list";
            document.body.appendChild(tempForm);
            tempForm.submit();
            document.body.removeChild(tempForm);
        }

		function fn_enable(obj) {
            console.log("fn_enable called"); // 로그 추가
			const form = obj.form;
			if (!form) {
                console.error("fn_enable: Form not found!"); // 오류 로그
                return;
            }
            const titleEl = document.getElementById("i_title");
            const contentEl = document.getElementById("i_content");
            //const imgEl = document.getElementById("i_imageFileName");
            const modifyBtnGroup = document.getElementById("tr_btn_modify");
            const defaultBtnGroup = document.getElementById("tr_btn");

            // 요소 찾기 확인 로그
            console.log("Elements found:", { titleEl, contentEl, imgEl, modifyBtnGroup, defaultBtnGroup });

			if(titleEl) titleEl.disabled = false; else console.warn("Element with id 'i_title' not found.");
			if(contentEl) contentEl.disabled = false; else console.warn("Element with id 'i_content' not found.");
			if(imgEl) imgEl.disabled = false; // 이미지 필드는 없을 수도 있음

            // 버튼 그룹 상태 변경 및 로그
			if(modifyBtnGroup) {
                modifyBtnGroup.style.display = "flex";
                console.log("Displayed #tr_btn_modify");
            } else {
                console.error("Element with id 'tr_btn_modify' not found!");
            }
			if(defaultBtnGroup) {
                defaultBtnGroup.style.display = "none";
                console.log("Hid #tr_btn");
            } else {
                console.error("Element with id 'tr_btn' not found!");
            }
            console.log("fn_enable finished");
		}

		function readURL(input) {
            console.log("readURL called"); // 로그 추가
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview').attr('src', e.target.result).show();
                }
                reader.readAsDataURL(input.files[0]);
            } else {
                $('#preview').hide();
            }
       }

		function fn_modify_article(button) {
            console.log("fn_modify_article called"); // 로그 추가
            const form = button.form;
            if (!form) return;
            const url = "${contextPath}/board/modArticle.do";
            const formData = new FormData(form);

            fetch(url, { method: "POST", body: formData })
            .then(res => {
                console.log("Modify Response Status:", res.status); // 응답 상태 로그
                if (!res.ok) { throw new Error('HTTP error! status: ' + res.status); }
                return res.json(); // JSON 파싱 시도
            })
            .then(data => {
                console.log("Modify Response Data:", data); // 응답 데이터 로그
                if (typeof data === 'object' && data !== null && data.result === 'success' && data.articleNO != null) {
                    alert("수정이 완료되었습니다.");
                    ["i_title", "i_content", "i_imageFileName"].forEach(id => { const element = document.getElementById(id); if (element) element.disabled = true; });
                    document.getElementById("tr_btn_modify").style.display = "none";
                    document.getElementById("tr_btn").style.display = "flex";
                    console.log("Modify success UI updated");
                } else {
                    console.error("수정 응답 오류 또는 실패:", data);
                    alert("수정 중 문제가 발생했습니다.");
                }
            })
            .catch(err => {
                console.error("fn_modify_article fetch 오류:", err); // 오류 상세 로그
                alert("수정 요청 중 오류가 발생했습니다: " + err.message);
            });
        }

		function fn_remove_article(url, noticeID) {
            console.log("fn_remove_article called with URL:", url, "and noticeID:", noticeID); // 로그 추가
            if (!confirm("정말 삭제하시겠어요? 관련 답글도 모두 삭제됩니다.")) return;

            fetch(url, { method: "POST", headers: { "Content-Type": "application/x-www-form-urlencoded" }, body: new URLSearchParams({ articleNO: articleNO }) })
            .then(res => {
                console.log("Remove Response Status:", res.status); // 응답 상태 로그
                if (!res.ok) { throw new Error('HTTP error! status: ' + res.status); }
                 return res.json(); // JSON 파싱 시도
            })
            .then(data => {
                 console.log("Remove Response Data:", data); // 응답 데이터 로그
                if (typeof data === 'object' && data !== null && data.result === 'success') {
                    alert(data.message || "글을 삭제했습니다.");
                    window.location.href = data.redirect || "${contextPath}/board/listArticles.do";
                } else {
                    console.error("삭제 응답 오류 또는 실패:", data);
                    alert("삭제 중 문제가 발생했습니다.");
                }
            })
            .catch(err => {
                console.error("fn_remove_article fetch 오류:", err); // 오류 상세 로그
                alert("삭제 요청 중 오류가 발생했습니다: " + err.message);
            });
        }

		function fn_reply_form(url, noticeID) {
            console.log("fn_reply_form called with URL:", url, "and noticeID:", noticeID); // 로그 추가
            const form = document.createElement("form");
            form.method = "get";
            form.action = url;
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "noticeID";
            input.value = noticeID;
            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);
       }
	</script>
</head>
<body>
<div class="container my-5">
	<h2 class="mb-4 text-center">게시글 보기</h2>
	<form name="frmNotice" method="post" action="${contextPath}" enctype="multipart/form-data">
		<%-- 폼 내용 --%>
        <div class="card">
			<div class="card-body">
				<div class="mb-3 row"> <label class="col-sm-2 col-form-label text-sm-end">번호</label> <div class="col-sm-10"> <input type="text" readonly class="form-control-plaintext" value="${noticevo.noticeID}"> <input type="hidden" name="noticeID" value="${noticevo.noticeID}"> </div> </div>
				<div class="mb-3 row"> <label class="col-sm-2 col-form-label text-sm-end">작성자</label> <div class="col-sm-10"> <input type="text" readonly class="form-control-plaintext" value="${noticevo.adminName}" name="writer"> </div> </div>
				<div class="mb-3 row"> <label for="i_title" class="col-sm-2 col-form-label text-sm-end">제목</label> <div class="col-sm-10"> <input type="text" class="form-control" value="${noticevo.title}" name="title" id="i_title" disabled> </div> </div>
				<div class="mb-3 row"> <label for="i_content" class="col-sm-2 col-form-label text-sm-end">내용</label> <div class="col-sm-10"> <textarea rows="10" class="form-control" name="content" id="i_content" disabled>${noticevo.content}</textarea> </div> </div>
<%-- 				<c:if test="${not empty article.imageFileName && article.imageFileName != 'null'}"> <div class="mb-3 row"> <label class="col-sm-2 col-form-label text-sm-end">이미지</label> <div class="col-sm-10"> <input type="hidden" name="origianlFileName" value="${article.imageFileName}"> <div class="mb-2"> <img src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview" alt="첨부 이미지"> </div> <input type="file" class="form-control" name="imageFileName" id="i_imageFileName" disabled onchange="readURL(this);"> </div> </div> </c:if> --%>
<%--                 <c:if test="${empty article.imageFileName || article.imageFileName == 'null'}"> <div class="mb-3 row"> <label for="i_imageFileName" class="col-sm-2 col-form-label text-sm-end">이미지 첨부</label> <div class="col-sm-10"> <input type="hidden" name="origianlFileName" value=""> <img src="#" id="preview" alt="이미지 미리보기" style="display: none; max-width: 100%; height: auto; margin-bottom: 10px;" /> <input type="file" class="form-control" name="imageFileName" id="i_imageFileName" disabled onchange="readURL(this);"> </div> </div> </c:if> --%>
				<div class="mb-3 row"> <label class="col-sm-2 col-form-label text-sm-end">등록일자</label> <div class="col-sm-10"> <input type="text" readonly class="form-control-plaintext" value="<fmt:formatDate value='${noticevo.createdAt}' pattern='yyyy-MM-dd HH:mm:ss'/>"> </div> </div>
			</div>
		</div>
		<%-- 버튼 그룹 --%>
        <div class="button-group-wrapper">
            <%-- 수정 반영/취소 버튼 그룹 --%>
            <div class="d-flex flex-column flex-sm-row justify-content-center gap-2" id="tr_btn_modify" style="display: none;">
                <button type="button" class="btn btn-success" onclick="fn_modify_article(this)">수정반영하기</button>
                <button type="button" class="btn btn-secondary" onclick="location.reload()">취소</button>
            </div>
            <%-- 기본 버튼 그룹 --%>
            <div class="d-flex flex-column flex-sm-row justify-content-center gap-2" id="tr_btn" style="display: flex;">
                <button type="button" class="btn btn-primary" onclick="fn_enable(this)">수정하기</button>
                <button type="button" class="btn btn-danger" onclick="fn_remove_article('${contextPath}/notice/delete', ${noticevo.noticeID})">삭제하기</button>
                <button type="button" class="btn btn-warning" onclick="backToList(this.form)">목록으로</button>
                <button type="button" class="btn btn-info" onclick="fn_reply_form('${contextPath}/notice/reply', ${noticevo.noticeID})">답글쓰기</button>
            </div>
		</div>
	</form>

	<%-- Include the chatbot module --%>
<%-- 	<jsp:include page="./chatbot.jsp" /> --%>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>