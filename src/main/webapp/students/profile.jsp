<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../top.jsp" %>

<div style="display:flex; justify-content:center; margin-top:40px;">
  <div style="width:80%; background:white; padding:40px; border-radius:15px; box-shadow:0 4px 10px rgba(0,0,0,0.1);">
    <h2 style="text-align:center; margin-bottom:30px;">학생 마이페이지</h2>
    
    <table style="width:100%; border-collapse:collapse; font-size:16px;">
      <tr style="border-bottom:1px solid #ccc;">
        <td style="padding:10px; font-weight:bold;">이름</td>
        <td style="padding:10px;">${studentVO.name}</td>
      </tr>
      <tr style="border-bottom:1px solid #ccc;">
        <td style="padding:10px; font-weight:bold;">학번</td>
        <td style="padding:10px;">${studentVO.studentId}</td>
      </tr>
      <tr style="border-bottom:1px solid #ccc;">
        <td style="padding:10px; font-weight:bold;">학과</td>
        <td style="padding:10px;">${studentVO.departmentName}</td>
      </tr>
    </table>
  </div>
</div>

<%@ include file="../bottom.jsp" %>
