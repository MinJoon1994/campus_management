package student.dao;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import student.vo.LectureVO;
import student.vo.SemesterGradeVO;
import student.vo.StudentGradeVO;
import student.vo.StudentSubjectVO;
import student.vo.StudentTimetableVO;
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
		String sql = "SELECT sub.* "
				+ "FROM student s "
				+ "JOIN subject sub ON sub.open_grade = s.grade "
				+ "JOIN professor p ON sub.professor_id = p.user_id "
				+ "WHERE s.user_id = ? "
				+ "  AND p.department = s.department;";
		
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
		
		String sql = "SELECT sub.* "
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
				vo.setSubjectCode(rs.getString("subject_code"));
				
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
	// 특정 학생의 성적 조회
	public List<StudentGradeVO> getGrades(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("student_id");
	    List<StudentGradeVO> list = new ArrayList<>();
	    
	    String sql = "SELECT " +
		    	     "    u.user_id AS student_id, " +
		    	     "    u.name AS student_name, " +
		    	     "    s.subject_code, " +
		    	     "    s.subject_name, " +
		    	     "    g.score, " +
		    	     "    g.grade " +
		    	     "FROM Grade g " +
		    	     "JOIN Enrollment e ON g.enrollment_id = e.enrollment_id " +
		    	     "JOIN Subject s ON e.subject_code = s.subject_code " +
		    	     "JOIN Student st ON e.student_id = st.user_id " +
		    	     "JOIN User u ON st.user_id = u.user_id " +
		    	     "WHERE u.user_id = ?";

	    try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			rs = pstmt.executeQuery();
			
	        while (rs.next()) {
	            StudentGradeVO vo = new StudentGradeVO();
	            vo.setStudentId(rs.getInt("student_id"));        // 학생 ID
	            vo.setStudentName(rs.getString("student_name")); // 학생 이름
	            vo.setSubjectCode(rs.getString("subject_code")); // 과목 코드
	            vo.setSubjectName(rs.getString("subject_name")); // 과목명
	            vo.setScore(rs.getDouble("score"));              // 점수
	            vo.setGrade(rs.getString("grade"));              // 학점

	            list.add(vo);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
	    return list;
	}
	// 특정 학생이 수강중인 과목 조회
	public List<StudentTimetableVO> getTimeTable(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("student_id");
	    List<StudentTimetableVO> list = new ArrayList<>();
		
	    String sql = "SELECT " +
		    	     "    s.subject_code, " +
		    	     "    s.subject_name, " +
		    	     "    s.subject_type, " +
		    	     "    s.open_grade, " +
		    	     "    s.division, " +
		    	     "    s.credit, " +
		    	     "    s.schedule, " +
		    	     "    s.professor_name " +
		    	     "FROM Enrollment e " +
		    	     "JOIN Subject s ON e.subject_code = s.subject_code " +
		    	     "WHERE e.student_id = ?";
	    
	    try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StudentTimetableVO vo = new StudentTimetableVO();
	            vo.setSubjectCode(rs.getString("subject_code"));
	            vo.setSubjectName(rs.getString("subject_name"));
	            vo.setSubjectType(rs.getString("subject_type"));
	            vo.setOpenGrade(rs.getInt("open_grade"));
	            vo.setDivision(rs.getString("division"));
	            vo.setCredit(rs.getInt("credit"));
	            vo.setSchedule(rs.getString("schedule"));
	            vo.setProfessorName(rs.getString("professor_name"));
	            list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		return list;
	}

	public List<StudentSubjectVO> getStudentSubject(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("student_id");
		List<StudentSubjectVO> list = new ArrayList<>();
		
	    String sql = "SELECT s.subject_code, s.subject_name " +
		             "FROM Enrollment e " +
		             "JOIN Subject s ON e.subject_code = s.subject_code " +
		             "WHERE e.student_id = ?";

        try {
        	con = DbcpBean.getConnection();
        	pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	StudentSubjectVO vo = new StudentSubjectVO();
                vo.setSubjectCode(rs.getString("subject_code"));  // 과목 코드 설정
                vo.setSubjectName(rs.getString("subject_name"));  // 과목 이름 설정
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력 (개발 중에는 로깅 권장)
        } finally {
			DbcpBean.close(con, pstmt, rs);
		} 

        return list; 
	}
}