package student.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import main.DbcpBean;
import member.vo.StudentVO;
import student.vo.LectureVO;

public class StudentServiceImpl implements StudentService {

	@Override
	public void enroll(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    String studentId = (String) session.getAttribute("studentId");  // 세션에서 꺼냄
	    int lectureId = Integer.parseInt(req.getParameter("lecture_id"));

	    Connection conn = null;
	    PreparedStatement checkStmt = null;
	    PreparedStatement insertStmt = null;
	    PreparedStatement updateStmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbcpBean.getConnection();

	        // 1. 중복 수강신청 체크
	        String checkSql = "SELECT COUNT(*) FROM course_registration WHERE student_id = ? AND lecture_id = ?";
	        checkStmt = conn.prepareStatement(checkSql);
	        checkStmt.setString(1, studentId);
	        checkStmt.setInt(2, lectureId);
	        rs = checkStmt.executeQuery();

	        if (rs.next() && rs.getInt(1) > 0) {
	            throw new Exception("이미 신청한 강의입니다.");
	        }

	        // 2. 정원 초과 체크
	        String capacitySql = "SELECT max_students, current_students FROM lecture WHERE lecture_id = ?";
	        PreparedStatement capacityStmt = conn.prepareStatement(capacitySql);
	        capacityStmt.setInt(1, lectureId);
	        ResultSet capacityRs = capacityStmt.executeQuery();
	        if (capacityRs.next()) {
	            int max = capacityRs.getInt("max_students");
	            int current = capacityRs.getInt("current_students");
	            if (current >= max) {
	                throw new Exception("정원이 초과된 강의입니다.");
	            }
	        }

	        // 3. 수강 신청 INSERT
	        String insertSql = "INSERT INTO course_registration (student_id, lecture_id) VALUES (?, ?)";
	        insertStmt = conn.prepareStatement(insertSql);
	        insertStmt.setString(1, studentId);
	        insertStmt.setInt(2, lectureId);
	        insertStmt.executeUpdate();

	        // 4. 수강 인원 증가
	        String updateSql = "UPDATE lecture SET current_students = current_students + 1 WHERE lecture_id = ?";
	        updateStmt = conn.prepareStatement(updateSql);
	        updateStmt.setInt(1, lectureId);
	        updateStmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	        req.setAttribute("errorMessage", e.getMessage());
	    } finally {
	        DbcpBean.close(rs);
	        DbcpBean.close(checkStmt);
	        DbcpBean.close(insertStmt);
	        DbcpBean.close(updateStmt);
	        DbcpBean.close(conn);
	    }
	}


	@Override
	public List<LectureVO> getLectureList(HttpServletRequest req) {
	    List<LectureVO> lectureList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbcpBean.getConnection();
	        String sql = "SELECT lecture_id, subject_name, professor_name, schedule, credit, max_students, current_students FROM lecture";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            LectureVO lecture = new LectureVO();
	            lecture.setLecture_id(rs.getInt("lecture_id"));
	            lecture.setSubject_name(rs.getString("subject_name"));
	            lecture.setProfessor_name(rs.getString("professor_name"));
	            lecture.setSchedule(rs.getString("schedule"));
	            lecture.setCredit(rs.getInt("credit"));
	            lecture.setMax_students(rs.getInt("max_students"));
	            lecture.setCurrent_students(rs.getInt("current_students"));
	            lectureList.add(lecture);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt, rs);
	    }

	    return lectureList;
	}
	
	@Override
	public List<LectureVO> getRegisteredLectures(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    String studentId = (String) session.getAttribute("studentId");

	    List<LectureVO> lectureList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbcpBean.getConnection();
	        String sql = "SELECT l.lecture_id, l.subject_name, l.professor_name, l.schedule, " +
	                     "l.credit, l.max_students, l.current_students, cr.registered_at " +
	                     "FROM course_registration cr " +
	                     "JOIN lecture l ON cr.lecture_id = l.lecture_id " +
	                     "WHERE cr.student_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, studentId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            LectureVO lecture = new LectureVO();
	            lecture.setLecture_id(rs.getInt("lecture_id"));
	            lecture.setSubject_name(rs.getString("subject_name"));
	            lecture.setProfessor_name(rs.getString("professor_name"));
	            lecture.setSchedule(rs.getString("schedule"));
	            lecture.setCredit(rs.getInt("credit"));
	            lecture.setMax_students(rs.getInt("max_students"));
	            lecture.setCurrent_students(rs.getInt("current_students"));
	            lectureList.add(lecture);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt, rs);
	    }

	    return lectureList;
	}


	@Override
	public void enrollDelete(HttpServletRequest req) {
		HttpSession session = req.getSession();
	    String studentId = (String) session.getAttribute("studentId");
	    int lectureId = Integer.parseInt(req.getParameter("lecture_id"));

	    Connection conn = null;
	    PreparedStatement deleteStmt = null;
	    PreparedStatement updateStmt = null;

	    try {
	        conn = DbcpBean.getConnection();

	        // 1. 수강신청 삭제
	        String deleteSql = "DELETE FROM course_registration WHERE student_id = ? AND lecture_id = ?";
	        deleteStmt = conn.prepareStatement(deleteSql);
	        deleteStmt.setString(1, studentId);
	        deleteStmt.setInt(2, lectureId);
	        deleteStmt.executeUpdate();

	        // 2. 현재 수강 인원 감소
	        String updateSql = "UPDATE lecture SET current_students = current_students - 1 WHERE lecture_id = ?";
	        updateStmt = conn.prepareStatement(updateSql);
	        updateStmt.setInt(1, lectureId);
	        updateStmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(deleteStmt);
	        DbcpBean.close(updateStmt);
	        DbcpBean.close(conn);
	    }
		
	}

	@Override
	public List getGrades(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getGradesDetail(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getTimeTable(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public StudentVO getStudent(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    String userId = (String) session.getAttribute("userId");

	    StudentVO student = null;

	    try {
	        Connection conn = DbcpBean.getConnection(); // 프로젝트에 맞게 수정
	        String sql = "SELECT s.student_id, s.department, s.grade, s.status, " +
	                     "u.name, d.department_name " +
	                     "FROM student s " +
	                     "JOIN user u ON s.user_id = u.user_id " +
	                     "JOIN departmentMajor d ON s.department = d.id " +
	                     "WHERE s.user_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            student = new StudentVO();
	            student.setStudent_id(rs.getString("student_id"));
	            student.setDepartment(rs.getString("department"));
	            student.setGrade(rs.getString("grade"));
	            student.setStatus(rs.getString("status"));

	            // 추가된 출력용 필드
	            student.setName(rs.getString("name"));
	            student.setDepartmentName(rs.getString("department_name"));
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return student;
	}

	

	@Override
	public void updateStudent(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qnaClass(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qnaCampus(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}
	
	


}
