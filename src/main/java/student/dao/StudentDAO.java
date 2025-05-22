package student.dao;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import student.vo.LectureVO;
import student.vo.SemesterGradeVO;
import student.vo.SubjectGradeVO;
import main.DbcpBean;   
import member.vo.StudentVO;

public class StudentDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
    //학생이 수강신청 가능한 목록 조회
	public List<LectureVO> getList(HttpServletRequest req) {
		
		//학생이 수강신청 가능한 목록 조회
		//학생 아이디
		Integer student_id = (Integer)req.getSession().getAttribute("student_id");
		
		System.out.println("학생 아이디 :"+student_id);
		
		//학생 아이디를 이용해 학생 학년에 맞는 수강 신청 가능한 과목 조회
		String sql = 
			    "SELECT sub.* " +
			    "FROM student s " +
			    "JOIN subject sub ON sub.open_grade = s.grade " +
			    "JOIN professor p ON sub.professor_id = p.user_id " +
			    "WHERE s.user_id = ? " +
			    "  AND p.department = s.department " +
			    "  AND NOT EXISTS ( " +
			    "      SELECT 1 " +
			    "      FROM enrollment e " +
			    "      WHERE e.student_id = s.user_id " +
			    "        AND e.subject_code = sub.subject_code " +
			    "  );";
		
		List<LectureVO> list = new ArrayList<>();
		
		try {
			
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, student_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				if(rs.getInt("is_available")==1) {
					
					LectureVO vo = new LectureVO();
					vo.setSubjectCode(rs.getString("subject_code"));
					vo.setSubjectName(rs.getString("subject_name"));
					vo.setSubjectType(rs.getString("subject_type"));
					vo.setOpenGrade(rs.getInt("open_grade"));
					vo.setDivision(rs.getString("division"));
					vo.setCredit(rs.getInt("credit"));
					vo.setProfessorName(rs.getString("professor_name"));
					vo.setSchedule(rs.getString("schedule"));
					vo.setCurrentEnrollment(rs.getInt("current_enrollment"));
					vo.setCapacity(rs.getInt("capacity"));

					list.add(vo);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return list;
	}
	
	//학생 수강 신청 요청
	public void enroll(String subject_code, int student_id) {
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "INSERT INTO enrollment (subject_code, student_id,enrolled_at) VALUES (?, ?, NOW())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subject_code);
			pstmt.setInt(2, student_id);
			pstmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt);
		}
		
	}
	
	//학생 수강신청 목록 확인
	public List<LectureVO> getLectureList(Integer student_id) {
		
		List<LectureVO> list = new ArrayList<>();
		
		String sql = "SELECT e.enrollment_id, sub.* "
				+ "FROM enrollment e "
				+ "JOIN subject sub ON e.subject_code = sub.subject_code "
				+ "WHERE e.student_id = ? ";
		
		try {
			
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, student_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				LectureVO vo = new LectureVO();
				vo.setEnrollmentId(rs.getString("enrollment_id"));
				vo.setSubjectCode(rs.getString("subject_code"));
				vo.setSubjectName(rs.getString("subject_name"));
				vo.setSubjectType(rs.getString("subject_type"));
				vo.setOpenGrade(rs.getInt("open_grade"));
				vo.setDivision(rs.getString("division"));
				vo.setCredit(rs.getInt("credit"));
				vo.setProfessorName(rs.getString("professor_name"));
				vo.setSchedule(rs.getString("schedule"));
				vo.setCurrentEnrollment(rs.getInt("current_enrollment"));
				vo.setCapacity(rs.getInt("capacity"));
				
				list.add(vo);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return list;
		
	}

	public int checkCapacity(HttpServletRequest req) {
		
		// 수강 신청할 과목의 정원보다 현재 인원이 적은지 확인
		String subject_code = req.getParameter("subject_code");
		
		String sql = "SELECT capacity, current_enrollment "
				+ "FROM subject "
				+ "WHERE subject_code = ?";
		
		int result = 1;
		
		try {
			
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subject_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int capacity = rs.getInt("capacity");
				int current_enrollment = rs.getInt("current_enrollment");
				
				if(current_enrollment >= capacity) {
					System.out.println("정원이 초과되었습니다.");
					result = 0;
				}
					
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return result;
		
	}
	
	//수강신청후 수강 현재인원수 +1
	public void updateCurrentEnrollment(String subject_code) {
		
		try {
			con = DbcpBean.getConnection();
			String sql = "UPDATE subject SET current_enrollment = current_enrollment + 1 WHERE subject_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subject_code);
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			DbcpBean.close(con, pstmt);
		}
	}
	
	//학생수강신청 취소
	public void enrollDelete(HttpServletRequest req, String subject_code, int student_id, int enrollment_id) {
	    try {
	        con = DbcpBean.getConnection();

	        // 🔹 1단계: grade 테이블에서 해당 enrollment_id 먼저 삭제
	        String sql = "DELETE FROM grade WHERE enrollment_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, enrollment_id);
	        pstmt.executeUpdate();
	        pstmt.close();

	        // 🔹 2단계: enrollment 삭제
	        sql = "DELETE FROM enrollment WHERE subject_code = ? AND student_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, subject_code);
	        pstmt.setInt(2, student_id);
	        pstmt.executeUpdate();
	        pstmt.close();

	        // 🔹 3단계: subject 인원 -1
	        sql = "UPDATE subject SET current_enrollment = current_enrollment - 1 WHERE subject_code = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, subject_code);
	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt);
	    }
	}

}