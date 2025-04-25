package qna.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.service.NoticeService;
import notice.service.NoticeServiceImpl;
import notice.vo.NoticeVO;
import qna.service.QnaService;
import qna.service.QnaServiceImpl;
import qna.vo.QnaVO;

@WebServlet("/qna/*")
public class QnaController extends HttpServlet{
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String action = req.getPathInfo();
		String nextPage = null;
		PrintWriter out = resp.getWriter();
		
		QnaService qnaService = new QnaServiceImpl();
		
        if (action.equals("/list")) { //질문글 목록 조회 요청
        	
        	//질문글 목록을 List형태로 가져오기
        	List<QnaVO> list  = qnaService.getQnaList(req);
        	
        	//질문글 목록 VIEW쪽에 전달
        	req.setAttribute("qnalist", list);
        	
        	req.setAttribute("center", "qnas/qnalist.jsp");
        	
        	nextPage="/main.jsp";

            
        }else if(action.equals("/detail")) { //특정 질문글 조회 요청
        	
        	//특정 질문글 정보 가져오기
        	QnaVO vo = qnaService.getQnaDetail(req);
        	
        	//특정 질문글 정보 VIEW 쪽에 전달
        	req.setAttribute("qnavo", vo);
        	
        	req.setAttribute("center", "qnas/qnadetail.jsp");
        	
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
