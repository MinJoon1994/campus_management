package student.dao;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import student.vo.LectureVO;
import student.vo.SemesterGradeVO;
import student.vo.StudentGradeVO;
import student.vo.StudentQnaListVO;
import student.vo.StudentQnaWithRelpyVO;
import student.vo.StudentQusetionVO;
import student.vo.StudentSubjectVO;
import student.vo.StudentTimetableVO;
import student.vo.SubjectGradeVO;
import main.DbcpBean;   
import member.vo.StudentVO;
import member.vo.UserVO;

public class StudentDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
    //학생이 수강신청 가능한 목록 조회
	public List<LectureVO> getList(HttpServletRequest req) {
		
		//학생이 수강신청 가능한 목록 조회
		//학생 아이디
		Integer student_id = (Integer)req.getSession().getAttribute("id");
		
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

	// 특정 학생의 성적 조회
	public List<StudentGradeVO> getGrades(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("id");
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
	    int studentId = (int) session.getAttribute("id");
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
	// 학생이 수강중인 과목 목록 조회
	public List<StudentSubjectVO> getStudentSubject(HttpServletRequest req) {
		HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("id");
		List<StudentSubjectVO> list = new ArrayList<>();
		
	    String sql = "SELECT s.subject_code, s.subject_name " +
		             "FROM Enrollment e " +
		             "JOIN Subject s ON e.subject_code = s.subject_code " +
		             "Where e.student_id = ?";

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
            e.printStackTrace(); 
        } finally {
			DbcpBean.close(con, pstmt, rs);
		} 

        return list; 
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

	// 교수, 학생 질문 테이블 조회
	public List<StudentQnaListVO> getStudentQna(HttpServletRequest req) {
		HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("id");
		List<StudentQnaListVO> list = new ArrayList<>();
		
		String sql = "SELECT " +
			         "    q.qna_id, " +
			         "    q.subject_code, " +
			         "    q.questioner_id, " +
			         "    q.questioner_title, " +
			         "    q.question, " +
			         "    q.question_time, " +
			         "    u.name AS questioner_name " +
			         "FROM Qna_Student_Professor q " +
			         "JOIN Student s ON q.questioner_id = s.user_id " +
			         "JOIN User u ON s.user_id = u.user_id " +
			         "WHERE q.subject_code IN ( " +
			         "    SELECT subject_code " +
			         "    FROM Enrollment " +
			         "    WHERE student_id = ?)";
 
	    try {
	        con = DbcpBean.getConnection();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, studentId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            StudentQnaListVO vo = new StudentQnaListVO();
	            vo.setQnaId(rs.getInt("qna_id"));                         // 질문 ID
	            vo.setSubjectCode(rs.getString("subject_code"));          // 과목 코드
	            vo.setQuestionerId(rs.getInt("questioner_id"));           // 질문자 ID
	            vo.setQuestionerTitle(rs.getString("questioner_title"));  // 질문 제목
	            vo.setQuestion(rs.getString("question"));                 // 질문 내용
	            vo.setQuestionTime(rs.getTimestamp("question_time"));     // 질문 시간
	            vo.setQuestionerName(rs.getString("questioner_name"));
	            list.add(vo);
	        }
	    }catch (Exception e) {
	    	e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
	    return list;
	}
	
	//학생 개인정보 조회
	public UserVO getStudent(Integer student_id) {
		UserVO userVO = new UserVO();
		StudentVO studentVO = new StudentVO();
		
		String sql = "SELECT "
				+ "    u.user_id AS user_id, "
				+ "    u.name, "
				+ "    u.password, "
				+ "    u.email, "
				+ "    u.role, "
				+ "    s.student_number, "
				+ "    s.department, "
				+ "    s.grade, "
				+ "    s.status "
				+ "FROM user u "
				+ "JOIN student s ON u.user_id = s.user_id "
				+ "WHERE u.user_id = ? ";
		
		try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, student_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {

				userVO.setId(rs.getInt("user_id"));
				userVO.setPassword(rs.getString("password"));
				userVO.setName(rs.getString("name"));
				userVO.setEmail(rs.getString("email"));
				userVO.setRole(rs.getString("role"));
				
				studentVO.setDepartment(rs.getString("department"));
				studentVO.setStudent_id(rs.getString("student_number"));
				studentVO.setGrade(rs.getString("grade"));
				studentVO.setStatus(rs.getString("status"));
				
				userVO.setStudentVO(studentVO);
							
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return userVO;
	}

	public void updateStudent(UserVO userVO) {
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "UPDATE user SET password = ?, email = ? WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userVO.getPassword());
			pstmt.setString(2, userVO.getEmail());
			pstmt.setInt(3, userVO.getId());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "UPDATE student SET status = ? WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userVO.getStudentVO().getStatus());
			pstmt.setInt(2, userVO.getId());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt);
		}
	}

	public List<StudentQnaListVO> getQnaBySubject(String subjectCode) {
		List<StudentQnaListVO> list = new ArrayList<>();

	    String sql = 
	        "SELECT " +
	        "    q.qna_id, " +
	        "    q.subject_code, " +
	        "    q.questioner_id, " +
	        "    q.questioner_title, " +
	        "    q.question, " +
	        "    q.question_time, " +
	        "    u.name AS questioner_name " +
	        "FROM Qna_Student_Professor q " +
	        "JOIN Student s ON q.questioner_id = s.user_id " +
	        "JOIN User u ON s.user_id = u.user_id " +
	        "WHERE q.subject_code = ?";
	    
	    try {
	        con = DbcpBean.getConnection();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, subjectCode);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            StudentQnaListVO vo = new StudentQnaListVO();
	            vo.setQnaId(rs.getInt("qna_id"));
	            vo.setSubjectCode(rs.getString("subject_code"));
	            vo.setQuestionerId(rs.getInt("questioner_id"));
	            vo.setQuestionerTitle(rs.getString("questioner_title"));
	            vo.setQuestion(rs.getString("question"));
	            vo.setQuestionTime(rs.getTimestamp("question_time"));
	            vo.setQuestionerName(rs.getString("questioner_name"));
	            list.add(vo);
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	DbcpBean.close(con, pstmt, rs);
	    }
		
		return list;
	}

	public StudentQnaWithRelpyVO getQnaWithReply(String qnaId) {
		StudentQnaWithRelpyVO vo = new StudentQnaWithRelpyVO();
		String sql = 
			    "SELECT " +
			    "  q.qna_id, q.questioner_title, q.question, q.question_time, " +
			    "  r.reply_content, r.reply_time " +
			    "FROM Qna_Student_Professor q " +
			    "LEFT JOIN Reply_Qna_Professor r ON q.qna_id = r.qna_id " +
			    "WHERE q.qna_id = ?";
		
		try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(qnaId));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
		        vo.setQnaId(rs.getInt("qna_id"));
		        vo.setQuestionerTitle(rs.getString("questioner_title"));
		        vo.setQuestion(rs.getString("question"));
		        vo.setQuestionTime(rs.getTimestamp("question_time"));
		        vo.setReplyContent(rs.getString("reply_content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		return vo;
	}

	public int insertStudentQna(HttpServletRequest req, StudentQusetionVO vo) {
		HttpSession session = req.getSession();
	    int studentId = (int) session.getAttribute("id");
		int result = 0;
	    String sql = "INSERT INTO Qna_Student_Professor (subject_code, questioner_id, questioner_title, question) " +
	                 "VALUES (?, ?, ?, ?)";

	    try {
	    	con = DbcpBean.getConnection();
	    	pstmt = con.prepareStatement(sql);

	        pstmt.setString(1, vo.getSubjectCode());
	        pstmt.setInt(2, studentId);
	        pstmt.setString(3, vo.getQuestionerTitle());
	        pstmt.setString(4, vo.getQuestion());

	        result = pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	DbcpBean.close(con, pstmt, rs);
	    }
	    return result;
	}

	public void studentDeleteQ(int qnaId) {
		String sql = "DELETE FROM Qna_Student_Professor WHERE qna_id = ?";
		
		try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qnaId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt);
		}
	}
	
	
	public void qnaCampus(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		int studentId = (int) session.getAttribute("id");
		String questionerTitle = req.getParameter("title");
		String question = req.getParameter("content");
		
		String sql = "INSERT INTO qna_user_admin (questioner_id, question_title, question) VALUES (?, ?, ?)";
		
		try {
			con = DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			pstmt.setString(2, questionerTitle);
			pstmt.setString(3, question);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt);
		}		
	}
}