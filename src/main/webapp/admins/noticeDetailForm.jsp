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
	body {
		background-color: #f0f4f9;
		font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		color: #333;
	}

	.container {
		background-color: #fff;
		border-radius: 10px;
		padding: 40px 30px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		max-width: 900px;
		margin-top: 60px;
	}

	h2 {
		text-align: center;
		color: #003366;
		font-weight: bold;
		border-bottom: 2px solid #003366;
		padding-bottom: 10px;
		margin-bottom: 30px;
	}

	label.col-form-label {
		font-weight: 600;
		color: #003366;
	}

	.form-control,
	.form-control-plaintext,
	textarea.form-control {
		border: 1px solid #ced4da;
		border-radius: 6px;
	}

	textarea.form-control {
		min-height: 200px;
		resize: vertical;
	}

	.card {
		border: none;
	}

	.card-body {
		padding: 20px 10px;
	}

	/* 버튼 그룹 스타일 */
	.button-group-wrapper {
		margin-top: 30px;
		text-align: center;
	}

	.button-group-wrapper .btn {
		min-width: 130px;
		padding: 10px 20px;
		font-size: 15px;
	}

	.btn-primary {
		background-color: #003366;
		border: none;
	}

	.btn-primary:hover {
		background-color: #00509e;
	}

	.btn-danger {
		background-color: #cc0000;
		border: none;
	}

	.btn-danger:hover {
		background-color: #a50000;
	}

	.btn-warning {
		background-color: #f0ad4e;
		border: none;
		color: #fff;
	}

	.btn-warning:hover {
		background-color: #ec971f;
	}

	.btn-success {
		background-color: #28a745;
		border: none;
	}

	.btn-success:hover {
		background-color: #218838;
	}

	.btn-info {
		background-color: #17a2b8;
		border: none;
		color: #fff;
	}

	.btn-info:hover {
		background-color: #138496;
	}
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

	</script>
</head>
<body>
<div class="container my-5">
	<h2 class="mb-4 text-center">게시글 보기</h2>
	<form name="frmNotice" method="post" action="${contextPath}/admin/updateNotice">
    <div class="card">
        <div class="card-body">
            <div class="mb-3 row">
                <label class="col-sm-2 col-form-label text-sm-end">번호</label>
                <div class="col-sm-10">
                    <input type="text" name="noticeID" class="form-control" value="${noticevo.noticeID}" readonly>
                </div>
            </div>
            <div class="mb-3 row">
                <label class="col-sm-2 col-form-label text-sm-end">작성자</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="adminName" value="${noticevo.adminName}" readonly>
                </div>
            </div>
            <div class="mb-3 row">
                <label for="i_title" class="col-sm-2 col-form-label text-sm-end">제목</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="title" id="i_title" value="${noticevo.title}">
                </div>
            </div>
            <div class="mb-3 row">
                <label for="i_content" class="col-sm-2 col-form-label text-sm-end">내용</label>
                <div class="col-sm-10">
                    <textarea rows="10" class="form-control" name="content" id="i_content">${noticevo.content}</textarea>
                </div>
            </div>
            <div class="mb-3 row">
                <label class="col-sm-2 col-form-label text-sm-end">등록일자</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" readonly
                        value="<fmt:formatDate value='${noticevo.createdAt}' pattern='yyyy-MM-dd HH:mm:ss'/>">
                </div>
            </div>
        </div>
    </div>

    <div class="button-group-wrapper">
        <div style="display: flex; justify-content: center;">
            <button type="submit" class="btn btn-success me-2">수정완료</button>
            <button type="button" class="btn btn-warning me-2" onclick="backToList(this)">목록</button>
        </div>
    </div>
</form>


	<%-- Include the chatbot module --%>
<%-- 	<jsp:include page="./chatbot.jsp" /> --%>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>