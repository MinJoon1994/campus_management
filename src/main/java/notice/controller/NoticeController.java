package notice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.vo.UserVO;
import notice.service.NoticeService;
import notice.service.NoticeServiceImpl;
import notice.vo.NoticeVO;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("NoticeController.doHandle() 호출됨");
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		
		NoticeService noticeService = new NoticeServiceImpl();
		
		
		
        if (action.equals("/list")) { //공지사항 목록 조회요청
        	

        	//공지사항을 List형태로 가져오기
        	List<NoticeVO> list  = noticeService.getNoticeList(req);
        	
        	req.setAttribute("noticelist", list);
        	
        	req.setAttribute("center", "notices/noticelist.jsp");
        	
        	nextPage="/main.jsp";

            
        }else if(action.equals("/detail")) { //특정 공지사항 조회
        	
        	
        	//특정 공지사항 정보 가져오기
        	NoticeVO vo = noticeService.getNoticeDetail(req);
        	
        	//특정 공지사항 정보
        	req.setAttribute("noticevo", vo);
        	
        	req.setAttribute("center", "notices/noticedetail.jsp");
        	
        	nextPage="/main.jsp";
        
        }else if(action.equals("/noticeForm.do")) { //공지사항 작성 요청

        	//out.println("공지사항 작성 요청");
        	String title = req.getParameter("title");
        	String content = req.getParameter("content");
        	UserVO id = (UserVO) req.getSession().getAttribute("id");

        	NoticeVO notice = new NoticeVO();
        	notice.setTitle(title);
        	notice.setContent(content);
        	notice.setUserVO(id);
        	//notice.setAdminName(userid.getRole());
             
        	int result = noticeService.insertNotice(notice);

        	if (result > 0) {
        		 resp.sendRedirect(req.getContextPath() + "/notice/list");
        	} else {
        		req.setAttribute("errorMsg", "공지사항 등록 실패");
        		req.getRequestDispatcher("/notices/noticewrite.jsp");
             }        	
			//공지사항 작성 폼으로 이동
        	nextPage="/main.jsp";
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
