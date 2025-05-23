package student.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.MemberService;
import member.vo.StudentVO;
import member.vo.UserVO;
import professorvo.QnaWithReplyVo;
import student.dao.StudentDAO;
import student.service.StudentService;
import student.service.StudentServiceImpl;
import student.vo.LectureVO;
import student.vo.StudentGradeVO;
import student.vo.StudentQnaListVO;
import student.vo.StudentQnaWithRelpyVO;
import student.vo.StudentQusetionVO;
import student.vo.StudentSubjectVO;
import student.vo.StudentTimetableVO;

@WebServlet("/student/*")
public class StudentController extends HttpServlet{
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		StudentService studentService = new StudentServiceImpl();
		
		//학생 메인 화면 요청
        if (action.equals("/main")) { //학생 메인 화면 요청
                       
            nextPage = "/students/StudentMain.jsp";
            
        }else if (action.equals("/enrollForm")) { //학생 수강신청 화면 요청
            
        	//학생 수강신청 정보 LIST 형태로 받아오기
        	List<LectureVO> list = studentService.getList(req);
        	
        	System.out.println("수강신청 정보 LIST : " + list);
        	
        	//뷰쪽에 LIST 형태로 전달
        	req.setAttribute("list", list);
        	
            req.setAttribute("center", "/students/enrollForm.jsp"); //수강 신청화면 요청
            
            nextPage = "/students/StudentMain.jsp";
            
        }else if(action.equals("/enroll")) { // 학생 수강신청 요청
        	
        	int result = studentService.enroll(req);
        	
        	if(result == 0) {
        		
				out.println("<script>");
				out.println("alert('정원이 초과되었습니다.');");
				out.println("location.href='"+req.getContextPath()+"/student/enrollForm';"); //수강신청 화면으로 이동
				out.println("</script>");
				
				return;
			}
        	
        	out.println("<script>");
        	out.println("alert('수강신청이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/enrollForm';");
        	out.println("</script>");
        	
        	return;
        
        }else if(action.equals("/modifyForm")) {//학생 개인정보 변경 폼 요청
        	
        	//학생 개인정보 가져오기
        	UserVO userVO = studentService.getStudent(req);
        	
        	req.setAttribute("vo", userVO);
        	
        	//학생 학적변경 요청 폼으로 이동
			req.setAttribute("center", "/students/modifyForm.jsp");
			
			nextPage = "/students/StudentMain.jsp";
        	
        }else if(action.equals("/modify")) {//학생 개인정보 수정
        	
        	studentService.updateStudent(req);
        	
        	out.println("<script>");
        	out.println("alert('수정이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/modifyForm';");
        	out.println("</script>");
        	
        }else if(action.equals("/courselist")) { //학생 수강목록 확인
			
        	//학생 수강목록 LIST 로 받아오기
            List<LectureVO> list = studentService.getLectureList(req);
            
            //뷰쪽에 LIST 형태로 전달
            req.setAttribute("list", list);
            
            //학생 수강목록 확인 VIEW
            req.setAttribute("center", "/students/courselist.jsp");
            
            nextPage = "/students/StudentMain.jsp";
			
        	
        }else if(action.equals("/enrolldelete")) { //학생 수강신청 취소요청
        	studentService.enrollDelete(req);
        	
        	out.println("<script>");
        	out.println("alert('수강신청이 삭제되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/student/courselist';");
        	out.println("</script>");
        	
        	return;
        	
        // ------------- 학생 전체 학기 성적 조회 -------------
        }else if (action.equals("/grades")) {
            List<StudentGradeVO> list = studentService.getGrades(req);
            req.setAttribute("list", list);
            req.setAttribute("center", "/students/grades.jsp"); 
            
            nextPage = "/students/StudentMain.jsp"; 

        // ------------- 학생 성적 상세 조회 -------------
        } else if (action.equals("/gradesdetail")) {

            List<student.vo.SubjectGradeVO> list = studentService.getGradesDetail(req);
            req.setAttribute("list", list);

            // center는 students/gradesdetail.jsp
            req.setAttribute("center", "students/gradesdetail.jsp");
            nextPage = "/main.jsp";
            return;

        }
        //============= 학생 시간표 관련 ==============
        else if(action.equals("/timetable")) {//학생 시간표조회
        	List<StudentTimetableVO> list = studentService.getTimeTable(req);
        	req.setAttribute("list", list);
        	req.setAttribute("center", "/students/StudentTimetable.jsp");
        	
        	nextPage = "/students/StudentMain.jsp"; 
        	
        }
        //============= 학생 질문글 관련 ==============
        // 학생 -> 교수 질문 화면 요청
        else if(action.equals("/qnaLectureList")) {//학생 질문글 관련 등록
        	// 학생이 수강한 과목 목록 리스트
        	List<StudentSubjectVO> subjectList = studentService.getStudentSubject(req);
        	
        	req.setAttribute("subjectList", subjectList);
        	
        	// 교수,학생 질문 테이블 조회하기(학생이 수강한 과목에 대해)
        	List<StudentQnaListVO> qnaList = studentService.getStudentQna(req);
        	req.setAttribute("qnaList", qnaList);
        	
        	req.setAttribute("center", "/students/StudentQnaMain.jsp");
        	
			nextPage = "/students/StudentMain.jsp";
			
        }
        // select option 과목에 따라
        else if(action.equals("/qnalistbySubject")) {
        	String subjectCode = req.getParameter("subjectCode");
        	req.setAttribute("subjectCode", subjectCode);
        	
        	List<StudentQnaListVO> qnaList;
        	
        	if (subjectCode == null || subjectCode.isEmpty()) {
        	    qnaList = studentService.getStudentQna(req);
        	} else {
        	    qnaList = studentService.getQnaBySubject(subjectCode);
        	}
        	req.setAttribute("qnaList", qnaList);
        	
        	// 학생이 수강한 과목 목록 리스트
        	List<StudentSubjectVO> subjectList = studentService.getStudentSubject(req);
        	req.setAttribute("subjectList", subjectList);
        	
        	req.setAttribute("center", "/students/StudentQnaMain.jsp");
			nextPage = "/students/StudentMain.jsp";
        }
        // 질문 상세 조회
        else if(action.equals("/qnaStudentDetail")) {
        	String qnaId = req.getParameter("qnaId");
        	StudentQnaWithRelpyVO vo = studentService.getQnaWithReply(qnaId);
			
			req.setAttribute("qnavo", vo);
			
			nextPage = "/students/StudentQnaCheck.jsp";
        }
        //학생 질문글 관련 등록
        else if(action.equals("/insertStudentQ")) {
            String subjectCode = req.getParameter("subject_code");
            String title = req.getParameter("title");
            System.out.println("이거제목"+title);
            String content = req.getParameter("content");

            StudentQusetionVO vo = new StudentQusetionVO();
            vo.setSubjectCode(subjectCode);
            vo.setQuestionerTitle(title);
            vo.setQuestion(content);

            int result = studentService.insertStudentQna(req, vo);

            out.println("<script>");
            if (result > 0) {
                out.println("alert('질문이 등록되었습니다.');");
            } else {
                out.println("alert('등록에 실패했습니다.');");
            }
            out.println("location.href='" + req.getContextPath() + "/student/qnaLectureList';");
            out.println("</script>");
			
			return;
			
        } 
        else if(action.equals("/deleteQna")) {
            int qnaId = Integer.parseInt(req.getParameter("qnaId"));
            studentService.studentDeleteQ(qnaId);
            out.println("<script>");
            out.println("alert('질문이 삭제되었습니다.');");
            out.println("if (window.opener) window.opener.location.reload();"); 
            out.println("window.close();"); 
            out.println("</script>");
            return;
        }
        else if(action.equals("/qnaCampusForm")) {
        	
        	req.setAttribute("center", "/students/qnaCampusForm.jsp");
        	
			nextPage = "/students/StudentMain.jsp";
        }
        else if(action.equals("/qnaCampus")) {//학생 학교관련 질문글 등록 요청
        	
        	studentService.qnaCampus(req);
			
			out.println("<script>");
			out.println("alert('질문등록이 완료되었습니다.');");
			out.println("location.href='"+req.getContextPath()+"/qna/list';");
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
