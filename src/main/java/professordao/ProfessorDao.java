package professordao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import main.DbcpBean;
import professorvo.EnrolledStudentVo;
import professorvo.LectureListVo;
import professorvo.LecturePlanVo;
import professorvo.SubjectVo;

public class ProfessorDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 교수 ID에 해당하는 강의 리스트를 조회
	public Vector<LectureListVo> getAllLectureList(String professorId) {
		Vector<LectureListVo> list = new Vector<>();

		String sql = "SELECT subject_code, subject_name, subject_type, open_grade, division, credit, "
				+ "professor_id, professor_name, schedule, current_enrollment, capacity, is_available " + "FROM subject WHERE professor_id = ?";

		try {
			conn = DbcpBean.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, professorId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				LectureListVo vo = new LectureListVo();
				vo.setSubjectCode(rs.getString("subject_code"));
				vo.setSubjectName(rs.getString("subject_name"));
				vo.setSubjectType(rs.getString("subject_type"));
				vo.setOpenGrade(rs.getInt("open_grade"));
				vo.setDivision(rs.getString("division"));
				vo.setCredit(rs.getInt("credit"));
				vo.setProfessorId(rs.getInt("professor_id"));
				vo.setProfessor(rs.getString("professor_name"));
				vo.setSchedule(rs.getString("schedule"));
				vo.setEnrollment(rs.getString("current_enrollment"));
				vo.setCapacity(rs.getString("capacity"));
				vo.setAvailable(rs.getBoolean("is_available"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs);
		}

		return list;
	}

	// 강의테이블에 등록(is_available은 false상태로 등록)
	public boolean addLectureForm(SubjectVo subjectVo) {
		String sql = "insert into subject (subject_code, subject_name, subject_type, open_grade, division, credit, professor_id, professor_name, schedule, current_enrollment, capacity, is_available) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DbcpBean.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectVo.getSubjectCode());
			pstmt.setString(2, subjectVo.getSubjectName());
			pstmt.setString(3, subjectVo.getSubjectType());
			pstmt.setInt(4, subjectVo.getOpenGrade());
			pstmt.setString(5, subjectVo.getDivision());
			pstmt.setInt(6, subjectVo.getCapacity());
			pstmt.setInt(7, subjectVo.getProfessorId());
			pstmt.setString(8, subjectVo.getProfessorName());
			pstmt.setString(9, subjectVo.getSchedule());
			pstmt.setInt(10, subjectVo.getCurrentEnrollment());
			pstmt.setInt(11, subjectVo.getCapacity());
			pstmt.setBoolean(12, subjectVo.getisAvailable());
			
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (Exception e) {
		}
		return false;
	}
	// 강의계획서 등록
	public boolean addLecturePlan(LecturePlanVo planvo) {
		String sql = "INSERT INTO lecture_plan (subject_code, subject_name, professor_id, professor_name, lecture_period, target_students, main_content, goal, method, content, evaluation) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			conn = DbcpBean.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, planvo.getSubjectCode());
			pstmt.setString(2, planvo.getSubjectName());
			pstmt.setString(3, planvo.getProfessorId());
			pstmt.setString(4, planvo.getProfessorName());
			pstmt.setString(5, planvo.getLecturePeriod());
			pstmt.setString(6, planvo.getTargetStudents());
			pstmt.setString(7, planvo.getMainContent());
			pstmt.setString(8, planvo.getGoal());
			pstmt.setString(9, planvo.getMethod());
			pstmt.setString(10, planvo.getContent());
			pstmt.setString(11, planvo.getEvaluation());

			int result = pstmt.executeUpdate(); // 성공 = 1, 실패 = 0
			return result > 0; // 성공적으로 삽입되었으면 true 반환
		} catch (Exception e) {
			System.out.println("강의계획 추가 중 오류 발생");
			e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt);
		}
		return false;
	}
	// 강의계획서 수정
	public boolean updateLacturePlan(LecturePlanVo planvo) {
	    String sql = "UPDATE lecture_plan SET "
	               + "lecture_period = ?, "
	               + "target_students = ?, "
	               + "main_content = ?, "
	               + "goal = ?, "
	               + "method = ?, "
	               + "content = ?, "
	               + "evaluation = ? "
	               + "WHERE professor_name = ?";

	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, planvo.getLecturePeriod());
	        pstmt.setString(2, planvo.getTargetStudents());
	        pstmt.setString(3, planvo.getMainContent());
	        pstmt.setString(4, planvo.getGoal());
	        pstmt.setString(5, planvo.getMethod());
	        pstmt.setString(6, planvo.getContent());
	        pstmt.setString(7, planvo.getEvaluation());
	        pstmt.setString(8, planvo.getProfessorName());

	        int result = pstmt.executeUpdate();
	        return result > 0;
	    } catch (Exception e) {
	        System.out.println("강의계획서 수정 중 오류 발생");
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt);
	    }

	    return false;
	}
	// 강의계획서 삭제
	public boolean deleteLecturePlan(String id) {
		String sql = "delete from lecture_plan where professor_id = ?";
		
		try {
			conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, id);

	        int result = pstmt.executeUpdate();
	        return result > 0;
		} catch (Exception e) {
			System.out.println("강의계획서 삭제 중 오류 발생");
	        e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt);
		}
		return false;
	}
	// 강의계획서 조회
	public LecturePlanVo getAllLecturePlanList(String subjectCode) {
		LecturePlanVo lecturePlanVo = null;
		String sql = "select * from lecture_plan where subject_code = ?";
		
		try {
			conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, subjectCode);
	        
	        rs = pstmt.executeQuery();
	        if(rs.next()) {
		        lecturePlanVo = new LecturePlanVo();
	            lecturePlanVo.setSubjectCode(rs.getString("subject_code"));
	            lecturePlanVo.setSubjectName(rs.getString("subject_name"));
	            lecturePlanVo.setProfessorId(rs.getString("professor_id"));
	            lecturePlanVo.setProfessorName(rs.getString("professor_name"));
	            lecturePlanVo.setLecturePeriod(rs.getString("lecture_period"));
	            lecturePlanVo.setTargetStudents(rs.getString("target_students"));
	            lecturePlanVo.setMainContent(rs.getString("main_content"));
	            lecturePlanVo.setGoal(rs.getString("goal"));
	            lecturePlanVo.setMethod(rs.getString("method"));
	            lecturePlanVo.setContent(rs.getString("content"));
	            lecturePlanVo.setEvaluation(rs.getString("evaluation"));	        	
	        }
		} catch (Exception e) {
			System.out.println("강의계획서 조회 중 오류 발생");
	        e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs);
		}
		return lecturePlanVo;
	}
	// 수강신청 학생명단 확인
	public Vector<EnrolledStudentVo> getAllEnrolledStudentList(String professor_id) {
	    Vector<EnrolledStudentVo> enrolledStudent_list = new Vector<>();
	    String sql = "SELECT " +
	            "p.user_id AS professor_id, " +
	            "p.professor_number, " +
	            "s.subject_code, " +
	            "s.subject_name, " +
	            "stu.user_id AS student_id, " +
	            "stu.name AS student_name, " +
	            "st.student_number, " +
	            "st.department " +
	            "FROM Professor p " +
	            "JOIN Subject s ON p.user_id = s.professor_id " +
	            "JOIN Enrollment e ON s.subject_code = e.subject_code " +
	            "JOIN Student st ON e.student_id = st.user_id " +
	            "JOIN User stu ON st.user_id = stu.user_id " +
	            "WHERE p.user_id = ?";

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbcpBean.getConnection(); // 커넥션 얻기
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, professor_id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            EnrolledStudentVo enrolledStudentVo = new EnrolledStudentVo();
	            enrolledStudentVo.setProfessorId(rs.getInt("professor_id"));
	            enrolledStudentVo.setProfessorNumber(rs.getString("professor_number")); // 수정된 부분
	            enrolledStudentVo.setSubjectCode(rs.getString("subject_code"));
	            enrolledStudentVo.setSubjectName(rs.getString("subject_name"));
	            enrolledStudentVo.setStudentId(rs.getInt("student_id"));
	            enrolledStudentVo.setStudentName(rs.getString("student_name"));
	            enrolledStudentVo.setStudentNumber(rs.getString("student_number"));
	            enrolledStudentVo.setDepartment(rs.getString("department"));

	            enrolledStudent_list.add(enrolledStudentVo);
	        }

	    } catch (Exception e) {
	        System.out.println("수강신청 학생명단 조회 중 오류 발생");
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt, rs);
	    }

	    return enrolledStudent_list;
	}
	// 강의 테이블 조회
	public Vector<SubjectVo> getAllSubject(String id) {
	    Vector<SubjectVo> subjectList = new Vector<>();
	    String sql = "SELECT subject_code, subject_name, subject_type, open_grade, division, credit, " +
	                 "professor_id, professor_name, schedule, current_enrollment, capacity, is_available " +
	                 "FROM Subject WHERE professor_id = ?";

	    try {
	        conn = DbcpBean.getConnection(); // 커넥션 풀에서 커넥션 얻기
	        pstmt = conn.prepareStatement(sql);
	        
	        int professorId = Integer.parseInt(id); 
	        pstmt.setInt(1, professorId); // 교수 ID 파라미터 설정
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            SubjectVo subjectVo = new SubjectVo();
	            subjectVo.setSubjectCode(rs.getString("subject_code"));
	            subjectVo.setSubjectName(rs.getString("subject_name"));
	            subjectVo.setSubjectType(rs.getString("subject_type"));
	            subjectVo.setOpenGrade(rs.getInt("open_grade"));
	            subjectVo.setDivision(rs.getString("division"));
	            subjectVo.setCredit(rs.getInt("credit"));
	            subjectVo.setProfessorId(rs.getInt("professor_id"));
	            subjectVo.setProfessorName(rs.getString("professor_name"));
	            subjectVo.setSchedule(rs.getString("schedule"));
	            subjectVo.setCurrentEnrollment(rs.getInt("current_enrollment"));
	            subjectVo.setCapacity(rs.getInt("capacity"));
	            subjectVo.setAvailable(rs.getBoolean("is_available"));
	            subjectList.add(subjectVo); // 과목 정보를 리스트에 추가
	        }
	    } catch (Exception e) {
	        System.out.println("수정되지 않은 과목 리스트 조회 중 오류 발생");
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt, rs); 
	    }

	    return subjectList;
	}
}
