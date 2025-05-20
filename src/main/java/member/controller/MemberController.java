package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.MemberService;
import member.vo.UserVO;

@WebServlet("/member/*")
public class MemberController extends HttpServlet{

	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		
		MemberService memberService = new MemberService();
		
        if (action.equals("/register")) { //회원가입 화면 요청
            System.out.println("/register 요청 처리 시작");
            
            req.setAttribute("center", "members/register.jsp");
            
            nextPage = "/main.jsp";
            
        }else if(action.equals("/new")) { //회원가입 요청
        	
        	memberService.insertMember(req);
        	
        	out.println("<script>");
        	out.println("alert('회원가입이 완료되었습니다.');");
        	out.println("location.href='"+req.getContextPath()+"/campus/main';");
        	out.println("</script>");
        	
        	return;
        
        }else if(action.equals("/checkEmail")) { //이메일 중복 체크 요청
			
            boolean result = memberService.serviceOverLappedId(req);
            // AJAX 응답을 위해 PrintWriter 얻기
            out = resp.getWriter();
            // 결과를 클라이언트(JavaScript)로 전송
            if (result) { // true (중복)
                out.write("not_usable");
            } else { // false (사용 가능)
                out.write("usable");
            }
			
			return;
        	
        }else if(action.equals("/loginForm")) { //로그인 화면 요청
        	
			System.out.println("/loginForm 요청 처리 시작");
			
			req.setAttribute("center", "members/login.jsp");
			
			nextPage = "/main.jsp";
			
        }else if(action.equals("/login")) { //로그인 요청
			
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			
			HttpSession session = req.getSession();
			
			/*
			 *  황도균 : 임시로 추가 ( 이메일 대신 교수 아이디를 사용하기 위해서)
			 *  추가한 부분
			 *  = session.setAttribute("professor_id", 4);
			 */
			
			//로그인 처리
			UserVO vo = memberService.login(email, password);
			
			if(vo != null) {
				
				//교수 로그인시 교수 아이디 세션에 저장
				if(vo.getRole().equals("PROFESSOR")) {
					
					System.out.println("교수 로그인 성공: "+vo.getId());
					
					session.setAttribute("professor_id", vo.getId());
				}
				
				if(vo.getRole().equals("STUDENT")) {
					
					System.out.println("학생 로그인 성공: "+vo.getId());
					
					session.setAttribute("student_id", vo.getId());
				}
				
				session.setAttribute("vo", vo);
				System.out.println(vo);
					
				out.println("<script>");
				out.println("alert('로그인 성공');");
				out.println("location.href='"+req.getContextPath()+"/campus/main';");
				out.println("</script>");
				
				return;
				
			}else {
				out.println("<script>");
				out.println("alert('로그인 실패');");
				out.println("history.back();");
				out.println("</script>");
				
				return;
			}
        
        }else if(action.equals("/logout")) { //로그아웃 요청
			
			HttpSession session = req.getSession();
			session.invalidate(); //세션 무효화
			
			out.println("<script>");
			out.println("alert('로그아웃 되었습니다.');");
			out.println("location.href='"+req.getContextPath()+"/campus/main';");
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
