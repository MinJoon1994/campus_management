package student.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.MemberService;
import member.vo.StudentVO;
import member.vo.UserVO;
import student.service.StudentService;
import student.service.StudentServiceImpl;
import student.vo.LectureVO;

@WebServlet("/student/*")
public class StudentController extends HttpServlet{
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		
		StudentService studentService = new StudentServiceImpl();
		
		//============= 학생 수강신청 관련==============
        if (action.equals("/enrollForm")) { //학생 수강신청 화면 요청
            
            req.setAttribute("center", "students/enrollForm.jsp"); //수강 신청화면 요청
            
            nextPage = "/main.jsp";
            
        }else if(action.equals("/enroll")) { // 학생 수강신청 요청
        	
        	
        	studentService.enroll(req);
        	
        	out.println("<script>");
        	out.println("alert('수강신청이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/courcelist;");
        	out.println("</script>");
        	
        	return;
        
        }else if(action.equals("/courcelist")) { //학생 수강목록 확인
			
        	//학생 수강목록 LIST 로 받아오기
            List<LectureVO> list = studentService.getLectureList(req);
            
            //뷰쪽에 LIST 형태로 전달
            req.setAttribute("list", list);
            
            //학생 수강목록 확인 VIEW
            req.setAttribute("center", "students/courcelist.jsp");
            
            nextPage = "/main.jsp";
			
			return;
        	
        }else if(action.equals("/enrolldelete")) { //학생 수강신청 취소요청
        	
        	studentService.enrollDelete(req);
        	
        	out.println("<script>");
        	out.println("alert('수강신청이 삭제되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/courcelist;");
        	out.println("</script>");
        	
        	return;
        		
        }
        //============= 학생 성적관련 ==============
        else if(action.equals("/grades")) {//학생 전체 학기 성적 조회
        	
        	//LIST 형태로 전체학기 성적 받아오기
        	List list = studentService.getGrades(req); 
        	
        	//뷰쪽에 LIST 형태로 전달
			req.setAttribute("list", list);
			
			//학생 성적 확인 VIEW
			req.setAttribute("center", "students/grades.jsp");
			
			nextPage = "/main.jsp";
			
			return;
        	
        }else if(action.equals("/gradesdetail")) {//학생 성적 상세 조회
			
			//학생 성적 상세 조회
			List list = studentService.getGradesDetail(req);
			
			//뷰쪽에 LIST 형태로 전달
			req.setAttribute("list", list);
			
			req.setAttribute("center", "students/gradesdetail.jsp");
			
			nextPage = "/main.jsp";
			
			return;
        
        }
        //============= 학생 시간표 관련 ==============
        else if(action.equals("/timetable")) {//학생 시간표조회
        	
        	//학생 시간표 LIST 형태로 받아오기
        	List list = studentService.getTimeTable(req);
        	
        	//뷰쪽에 LIST 형태로 전달
        	req.setAttribute("list", list);
        	
        	req.setAttribute("center", "students/timetable.jsp");
        	
        	nextPage = "/main.jsp";
        	
        	return;
        	
        }
        //============= 학생 개인 정보 관련 ==============
        else if(action.equals("/profile")) {//학생 개인정보 조회
        	
        	//학생 개인 정보 조회
        	StudentVO studentVO = studentService.getStudent(req);
        	
        	//뷰쪽에 학생 개인정보 전달
        	req.setAttribute("studentVO", studentVO);
        	
        	req.setAttribute("center", "students/profile.jsp");
        	
        	nextPage = "/main.jsp";
        	
        	return;
        	
        }else if(action.equals("/profileupdate")){//학생 개인정보 수정 요청
        	
        	//학생 개인 정보 수정
        	studentService.updateStudent(req);
        	
        	out.println("<script>");
        	out.println("alert('수정이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/profile;");
        	out.println("</script>");
        	
        	return;
        	
        }
        //============= 학생 질문글 관련 ==============
        else if(action.equals("/qnaform")) {//학생 질문글 관련 등록
        	
        	//학생 질문글 작성 화면 요청
			req.setAttribute("center", "students/qnaform.jsp");
			
			nextPage = "/main.jsp";
			
			return;
			
        }else if(action.equals("/qnaclass")) {//학생 강의관련 질문글 등록 요청
        	
        	studentService.qnaClass(req);
        	
        	out.println("<script>");
        	out.println("alert('질문등록이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/qna/list;");
        	out.println("</script>");
        	
        	return;
        	
        }else if(action.equals("/qnacampus")) {//학생 학교관련 질문글 등록 요청
        	
        	studentService.qnaCampus(req);
			
			out.println("<script>");
			out.println("alert('질문등록이 완료되었습니다.');");
			out.println("location.href='"+req.getContextPath()+"/qna/list;");
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
