package student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import member.vo.StudentVO;
import student.dao.StudentDAO;
import student.vo.LectureVO;
import student.vo.SemesterGradeVO;
import student.vo.SubjectGradeVO;

public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO = new StudentDAO();

    //학생이 수강신청 가능한 목록 조회
    @Override
    public List<LectureVO> getList(HttpServletRequest req) {
    
    	return studentDAO.getList(req);
    }
    
    //학생 수강 신청 요청
    @Override
    public int enroll(HttpServletRequest req) {
    	
    	//수강 신청할 과목의 정원보다 현재 인원이 적은지 확인
    	int result = studentDAO.checkCapacity(req);
    	
    	if(result == 1) {
        	String subject_code = req.getParameter("subjectCode");
        	
        	//학생 아이디
        	Integer student_id = (Integer)req.getSession().getAttribute("id");
        	
        	studentDAO.enroll(subject_code,student_id);
        	
        	//수강신청후 수강현재인원수 변경
        	studentDAO.updateCurrentEnrollment(subject_code);
    	}
    	
    	return result;

    }
    
    @Override
    public List<LectureVO> getLectureList(HttpServletRequest req) {
        
    	// 학생이 수강신청 가능한 목록 조회
    	Integer student_id = (Integer)req.getSession().getAttribute("id");
    	
        return studentDAO.getLectureList(student_id);
    }

    @Override
    public void enrollDelete(HttpServletRequest req) {
    	
    	int enrollment_id = Integer.parseInt(req.getParameter("enrollmentId"));
    	String subject_code = req.getParameter("subjectCode");
    	int id = (Integer)req.getSession().getAttribute("id");
    	
    	studentDAO.enrollDelete(req,subject_code,id,enrollment_id);
    	
    }

    // 전체 학기 성적 조회
    @Override
    public List<SemesterGradeVO> getGrades(HttpServletRequest req) {
    	return null;
    }

    // 특정 학기 성적 상세 조회
    @Override
    public List<SubjectGradeVO> getGradesDetail(HttpServletRequest req) {

        return null;
    }

    @Override
    public List getTimeTable(HttpServletRequest req) {

        return null;
    }

    // 학생 정보 (학번, 학년, 전공, 학적상태 등)
    @Override
    public StudentVO getStudent(HttpServletRequest req) {

        return null;
    }

    // 학생 전체정보 (이름 + 학번 + 전공 + 학년 + 학적상태 등)
    @Override
    public Map<String, Object> getStudentFullInfo(HttpServletRequest req) {

        return null;
    }

    // 학생이 수강한 학기 목록 조회
    @Override
    public List<String> getSemesterList(HttpServletRequest req) {

        return null;
    }

    @Override
    public void updateStudent(HttpServletRequest req) {
        // TODO: 개인정보 수정 구현
    }

    @Override
    public void qnaClass(HttpServletRequest req) {
        // TODO: 강의관련 질문글 등록 구현
    }

    @Override
    public void qnaCampus(HttpServletRequest req) {
        // TODO: 학교관련 질문글 등록 구현
    }
}
