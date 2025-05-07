package professorcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import professorservice.ProfessorService;
import professorvo.EnrolledStudentVo;
import professorvo.LectureListVo;
import professorvo.LecturePlanVo;
import professorvo.SubjectVo;

@WebServlet("/professor/*")
public class ProfessorController extends HttpServlet {
	private ProfessorService professorService;

	@Override
	public void init() throws ServletException {
		professorService = new ProfessorService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		PrintWriter pw = response.getWriter();

		String action = request.getPathInfo();
		System.out.println("요청한 2단계 주소 이거:" + action);

		String nextPage = null;
        
		LecturePlanVo planvo = new LecturePlanVo();
		
		// 로그인 중인 교수 id(교수 이메일) 를 얻기 위해
		HttpSession session = request.getSession(false); // false는 세션이 없으면 새로운 세션을 만들지 않음
		session.setAttribute("id", "4");

		// 메인화면 요청 처리
		if (action.equals("/main")) {

			nextPage = "/professors/ProfessorMain.jsp";
		}
		/*
		 * ----------------------------------------------------------------------------
		 *                                강의관리
		 * ----------------------------------------------------------------------------
		 */
		// 강의 개설 폼 요청
		else if (action.equals("/lectureform")) {
			String id = (String) session.getAttribute("id");
			System.out.println("강의 개설 폼 요청...");
			
			request.setAttribute("professor_id" , id);
			request.setAttribute("center", "/professors/LectureForm.jsp");
			
			nextPage = "/professors/ProfessorMain.jsp";
		}
		// 강의 개설 요청
		else if (action.equals("/lecturecreate")) {
		    String subjectCode = request.getParameter("subject_code");
		    String subjectName = request.getParameter("subject_name");
		    String subjectType = request.getParameter("subject_type");
		    int openGrade = Integer.parseInt(request.getParameter("open_grade"));
		    String division = request.getParameter("division");
		    int credit = Integer.parseInt(request.getParameter("credit"));
		    int professorId = Integer.parseInt(request.getParameter("professor_id"));
		    String professorName = request.getParameter("professor_name");
		    int capacity = Integer.parseInt(request.getParameter("capacity"));

		    String[] days = request.getParameterValues("day[]");
		    String[] startTimes = request.getParameterValues("start_time[]");
		    String[] endTimes = request.getParameterValues("end_time[]");

		    StringBuilder scheduleBuilder = new StringBuilder();
		    if (days != null && startTimes != null && endTimes != null) {
		        for (int i = 0; i < days.length; i++) {
		            scheduleBuilder.append(days[i])
		                           .append(" ")
		                           .append(startTimes[i])
		                           .append("-")
		                           .append(endTimes[i])
		                           .append("교시, ");
		        }
		        // 마지막 콤마 제거
		        if (scheduleBuilder.length() > 0) {
		            scheduleBuilder.setLength(scheduleBuilder.length() - 2);
		        }
		    }
		    String schedule = scheduleBuilder.toString();
		    System.out.println(schedule);
		    System.out.println(capacity);
		    // 월 4-6교시, 월 7-8교시

		    SubjectVo subjectVo = new SubjectVo(
		        subjectCode,
		        subjectName,
		        subjectType,
		        openGrade,
		        division,
		        credit,
		        professorId,
		        professorName,
		        schedule,
		        0,          // 현재 수강 인원은 기본 0
		        capacity,
		        false        // 수강 가능 여부
		    );

		    boolean result = professorService.addSubject(subjectVo);
		    
		    if (result) {
		        request.setAttribute("message", "강의 등록이 완료되었습니다!");
		        request.setAttribute("center", "/professors/CompleteRegisteringLecture.jsp");
		    } else {
		        request.setAttribute("message", "강의 등록에 실패했습니다. 다시 시도해주세요.");
		        request.setAttribute("center", "/professors/FailRegisteringLecture.jsp");
		    }
		    nextPage = "/professors/ProfessorMain.jsp";
		}
		// 요청한 강의 확인
		else if (action.equals("/lecturerequest")) {
			String id = (String) session.getAttribute("id");
			
			Vector<SubjectVo> LectureListV = professorService.getAllSubject(id);
			for(SubjectVo s : LectureListV) {
				System.out.println("이값인데 : "+s.getisAvailable()); // false
			}
			request.setAttribute("subjectList", LectureListV);
			request.setAttribute("center", "/professors/RequestSubjectList.jsp");
			nextPage ="/professors/ProfessorMain.jsp";
		}
		
		// 나의 강의목록 조회
		else if (action.equals("/lectures")) {
			String id = (String) session.getAttribute("id");
			
			Vector<LectureListVo> LectureListV = professorService.getAllLectureList(id);
			request.setAttribute("v", LectureListV);
			
			// 강의 등록 신청 완료 화면
			request.setAttribute("center", "/professors/LectureList.jsp");
			nextPage = "/professors/ProfessorMain.jsp";
		}
		// 강의계획서 조회
		else if (action.equals("/lectures/lectureplan")) {
			String subjectList = request.getParameter("subjectList");
			String subjectCode = request.getParameter("subjectCode");
			
			LecturePlanVo lecturePlanVo = professorService.getAllLecturePlanList(subjectCode);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("lecturePlanVo", lecturePlanVo);
			nextPage = "/professors/LecturePlan.jsp";
		}

		// 강의계획서 등록
		else if (action.equals("/lectures/lectureplanadd.do")) {
			planvo.setSubjectCode(request.getParameter("subjectCode"));
			planvo.setSubjectName(request.getParameter("subjectName"));
	        planvo.setProfessorId(request.getParameter("professorId"));
	        planvo.setProfessorName(request.getParameter("professor"));
	        planvo.setLecturePeriod(request.getParameter("lecturePeriod"));
	        planvo.setTargetStudents(request.getParameter("open_grade"));
	        planvo.setMainContent(request.getParameter("mainContent"));
	        planvo.setGoal(request.getParameter("goal"));
	        planvo.setMethod(request.getParameter("method"));
	        planvo.setContent(request.getParameter("content"));
	        planvo.setEvaluation(request.getParameter("evaluation"));
		    
	        boolean result = professorService.addLecturePlan(planvo);

		    if (result) {
		    	pw.println("<script>alert('등록 성공');history.back();</script>");
		    } else {
		        pw.println("<script>alert('등록 실패');history.back();</script>");
		        return;
		    }
			
		}
		// 강의계획서 수정
		else if (action.equals("/lectures/lectureplanupdate.do")) {
			planvo.setSubjectCode(request.getParameter("subjectCode"));
			planvo.setSubjectName(request.getParameter("subjectName"));
	        planvo.setProfessorId(request.getParameter("professorId"));
	        planvo.setProfessorName(request.getParameter("professor"));
	        planvo.setLecturePeriod(request.getParameter("lecturePeriod"));
	        planvo.setTargetStudents(request.getParameter("open_grade"));
	        planvo.setMainContent(request.getParameter("mainContent"));
	        planvo.setGoal(request.getParameter("goal"));
	        planvo.setMethod(request.getParameter("method"));
	        planvo.setContent(request.getParameter("content"));
	        planvo.setEvaluation(request.getParameter("evaluation"));
	        
			boolean result = professorService.updateLecturePlan(planvo);
		    if (result) {
		    	pw.println("<script>alert('수정 성공');history.back();</script>");
		    } else {
		        pw.println("<script>alert('수정 실패');history.back();</script>");
		        return;
		    }
		}
		// 강의계획서 삭제
		else if (action.equals("/lectures/lectureplandelete.do")) {
			String id = (String)session.getAttribute("id");
			System.out.println("강의게획서 삭제... : " + id);
			
			boolean result = professorService.deleteLecturePlan(id);
			if (result) {
				pw.println("<script>alert('삭제 성공');history.back();</script>");
		    } else {
		        pw.println("<script>alert('삭제 실패');history.back();</script>");
		        return;
		    }
		}
		// 교수 강의 시간표 조회
		else if (action.equals("/timetable")) {
			String id = (String)session.getAttribute("id");
			System.out.println("교수 강의 시간표 조회... : " + id);
			// 강의 테이블 조회
			Vector<SubjectVo> subjectVo = professorService.getAllSubject(id);
			request.setAttribute("subjectList", subjectVo);
			request.setAttribute("center" , "/professors/TimeTable.jsp");
			
			nextPage = "/professors/ProfessorMain.jsp";
		}
		/*
		 * ----------------------------------------------------------------------------
		 *                                수강생 관리
		 * ----------------------------------------------------------------------------
		 */
		
		// 1. 수강신청 학생명단 확인
		else if(action.equals("/enrolledstudent")) {
			String professor_id = (String)session.getAttribute("id");
			System.out.println("수강신청 학생명단 확인");
			
			Vector<EnrolledStudentVo> enrolledStudentVo = professorService.getAllEnrolledStudentList(professor_id);
			request.setAttribute("enrolledStudentList", enrolledStudentVo);
			request.setAttribute("center", "/professors/EnrolledStudentList.jsp");
			nextPage = "/professors/ProfessorMain.jsp";
		}
		System.out.println("nextpage 값 : " + nextPage);
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}
}
