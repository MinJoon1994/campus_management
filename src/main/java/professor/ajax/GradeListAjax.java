//package professor.ajax;
//
//import java.io.IOException;
//import java.util.Vector;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.google.gson.Gson;
//
//import professorservice.ProfessorService;
//import professorvo.GradeVo;
//
//@WebServlet("/GradeListAjax.do")
//public class GradeListAjax extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private ProfessorService professorService = new ProfessorService();
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 응답 타입 설정
//        response.setContentType("application/json; charset=UTF-8");
//
//        // 로그인한 교수 ID
//        HttpSession session = request.getSession(false);
//        String professorId = (String) session.getAttribute("id");
//        if (professorId == null) {
//            response.getWriter().print("[]");
//            return;
//        }
//
//        // 검색 조건 받기
//        String subjectCode = request.getParameter("subject_code");
//        String openGrade = request.getParameter("open_grade");
//        String studentName = request.getParameter("student_name");
//
//        // DB 조회
//        Vector<GradeVo> gradeList = professorService.searchGrades(professorId, subjectCode, openGrade, studentName);
//
//        // JSON으로 변환
//        Gson gson = new Gson();
//        String json = gson.toJson(gradeList);
//        response.getWriter().print(json);
//    }
//}
