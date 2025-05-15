<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<div style="width: 70%; margin: 30px auto;">
  <h2 style="text-align: center;">개인정보 수정</h2>
  <form action="${pageContext.request.contextPath}/student/profileupdate" method="post" style="margin-top: 30px;">

    <table style="width: 100%; border-collapse: collapse;">
      <tr>
        <th>이름</th>
        <td><input type="text" name="name" value="${studentVO.name}" readonly /></td>
      </tr>
      <tr>
        <th>학과</th>
        <td><input type="text" name="department" value="${studentVO.department}" /></td>
      </tr>
      <tr>
        <th>학년</th>
        <td><input type="number" name="grade" value="${studentVO.grade}" min="1" max="4" /></td>
      </tr>
      <tr>
        <th>학적 상태</th>
        <td>
          <select name="status">
            <option value="재학" ${studentVO.status == '재학' ? 'selected' : ''}>재학</option>
            <option value="휴학" ${studentVO.status == '휴학' ? 'selected' : ''}>휴학</option>
            <option value="졸업" ${studentVO.status == '졸업' ? 'selected' : ''}>졸업</option>
          </select>
        </td>
      </tr>
    </table>

    <div style="text-align: center; margin-top: 20px;">
      <button type="submit">수정하기</button>
    </div>
  </form>
</div>

<%@ include file="../bottom.jsp" %>
