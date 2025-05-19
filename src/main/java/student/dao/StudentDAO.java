package student.dao;

import java.sql.*;
import java.util.*;

import student.vo.LectureVO;
import student.vo.SemesterGradeVO;
import student.vo.SubjectGradeVO;
import main.DbcpBean;   
import member.vo.StudentVO;

public class StudentDAO {
	
	// student.dao.StudentDAO

	// 수강신청 (등록)
	public int enroll(int studentId, String subjectCode) throws Exception {
	    String sql = "INSERT INTO Enrollment (student_id, subject_code) VALUES (?, ?)";
	    try (Connection conn = DbcpBean.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, studentId);
	        ps.setString(2, subjectCode);
	        return ps.executeUpdate();
	    }
	}

	// 수강신청 목록 조회
	public List<student.vo.LectureVO> getEnrolledLectures(int studentId) throws Exception {
	    List<student.vo.LectureVO> list = new ArrayList<>();
	    String sql = "SELECT e.enrollment_id, s.subject_code, s.subject_name, s.professor_name, s.credit, s.open_grade " +
	                 "FROM Enrollment e " +
	                 "JOIN Subject s ON e.subject_code = s.subject_code " +
	                 "WHERE e.student_id = ? " +
	                 "ORDER BY s.open_grade DESC, s.subject_name";
	    try (Connection conn = DbcpBean.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, studentId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                student.vo.LectureVO vo = new student.vo.LectureVO();
	                vo.setEnrollmentId(rs.getInt("enrollment_id"));
	                vo.setSubjectCode(rs.getString("subject_code"));
	                vo.setSubjectName(rs.getString("subject_name"));
	                vo.setProfessorName(rs.getString("professor_name"));
	                vo.setCredit(rs.getInt("credit"));
	                vo.setOpenGrade(rs.getString("open_grade"));
	                list.add(vo);
	            }
	        }
	    }
	    return list;
	}

	// 수강신청 취소
	public int deleteEnrollment(int studentId, int enrollmentId) throws Exception {
	    String sql = "DELETE FROM Enrollment WHERE enrollment_id = ? AND student_id = ?";
	    try (Connection conn = DbcpBean.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, enrollmentId);
	        ps.setInt(2, studentId);
	        return ps.executeUpdate();
	    }
	}


    // 학기별 성적 리스트 조회
    public List<SemesterGradeVO> getSemesterGrades(int studentId) throws Exception {
        String sql = 
            "SELECT s.open_grade AS semester, " +
            "       COUNT(e.enrollment_id) AS subject_count, " +
            "       SUM(s.credit) AS total_credits, " +
            "       ROUND(AVG(g.score), 2) AS avg_score " +
            "FROM Enrollment e " +
            "JOIN Subject s ON e.subject_code = s.subject_code " +
            "LEFT JOIN Grade g ON e.enrollment_id = g.enrollment_id " +
            "WHERE e.student_id = ? " +
            "GROUP BY s.open_grade " +
            "ORDER BY s.open_grade DESC";
        List<SemesterGradeVO> list = new ArrayList<>();
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SemesterGradeVO vo = new SemesterGradeVO();
                    vo.setSemester(rs.getString("semester"));
                    vo.setSubjectCount(rs.getInt("subject_count"));
                    vo.setTotalCredit(rs.getInt("total_credits"));
                    vo.setAverageScore(rs.getDouble("avg_score"));
                    list.add(vo);
                }
            }
        }
        return list;
    }

    // 특정 학기 과목별 성적 상세 조회
    public List<SubjectGradeVO> getGradesBySemester(int studentId, String semester) throws Exception {
        String sql =
            "SELECT s.subject_name, s.professor_name, s.credit, g.score, g.grade " +
            "FROM Enrollment e " +
            "JOIN Subject s ON e.subject_code = s.subject_code " +
            "LEFT JOIN Grade g ON e.enrollment_id = g.enrollment_id " +
            "WHERE e.student_id = ? AND s.open_grade = ? " +
            "ORDER BY s.subject_name";
        List<SubjectGradeVO> list = new ArrayList<>();
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, semester);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubjectGradeVO vo = new SubjectGradeVO();
                    vo.setSubjectName(rs.getString("subject_name"));
                    vo.setProfessorName(rs.getString("professor_name"));
                    vo.setCredit(rs.getInt("credit"));
                    // 성적(score)이 없을 수도 있으므로, null 체크
                    Object scoreObj = rs.getObject("score");
                    vo.setScore(scoreObj == null ? null : rs.getDouble("score"));
                    vo.setGrade(rs.getString("grade"));
                    list.add(vo);
                }
            }
        }
        return list;
    }
    
    public StudentVO getStudentInfo(int studentId) throws Exception {
        StudentVO vo = null;
        String sql = "SELECT u.name, s.student_number, s.grade, s.department FROM User u JOIN Student s ON u.user_id = s.user_id WHERE s.user_id = ?";
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vo = new StudentVO();
                    vo.setStudent_id("student_id");
                    vo.setDepartment("department");
                    vo.setGrade(rs.getString("grade"));
                    vo.setStatus(rs.getString("status"));
                }
            }
        }
        return vo;
    }
    
 // 이름 + 학번 + 전공 + 학년 조회 (User, Student JOIN)
    public Map<String, Object> getStudentFullInfo(int studentId) throws Exception {
        Map<String, Object> result = new HashMap<>();

        String sql = "SELECT u.name, s.student_id, s.department, s.grade, s.status " +
                     "FROM User u JOIN Student s ON u.user_id = s.user_id WHERE s.user_id = ?";
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // UserVO
                    member.vo.UserVO userVO = new member.vo.UserVO();
                    userVO.setName(rs.getString("name"));
                    // StudentVO
                    member.vo.StudentVO studentVO = new member.vo.StudentVO();
                    studentVO.setStudent_id(rs.getString("student_id"));
                    studentVO.setDepartment(rs.getString("department"));
                    studentVO.setGrade(rs.getString("grade"));
                    studentVO.setStatus(rs.getString("status"));

                    result.put("userVO", userVO);
                    result.put("studentVO", studentVO);
                }
            }
        }
        return result;
    }
    
    //학생 수강 학기 리스트
    public List<String> getSemesterList(int studentId) throws Exception {
        List<String> semesterList = new ArrayList<>();
        String sql = "SELECT DISTINCT s.open_grade " +
                     "FROM Enrollment e " +
                     "JOIN Subject s ON e.subject_code = s.subject_code " +
                     "WHERE e.student_id = ? ORDER BY s.open_grade DESC";
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    semesterList.add(rs.getString("open_grade"));
                }
            }
        }
        return semesterList;
    }
    
 // 학생이 신청 안 한(수강 안 한) 과목 목록
    public List<LectureVO> getAvailableLectures(int studentId) throws Exception {
        List<LectureVO> list = new ArrayList<>();
        String sql = "SELECT s.subject_code, s.subject_name, s.professor_name, s.credit, s.open_grade " +
                     "FROM Subject s " +
                     "WHERE s.subject_code NOT IN (SELECT e.subject_code FROM Enrollment e WHERE e.student_id = ?) " +
                     "ORDER BY s.open_grade DESC, s.subject_name";
        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LectureVO vo = new LectureVO();
                    vo.setSubjectCode(rs.getString("subject_code"));
                    vo.setSubjectName(rs.getString("subject_name"));
                    vo.setProfessorName(rs.getString("professor_name"));
                    vo.setCredit(rs.getInt("credit"));
                    vo.setOpenGrade(rs.getString("open_grade"));
                    list.add(vo);
                }
            }
        }
        return list;
    }
}