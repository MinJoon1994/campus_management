package admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import admin.service.AdminService;
import admin.service.AdminServiceImpl;
import admin.vo.Admin_StudentVO;
import admin.vo.Admin_LectureVO;
import qna.vo.QnaVO;
import student.vo.LectureVO;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet{
protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		
		System.out.println("수정테스트");
		
		AdminService adminService = new AdminServiceImpl();
		
		//============= 교내 구성원 관련 ==============
		if (action.equals("/memberlist")) {
			int studentPage = 1;
			int professorPage = 1;
			
			String activeTab = req.getParameter("activeTab");
			
			if (req.getParameter("studentPage") != null) {
				studentPage = Integer.parseInt(req.getParameter("studentPage"));
			}
			if (req.getParameter("professorPage") != null) {
				professorPage = Integer.parseInt(req.getParameter("professorPage"));
			}

			List studentlist = adminService.getStudentList(req, studentPage);
			List professorlist = adminService.getProfessorList(req, professorPage);

			req.setAttribute("studentlist", studentlist);
			req.setAttribute("professorlist", professorlist);
			req.setAttribute("studentPage", studentPage);
			req.setAttribute("professorPage", professorPage);
			
			int studentTotal = adminService.getStudentCount();
			int professorTotal = adminService.getProfessorCount();

			int pageSize = 10;
			int studentTotalPage = (int)Math.ceil((double)studentTotal / pageSize);
			int professorTotalPage = (int)Math.ceil((double)professorTotal / pageSize);

			req.setAttribute("studentTotalPage", studentTotalPage);
			req.setAttribute("professorTotalPage", professorTotalPage);
			
			req.setAttribute("activeTab", activeTab); // ⭐ 반드시 추가해야 작동함

			req.setAttribute("center", "admins/memberlist.jsp");
			nextPage = "/main.jsp";
		}
		//학생 필터링 ajax 요청
		else if(action.equals("/filterStudent")) {
			resp.setContentType("application/json; charset=UTF-8");
			req.setCharacterEncoding("UTF-8");

			String grade = req.getParameter("grade");
			String status = req.getParameter("status");

			int page = Integer.parseInt(Optional.ofNullable(req.getParameter("page")).orElse("1"));
			int pageSize = Integer.parseInt(Optional.ofNullable(req.getParameter("pageSize")).orElse("10"));
			int offset = (page - 1) * pageSize;

			List<Admin_StudentVO> list = adminService.getFilteredStudentList(grade, status, offset, pageSize);
			int total = adminService.getFilteredStudentCount(grade, status);

			JSONObject result = new JSONObject();
			JSONArray arr = new JSONArray();

			for (Admin_StudentVO s : list) {
				JSONObject o = new JSONObject();
				o.put("user_id", s.getUser_id());
				o.put("email", s.getEmail());
				o.put("name", s.getName());
				o.put("password", s.getPassword());
				o.put("student_number", s.getStudent_number());
				o.put("department", s.getDepartment());
				o.put("grade", s.getGrade());
				o.put("status", s.getStatus());
				arr.add(o);
			}

			result.put("studentList", arr);
			result.put("totalCount", total);

			out.print(result.toString());
			return;
		}
		
        //공지사항 관련
        else if(action.equals("/noticeform")) { //공지사항 등록폼 요청
        	
        	req.setAttribute("center", "admins/noticeForm.jsp");
        	
        	nextPage = "/main.jsp";
        	
        }else if(action.equals("/notice")) { //공지사항 등록 요청
			
        	//공지사항 등록
        	adminService.addNotice(req);
        	
        	out.println("<script>");
        	out.println("alert('공지사항 등록이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/notice/list';");
        	out.println("</script>");
        	
        	return;
        	
        	
        }else if(action.equals("/noticeupdate")) { //공지사항 수정 요청
        	
        	//공지사항  수정 요청
        	adminService.updateNotice(req);
        	
        	out.println("<script>");
        	out.println("alert('공지사항 수정이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/notice/list';");
        	out.println("</script>");
        	
        	return;
        		
        }else if(action.equals("/noticedelete")) {//공지사항 삭제 요청
        	
        	//공지사항 삭제 요청
        	adminService.deleteNotice(req);
        	
        	out.println("<script>");
        	out.println("alert('공지사항 삭제가 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/notice/list';");
        	out.println("</script>");
        	
        	return;
        	
        	
        }
        //================ 강의 관련 ===================
        else if(action.equals("/lecturelist")) {//교수가 등록한 강의 목록 보기
			
        	//교수가 등록한 강의목록을 LIST 형태로 들고오기
        	List<Admin_LectureVO> list = adminService.getlectureList(req);
        	
        	//강의목록 VIEW 쪽에 전달
        	req.setAttribute("lectureList", list);
        	
        	req.setAttribute("center", "admins/lecturelist.jsp");
        	
        	nextPage = "/main.jsp";

        }else if(action.equals("/approve")) { //교수가 등록한 강의 승인 처리
        	
        	adminService.approveLecture(req);
        	
        	out.println("<script>");
        	out.println("alert('강의 승인이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/admin/lecturelist';");
        	out.println("</script>");
        	
        	return;
        	
        	
        }else if(action.equals("/reject")) { //교수가 등록한 강의 거부 처리
        	
        	adminService.rejectLecture(req);
        	
        	out.println("<script>");
        	out.println("alert('강의 승인이 거부되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/admin/lecturelist';");
        	out.println("</script>");
        	
        	return;
        }
        //============= 학사일정 관련 ==============
        else if(action.equals("/schedulelist")) {
        	
        	req.setAttribute("center", "admins/schedulelist.jsp");
        	
        	nextPage = "/main.jsp";
        }
		//학사일정 추가
        else if (action.equals("/addEvent")) {
        	resp.setContentType("application/json; charset=UTF-8");
        	
        	boolean success = adminService.addCalendarEvent(req); // boolean 리턴 받는다고 가정
        	
        	JSONObject result = new JSONObject();
        	result.put("success", success);
        	out.print(result.toString());
        	return;
        }
		//학사일정 수정
        else if (action.equals("/updateEvent")) {
        	resp.setContentType("application/json; charset=UTF-8");
        	
        	boolean success = adminService.updateCalendarEvent(req);
        	
        	JSONObject result = new JSONObject();
        	result.put("success", success);
        	out.print(result.toJSONString());
        	return;
        }
		//학사일정 삭제
        else if (action.equals("/deleteEvent")) {
        	resp.setContentType("application/json; charset=UTF-8");
        	
        	boolean success = adminService.deleteCalendarEvent(req);
        	
        	JSONObject result = new JSONObject();
        	result.put("success", success);
        	out.print(result.toJSONString());
        	return;
        }
		
        //============= 질문글 관련 ==============
        else if(action.equals("/qnalist")) {//전체 질문글 목록 조회
        	
        	//전체 질문글들을 LIST 형태로 들고오기
        	List<QnaVO> list = adminService.getQnaList(req);
        	
        	//질문목록 VIEW 쪽에 전달
        	req.setAttribute("qnalist", list);
        	
        	req.setAttribute("center", "admins/qnalist.jsp");
        	
        	nextPage ="/main.jsp";
        	
        	
        }else if(action.equals("/qnadetail")){ //특정 질문글 보기
        	
        	//특정 질문글 보기 요청
        	QnaVO vo = adminService.getDetailQna(req);
        	
        	//특정 질문글 정보 VIEW쪽에 전달
        	req.setAttribute("qnavo", vo);
        	
        	req.setAttribute("center", "admins/qnadetail.jsp");
        	
        	nextPage="/main.jsp";
        	
        }else if(action.equals("/replyform")) { //답변 작성 폼 요청
        	
        	req.setAttribute("center", "admins/replyform.jsp");
        	
        	nextPage="/main.jsp";
        	
        }else if(action.equals("/reply")) { //답변 작성 요청
        	
        	//답변 작성
        	adminService.reply(req);
        	
        	out.println("<script>");
        	out.println("alert('답변 등록이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/admin/qnalist';");
        	out.println("</script>");
        	
        	return;
			
        }
        
        
		if(nextPage != null) {
			req.getRequestDispatcher(nextPage).forward(req, resp);
		} else {
            System.out.println("nextPage가 null입니다. (아마도 out.print로 직접 응답 처리됨)");
        }
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req,resp);
	}
}
