<%@page import="professorvo.LectureListVo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	Vector<LectureListVo> subjectVo = (Vector<LectureListVo>) request.getAttribute("subjectList");
	// 시간표 초기화
	String[][] timetable = new String[5][9]; // [요일][교시]
	String[] days = { "월", "화", "수", "목", "금" };
    
	Map<String, String> subjectColorMap = new HashMap<>();
    
	for(LectureListVo v : subjectVo) {
    	String subjectName = v.getSubjectName();
    	System.out.println("과목명 : " + subjectName);
    	// 월 3-5교시, 화 4-7교시
    	String schedule = v.getSchedule();
    	System.out.println("시간표 : " + schedule);
    	
        // 과목별 고유 색상 만들기
        int hash = Math.abs(subjectName.hashCode()); // 
        String color = String.format("#%06X", (hash & 0xFFFFFF));
        subjectColorMap.put(subjectName, color);

        // 시간표 채우기
        String[] blocks = schedule.split(",");
        for (String block : blocks) {
            block = block.trim();
            // block[0] = 월 3-5교시
           	// block[1] = 화 4-7교시
            for (int i = 0; i < days.length; i++) {
                if (block.startsWith(days[i])) {
                	String timePart = block.replaceAll("[^0-9\\-]", ""); // 3-5
                	String[] range = timePart.split("-"); // range[0] = 3, range[1] = 5
					// 0부터 시작하는 timetable 배열 때문에 -1 처리
                	int start = Integer.parseInt(range[0]) - 1;
                	int end = (range.length > 1) ? Integer.parseInt(range[1]) - 1 : start;
					
                	for (int j = start; j <= end; j++) { // 2~4
                	    if (timetable[i][j] == null) timetable[i][j] = subjectName;
                	    // [월][2], [월][3], [월][4]
                	}
                }
            }
        }
    }
%>

<style>
	.container {
        width: 90%;         /* 전체 폭의 90% 사용 (양쪽 5% 여백) */
        margin: 0 auto;     /* 중앙 정렬 */
        padding-top: 10px;
   	}
    table {
        width: 80%;
        border-collapse: collapse;
    }
    th, td {
        width: 100px;
        height: 50px;
        text-align: center;
        border: 1px solid #ccc;
    }
</style>
<div class="container">
	<h2 style="text-align: center; padding-bottom: 20px;">시간표</h2>
	<table align="center">
	    <tr>
	        <th>시간/요일</th>
	        <th>월</th>
	        <th>화</th>
	        <th>수</th>
	        <th>목</th>
	        <th>금</th>
	    </tr>
	    <%
	        for (int i = 0; i < 9; i++) {
	    %>
			    <tr>
			        <td><%= (i+1) %>교시</td>
			        <%
			            for (int j = 0; j < 5; j++) {
			                String cell = timetable[j][i];
			                String bgColor = (cell != null && subjectColorMap.containsKey(cell)) ? subjectColorMap.get(cell) : "";
			        %>
			            <td style="background-color:<%= bgColor %>;">
			                <%= (cell != null ? cell : "") %>
			            </td>
			        <% 
			        	} 
			        %>
			    </tr>
	    <% 
	    	} 
	    %>
	</table>
</div>
