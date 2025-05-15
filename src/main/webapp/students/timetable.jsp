<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../top.jsp" %>

<!-- FullCalendar CSS & JS -->
<link href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.css' rel='stylesheet' />
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.js'></script>

<div style="width: 95%; margin: 20px auto;">
  <h2 style="text-align:center;">${vo.name}님의 수강 시간표</h2>
  <div id='calendar'></div>
</div>

<script>
  // 고정 색상 맵 (과목명 기준)
  const subjectColors = {};
  const colorPalette = ['#f94144','#f3722c','#f8961e','#f9844a','#f9c74f','#90be6d','#43aa8b','#577590'];
  let colorIndex = 0;

  function getSubjectColor(title) {
    if (!subjectColors[title]) {
      subjectColors[title] = colorPalette[colorIndex % colorPalette.length];
      colorIndex++;
    }
    return subjectColors[title];
  }

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'timeGridWeek',
      allDaySlot: false,
      slotMinTime: '08:00:00',
      slotMaxTime: '20:00:00',
      headerToolbar: {
        left: '',
        center: 'title',
        right: ''
      },
      locale: 'ko',
      editable: false,
      events: [
        <c:forEach var="event" items="${list}" varStatus="status">
          {
            title: '${event.title}',
            daysOfWeek: ['${event.daysOfWeek[0]}'],
            startTime: '${event.startTime}',
            endTime: '${event.endTime}',
            display: 'block',
            backgroundColor: getSubjectColor('${event.title}'),
            borderColor: getSubjectColor('${event.title}'),
            url: '${pageContext.request.contextPath}/student/gradesDetail?subject_code=${event.subjectCode}'
          }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
      ],
      eventClick: function(info) {
        if (info.event.url) {
          window.location.href = info.event.url;
          info.jsEvent.preventDefault();
        }
      }
    });

    calendar.render();
  });
</script>

<%@ include file="../bottom.jsp" %>
