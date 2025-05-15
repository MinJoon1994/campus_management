package qna.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.vo.UserVO;
import qna.service.QnaService;
import qna.service.QnaServiceImpl;
import qna.vo.QnaVO;

@WebServlet("/qna/*")
public class QnaController extends HttpServlet {

    protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String action = req.getPathInfo();
        String nextPage = null;
        PrintWriter out = resp.getWriter();

        QnaService qnaService = new QnaServiceImpl();

        if (action.equals("/list")) { // 질문글 목록 조회 요청

            UserVO vo = (UserVO) req.getSession().getAttribute("vo");
            int studentId = vo.getId();

            List<QnaVO> list = qnaService.getQnaList(studentId);
            req.setAttribute("list", list);
            req.setAttribute("center", "qnas/qnalist.jsp");
            nextPage = "/main.jsp";

        } else if (action.equals("/detail")) { // 특정 질문글 조회 요청

            int qnaId = Integer.parseInt(req.getParameter("qna_id"));
            QnaVO qna = qnaService.getQnaDetail(qnaId);

            req.setAttribute("qna", qna);
            req.setAttribute("center", "qnas/qnadetail.jsp");
            nextPage = "/main.jsp";

        }

        if (nextPage != null) {
            req.getRequestDispatcher(nextPage).forward(req, resp);
        } else {
            System.out.println("nextPage가 null입니다. (아마도 out.print로 직접 응답 처리됨)");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }
}
