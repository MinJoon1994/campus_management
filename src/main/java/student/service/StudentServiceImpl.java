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

    @Override
    public void enroll(HttpServletRequest req) {
        // TODO: 수강신청 구현
    }

    @Override
    public List<LectureVO> getLectureList(HttpServletRequest req) {
        // TODO: 수강목록 조회 구현
        return null;
    }

    @Override
    public void enrollDelete(HttpServletRequest req) {
        // TODO: 수강신청 취소 구현
    }

    // 전체 학기 성적 조회
    @Override
    public List<SemesterGradeVO> getGrades(HttpServletRequest req) {
        List<SemesterGradeVO> gradeList = new ArrayList<>();
        try {
            Integer studentId = (Integer) req.getSession().getAttribute("studentId");
            if (studentId != null) {
                gradeList = studentDAO.getSemesterGrades(studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    // 특정 학기 성적 상세 조회
    @Override
    public List<SubjectGradeVO> getGradesDetail(HttpServletRequest req) {
        List<SubjectGradeVO> subjectGradeList = new ArrayList<>();
        try {
            Integer studentId = (Integer) req.getSession().getAttribute("studentId");
            String semester = req.getParameter("semester");
            if (studentId != null && semester != null) {
                subjectGradeList = studentDAO.getGradesBySemester(studentId, semester);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectGradeList;
    }

    @Override
    public List getTimeTable(HttpServletRequest req) {
        // TODO: 시간표 조회 구현
        return null;
    }

    // 학생 정보 (학번, 학년, 전공, 학적상태 등)
    @Override
    public StudentVO getStudent(HttpServletRequest req) {
        StudentVO studentVO = null;
        try {
            Integer studentId = (Integer) req.getSession().getAttribute("studentId");
            if (studentId != null) {
                studentVO = studentDAO.getStudentInfo(studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentVO;
    }

    // 학생 전체정보 (이름 + 학번 + 전공 + 학년 + 학적상태 등)
    @Override
    public Map<String, Object> getStudentFullInfo(HttpServletRequest req) {
        Map<String, Object> result = null;
        try {
            Integer studentId = (Integer) req.getSession().getAttribute("studentId");
            if (studentId != null) {
                result = studentDAO.getStudentFullInfo(studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 학생이 수강한 학기 목록 조회
    @Override
    public List<String> getSemesterList(HttpServletRequest req) {
        List<String> semesterList = new ArrayList<>();
        try {
            Integer studentId = (Integer) req.getSession().getAttribute("studentId");
            if (studentId != null) {
                semesterList = studentDAO.getSemesterList(studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semesterList;
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
