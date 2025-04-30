	package main.controller;
	
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.List;
	
	import javax.servlet.RequestDispatcher;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	
	import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	
	import main.dao.MainDAO;
	import main.service.MainService;
	import main.service.MainServiceImpl;
	import main.vo.AcademicCalendarVO;
	import main.vo.NoticeVO;
	import main.vo.QnaVO;
	
	@WebServlet("/campus/*")
	public class MainController extends HttpServlet{
		
		
		protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//기본세팅
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			String action = request.getPathInfo();
			String nextPage = null;
			
			HttpSession session = request.getSession();
			
			MainService mainService = new MainServiceImpl();
			
			//1.메인화면 페이지
	        if (action.equals("/main")) {
	        	
	            System.out.println("/main 요청 처리 시작");
	            
	            nextPage = "/main.jsp";
	            
	        }
	        //2.학사일정 달력 AJAX요청
	        else if (action.equals("/calendarEvent")) {
	        	
	            System.out.println("/calendarEvent 요청 처리 시작");
	
	            // 학사일정 DB에서 조회
	            List<AcademicCalendarVO> calendarList = mainService.getAllEvents(); // 서비스/DAO 구현 필요
	
	            // JSON으로 변환해서 보내기
	            JSONArray jsonArray = new JSONArray();
	            
	            // JSON 배열에 학사일정 객체 추가
	            for (AcademicCalendarVO event : calendarList) {
	                JSONObject obj = new JSONObject();
	                obj.put("id", event.getCalendarId());
	                obj.put("title", event.getTitle());
	                obj.put("start", event.getStart().toString()); // 날짜 형식에 맞게 변환 필요
	                if (event.getEnd() != null) {
	                    obj.put("end", event.getEnd().toString());
	                }
	                obj.put("description", event.getDescription());
	                obj.put("color", event.getColor());
	                jsonArray.add(obj);
	            }
	
	            out.print(jsonArray.toString()); // JSON 응답
	            return; // 바로 종료
	        }
	        //공지사항
	        else if (action.equals("/noticePage")) {

	            int page = Integer.parseInt(request.getParameter("noticepage"));
	            int pageSize = 5;
	
	            List<NoticeVO> noticeList = mainService.getAllNotices(page, pageSize);
	
	            JSONArray jsonArray = new JSONArray();
	            for (NoticeVO vo : noticeList) {
	                JSONObject obj = new JSONObject();
	                obj.put("noticeId", vo.getNoticeId());
	                obj.put("title", vo.getTitle());
	                obj.put("content", vo.getContent());
	                obj.put("createdAt", vo.getCreatedAt().toString());
	                jsonArray.add(obj);
	            }
	            
	            JSONObject result = new JSONObject();
	            result.put("noticeList", jsonArray);
	            result.put("currentPage", page);
	            result.put("totalPage", (int)Math.ceil((double)mainService.getNoticeCount() / pageSize)); // ← 총 페이지 수 계산 필요
	            out.print(result.toString());
	            return;
	        }
	        //qna
	        else if (action.equals("/qnaPage")) {
	        	
	        	int page = Integer.parseInt(request.getParameter("qnapage"));
	        	int pageSize = 5;
	        	
	        	List<QnaVO> qnaList = mainService.getAllQnas(page, pageSize);
	        	
	        	JSONArray jsonArray = new JSONArray();
	        	for (QnaVO vo : qnaList) {
	        		JSONObject obj = new JSONObject();
	        		obj.put("qnaId", vo.getQnaId());
	        		obj.put("title", vo.getTitle());
	        		obj.put("questioner", vo.getQuestioner_name());
	        		obj.put("questiontime", vo.getQuestionTime().toString());
	        		jsonArray.add(obj);
	        	}
	        	
	            JSONObject result = new JSONObject();
	            result.put("qnaList", jsonArray);
	            result.put("currentPage", page);
	            result.put("totalPage", (int)Math.ceil((double)mainService.getQnaCount() / pageSize)); // ← 총 페이지 수 계산 필요
	            out.print(result.toString());
	        	return;
	        }
	        
	        if (nextPage != null) {
	            System.out.println("페이지 이동(forward) 처리: " + nextPage);
	            RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
	            dispatch.forward(request, response);
	        } else {
	            System.out.println("nextPage가 null입니다. (아마도 out.print로 직접 응답 처리됨)");
	        }//if문 끝
	        
		}//doHandle() 끝
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doHandle(req,resp);
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doHandle(req,resp);
		}
	}