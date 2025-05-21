package qna.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.service.NoticeService;
import notice.service.NoticeServiceImpl;
import notice.vo.NoticeVO;
import qna.service.QnaAdminService;
import qna.service.QnaAdminServiceImpl;
import qna.vo.QnaVO;

@WebServlet("/qna/*")
public class QnaAdminController extends HttpServlet{
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		int count = 0;
		String center = null;
		String nowBlock = null;
		String nowPage = null;
		String action = request.getPathInfo();
		String nextPage = null;
		PrintWriter out = response.getWriter();

		
		QnaAdminService qnaAdminService = new QnaAdminServiceImpl();
		
        if (action.equals("/listadmin")) { //질문글 목록 조회 요청
        	
        	//관리자에게 질문하는글 목록을 List형태로 가져오기
        	//List<QnaVO> list  = qnaAdminService.getQnaAdminList(request);
        	ArrayList<HashMap<String, Object>> list = qnaAdminService.getQnaAdminList();
        	
        	System.out.println("list : " + list.size());
        	
        	//count = qnaAdminService.getTotalAdminCount(request);

			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");

			System.out.println("nowBlock : " + nowBlock);
			System.out.println("nowPage : " + nowPage);
			
			//center = request.getParameter("center");
			//request.setAttribute("center", center);
        	//request.setAttribute("count", count);

        	request.setAttribute("qnalist", list);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);

			//로그인한 회원의 아이디 request에 바인딩
			//request.setAttribute("id", loginid);
 
			//질문글 목록 VIEW쪽에 전달
        	
        	request.setAttribute("center", "qnas/qnalist.jsp");
        	
        	nextPage="/main.jsp"; 

            
        }else if(action.equals("/detail")) { //특정 질문글 조회 요청
        	
        	String qnaID = request.getParameter("qnaID");
        	HashMap<String, Object> detail = qnaAdminService.getQnaAdminDetail(qnaID);

        	System.out.println("detail : " + detail);
        	System.out.println("qnaID: " + qnaID);
        	QnaVO vo = (QnaVO) detail.get("vo");
        	
        	System.out.println(vo.getTitle());
        	System.out.println(vo.getQuestion());
        	System.out.println(vo.getWriterName());
        	System.out.println(vo.getQuestionTime());
        	System.out.println(vo.getCategory());
        	
        	request.setAttribute("nowBlock", request.getParameter("nowBlock"));
    		request.setAttribute("nowPage", request.getParameter("nowPage"));        	
        	//특정 질문글 정보 VIEW 쪽에 전달
        	request.setAttribute("qnavo", vo);
    		request.setAttribute("qnadetail", detail);
        	request.setAttribute("center", "qnas/qnadetail.jsp");
        	
        	nextPage="/main.jsp";
        	
        	return;
        
        }
        
        
		if(nextPage != null) {
			request.getRequestDispatcher(nextPage).forward(request, response);
		} else {
            System.out.println("nextPage가 null입니다. (아마도 out.print로 직접 응답 처리됨)");
        }
	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request ,response);
	}
}
