package professordao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import main.DbcpBean;
import professorvo.AttendanceViewVo;
import professorvo.EnrolledStudentVo;
import professorvo.GradeInsertVo;
import professorvo.GradeUpdateVo;
import professorvo.GradeVo;
import professorvo.LectureListVo;
import professorvo.LecturePlanVo;
import professorvo.NoticeProfessorVo;
import professorvo.QnaStduentProfessorVo;
import professorvo.QnaVo;
import professorvo.QnaWithReplyVo;
import professorvo.ReplyProfessorVo;
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
	        pstmt.setInt(1, professorId); 
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
	// 교수 아이디에 해당하는 학생들의 성적 조회
	public Vector<GradeVo> getAllGrade(String professor_id) {
	    Vector<GradeVo> list = new Vector<>();

	    String sql =
	        "SELECT s.subject_code, s.subject_name, stu.user_id AS student_id, stu.name AS student_name, " +
	        "st.student_number, st.department, e.enrollment_id, s.open_grade, g.score, g.grade " +
	        "FROM Professor p " +
	        "JOIN Subject s ON p.user_id = s.professor_id " +
	        "JOIN Enrollment e ON s.subject_code = e.subject_code " +
	        "JOIN Student st ON e.student_id = st.user_id " +
	        "JOIN User stu ON st.user_id = stu.user_id " +
	        "LEFT JOIN Grade g ON e.enrollment_id = g.enrollment_id " +
	        "WHERE p.user_id = ?";

	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(professor_id));

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            GradeVo vo = new GradeVo(
	                rs.getString("subject_code"),
	                rs.getString("subject_name"),
	                rs.getInt("student_id"),
	                rs.getString("student_name"),
	                rs.getString("student_number"),
	                rs.getString("department"),
	                rs.getInt("enrollment_id"),
	                rs.getInt("open_grade"),
	                rs.getDouble("score"),
	                rs.getString("grade")
	            );
	            list.add(vo);
	        }
		} catch (Exception e) {
			System.out.println("교수 아이디에 해당하는 성적조회 중 오류 발생");
	        e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs); 
		}

	    return list;
	}
	// 과목조회(수강신청 승인받은 과목만)
	public Vector<LectureListVo> getAllLectureList2(String professorId) {
		Vector<LectureListVo> list = new Vector<>();

		String sql = "SELECT subject_code, subject_name, subject_type, open_grade, division, credit, "
				+ "professor_id, professor_name, schedule, current_enrollment, capacity, is_available " + "FROM subject WHERE professor_id = ? "
				+ "and is_available = 1";

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
	// 성적 입력을 위한 => 성적 조회(점수가 null)
	public Vector<GradeVo> getInsertGrade(String professor_id) {
	    Vector<GradeVo> list = new Vector<>();

	    String sql = 
	    	    "SELECT " +
	    	    "    s.subject_code, s.subject_name, " +
	    	    "    stu.user_id AS student_id, stu.name AS student_name, " +
	    	    "    st.student_number, st.department, " +
	    	    "    e.enrollment_id, s.open_grade, " +
	    	    "    g.score, g.grade " +
	    	    "FROM Professor p " +
	    	    "JOIN Subject s ON p.user_id = s.professor_id " +
	    	    "JOIN Enrollment e ON s.subject_code = e.subject_code " +
	    	    "JOIN Student st ON e.student_id = st.user_id " +
	    	    "JOIN User stu ON st.user_id = stu.user_id " +
	    	    "LEFT JOIN Grade g ON e.enrollment_id = g.enrollment_id " +
	    	    "WHERE p.user_id = ? AND g.enrollment_id IS NULL";


	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(professor_id));

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            GradeVo vo = new GradeVo(
	                rs.getString("subject_code"),
	                rs.getString("subject_name"),
	                rs.getInt("student_id"),
	                rs.getString("student_name"),
	                rs.getString("student_number"),
	                rs.getString("department"),
	                rs.getInt("enrollment_id"),
	                rs.getInt("open_grade"),
	                rs.getDouble("score"),
	                rs.getString("grade")
	            );
	            list.add(vo);
	        }
		} catch (Exception e) {
			System.out.println("교수 아이디에 해당하는 성적조회 중 오류 발생");
	        e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs); 
		}

	    return list;
	}
	// 성적 입력을 위한 => 성적 조회(점수가 not null)
	public Vector<GradeVo> getUpdatetGrade(String professor_id) {
	    Vector<GradeVo> list = new Vector<>();

	    String sql =
	        "SELECT s.subject_code, s.subject_name, stu.user_id AS student_id, stu.name AS student_name, " +
	        "st.student_number, st.department, e.enrollment_id, s.open_grade, g.score, g.grade " +
	        "FROM Professor p " +
	        "JOIN Subject s ON p.user_id = s.professor_id " +
	        "JOIN Enrollment e ON s.subject_code = e.subject_code " +
	        "JOIN Student st ON e.student_id = st.user_id " +
	        "JOIN User stu ON st.user_id = stu.user_id " +
	        "LEFT JOIN Grade g ON e.enrollment_id = g.enrollment_id " +
	        "WHERE p.user_id = ? and g.grade IS NOT NULL AND g.score IS NOT NULL";

	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(professor_id));

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            GradeVo vo = new GradeVo(
	                rs.getString("subject_code"),
	                rs.getString("subject_name"),
	                rs.getInt("student_id"),
	                rs.getString("student_name"),
	                rs.getString("student_number"),
	                rs.getString("department"),
	                rs.getInt("enrollment_id"),
	                rs.getInt("open_grade"),
	                rs.getDouble("score"),
	                rs.getString("grade")
	            );
	            list.add(vo);
	        }
		} catch (Exception e) {
			System.out.println("교수 아이디에 해당하는 성적조회 중 오류 발생");
	        e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs); 
		}

	    return list;
	}
	// 성적 등록
	public boolean insertGrade(GradeInsertVo vo) {
	    String sql = "INSERT INTO Grade (enrollment_id, score, grade, registered_by) VALUES (?, ?, ?, ?)";

	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setInt(1, vo.getEnrollmentId());
	        pstmt.setDouble(2, vo.getScore());
	        pstmt.setString(3, vo.getGrade());
	        pstmt.setInt(4, vo.getRegisteredBy());

	        int count = pstmt.executeUpdate();
	        return count > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        DbcpBean.close(conn, pstmt, rs);
	    }
	}
	// 성적 수정
	public boolean getUpdatetGrade(GradeUpdateVo vo) {
		String sql = "UPDATE Grade g\n" +
				    "JOIN Enrollment e ON g.enrollment_id = e.enrollment_id\n" +
				    "JOIN Student s ON e.student_id = s.user_id\n" +
				    "SET g.score = ?, g.grade = ?\n" +
				    "WHERE e.subject_code = ? AND s.student_number = ?"; 

        try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
            pstmt.setDouble(1, vo.getTotalScore());
            pstmt.setString(2, vo.getGrade());
            pstmt.setString(3, vo.getSubjectCode());
            pstmt.setString(4, vo.getStudentNumber());

            int count = pstmt.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }
	}
	// Qna 조회
	public Vector<QnaStduentProfessorVo> getAllQna(String professor_id) {
	    Vector<QnaStduentProfessorVo> qnaList = new Vector<>();

	    String sql = "SELECT q.*, u.name AS student_name " +
	                 "FROM Qna_Student_Professor q " +
	                 "JOIN Subject s ON q.subject_code = s.subject_code " +
	                 "JOIN Student st ON q.questioner_id = st.user_id " +
	                 "JOIN User u ON st.user_id = u.user_id " +
	                 "WHERE s.professor_id = ?";

	    try {
		    conn = DbcpBean.getConnection();
		    pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(professor_id));
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	            	QnaStduentProfessorVo vo = new QnaStduentProfessorVo();
	            	vo.setQnaId(rs.getInt("qna_id"));
	            	vo.setSubjectCode(rs.getString("subject_code"));
	            	vo.setQuestionerId(rs.getInt("questioner_id"));
	            	vo.setQuestionerTitle(rs.getString("questioner_title"));
	            	vo.setQuestion(rs.getString("question"));
	            	vo.setQuestionTime(rs.getTimestamp("question_time"));
	            	vo.setStudentName(rs.getString("student_name"));

	                qnaList.add(vo);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	DbcpBean.close(conn, pstmt, rs);
	    }

	    return qnaList;
	}

	public QnaVo getQna(int qnaId) {
		String sql = "select * from Qna_Student_Professor where qna_id = ?";
		
		try {
		    conn = DbcpBean.getConnection();
		    pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaId);
	        ResultSet rs = pstmt.executeQuery();
            
	        if (rs.next()) {
            	QnaVo vo = new QnaVo();
            	vo.setQnaId(rs.getInt("qna_id"));
            	vo.setSubjectCode(rs.getString("subject_code"));
            	vo.setQuestionerId(rs.getInt("questioner_id"));
            	vo.setQuestionerTitle(rs.getString("questioner_title"));
            	vo.setQuestion(rs.getString("question"));
            	vo.setQuestionTime(rs.getTimestamp("question_time"));

            	return vo;
            }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	DbcpBean.close(conn, pstmt, rs);
	    }
		return null;
	}
	// 선택 질문에 대한 질문과 답변 조회
	public QnaWithReplyVo getQnaWithReply(int qnaId) {
	    QnaWithReplyVo result = new QnaWithReplyVo();

	    String sql = "SELECT q.qna_id, q.subject_code, q.questioner_id, q.questioner_title, q.question, q.question_time, " +
	                 "r.reply_id, r.professor_number, r.reply_content, r.reply_time " +
	                 "FROM Qna_Student_Professor q " +
	                 "LEFT JOIN Reply_Qna_Professor r ON q.qna_id = r.qna_id " +
	                 "WHERE q.qna_id = ?";

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            // 질문 정보
	            QnaVo qna = new QnaVo();
	            qna.setQnaId(rs.getInt("qna_id"));
	            qna.setSubjectCode(rs.getString("subject_code"));
	            qna.setQuestionerId(rs.getInt("questioner_id"));
	            qna.setQuestionerTitle(rs.getString("questioner_title"));
	            qna.setQuestion(rs.getString("question"));
	            qna.setQuestionTime(rs.getTimestamp("question_time"));
	            result.setQna(qna);

	            // 답변 정보 (nullable)
	            Integer replyIdObj = (Integer) rs.getObject("reply_id");
	            if (replyIdObj != null) {
	                ReplyProfessorVo reply = new ReplyProfessorVo();
	                reply.setReplyId(replyIdObj);
	                reply.setQnaId(qnaId);
	                reply.setProfessorNumber(rs.getInt("professor_number"));
	                reply.setReplyContent(rs.getString("reply_content"));
	                reply.setReplyTime(rs.getTimestamp("reply_time"));
	                result.setReply(reply);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(conn, pstmt, rs);
	    }

	    return result;
	}
    // 답변 등록
    public boolean insertReply(ReplyProfessorVo vo, String professorId) {
        String sql = "INSERT INTO Reply_Qna_Professor (qna_id, professor_number, reply_content) " +
                     "VALUES (?, ?, ?)";
        try {
        	conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vo.getQnaId());
            pstmt.setString(2, professorId);
            pstmt.setString(3, vo.getReplyContent());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	DbcpBean.close(conn, pstmt);
        }
        return false;
    }

    // 답변 수정
    public boolean updateReply(ReplyProfessorVo vo) {
        String sql = "UPDATE Reply_Qna_Professor SET reply_content = ?, reply_time = NOW() WHERE qna_id = ?";
        try {
        	conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getReplyContent());
            pstmt.setInt(2, vo.getQnaId());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	DbcpBean.close(conn, pstmt);
        }
        return false;
    }

    // 답변 삭제
    public boolean deleteReply(int qnaId) {
        String sql = "DELETE FROM Reply_Qna_Professor WHERE qna_id = ?";
        try {
        	conn = DbcpBean.getConnection();
	        pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, qnaId);
            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	DbcpBean.close(conn, pstmt);
        }
        return false;
    }

    public boolean deleteStudentQna(String[] qnaIds) {
        boolean allDeleted = true;
        
        try {
            conn = DbcpBean.getConnection();

            // 1. 답변 먼저 삭제
            String deleteRepliesSql = "DELETE FROM Reply_Qna_Professor WHERE qna_id = ?";
            pstmt = conn.prepareStatement(deleteRepliesSql);
            for (String qnaIdStr : qnaIds) {
                int qnaId = Integer.parseInt(qnaIdStr);
                pstmt.setInt(1, qnaId);
                pstmt.executeUpdate(); 
            }
            pstmt.close(); 

            // 2. 질문 삭제
            String deleteQnaSql = "DELETE FROM Qna_Student_Professor WHERE qna_id = ?";
            pstmt = conn.prepareStatement(deleteQnaSql);
            for (String qnaIdStr : qnaIds) {
                int qnaId = Integer.parseInt(qnaIdStr);
                pstmt.setInt(1, qnaId);
                int result = pstmt.executeUpdate();
                if (result != 1) {
                    allDeleted = false; // 하나라도 실패
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt);
        }

        return allDeleted;
    }
    

    // ✅ 1. 과목코드 + 날짜 기준 출결 데이터 조회
    public Vector<AttendanceViewVo> getAttendanceListBySubjectAndDate(String subjectCode, String date) {
        Vector<AttendanceViewVo> list = new Vector<>();
        String sql = """
            SELECT e.enrollment_id, u.name, a.status
            FROM Enrollment e
            JOIN Student s ON e.student_id = s.user_id
            JOIN User u ON s.user_id = u.user_id
            LEFT JOIN Attendance a ON e.enrollment_id = a.enrollment_id AND a.date = ?
            WHERE e.subject_code = ?
            ORDER BY u.name ASC
        """;

        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            pstmt.setString(2, subjectCode);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AttendanceViewVo vo = new AttendanceViewVo();
                vo.setEnrollmentId(rs.getInt("enrollment_id"));
                vo.setStudentName(rs.getString("name"));
                vo.setStatus(rs.getString("status")); // null일 수도 있음
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ 2. 출결 저장 (INSERT or UPDATE)
    public void saveOrUpdateAttendance(int enrollmentId, String date, String status, int professorId) {
        String sql = """
            INSERT INTO Attendance (enrollment_id, date, status, checked_by)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                status = VALUES(status),
                checked_by = VALUES(checked_by)
        """;

        try (Connection conn = DbcpBean.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, enrollmentId);
            pstmt.setString(2, date);
            pstmt.setString(3, status);
            pstmt.setInt(4, professorId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 공지사항(교수) 테이블에서 교수 아이디로 모두 조회
	public Vector<NoticeProfessorVo> getAllNoticeProfessorList(String professor_id) {
		Vector<NoticeProfessorVo> noticeList = new Vector<>();
		String sql = "select * from NoticeProfessor where user_id = ?";
		
		try {
			conn = DbcpBean.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(professor_id));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoticeProfessorVo vo = new NoticeProfessorVo();
				vo.setNoticeId(rs.getInt("notice_id"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setCreatedAt(rs.getTimestamp("created_at"));
				vo.setUserId(rs.getInt("user_id"));
				vo.setFileName(rs.getString("file_name"));
				vo.setFilePath(rs.getString("file_path"));
				vo.setFileSize(rs.getInt("file_size"));
				noticeList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs);
		}
		return noticeList;
	}
	// 공지사항(교수) 테이블에 데이터 추가
	public void insertNoticeProfessor(NoticeProfessorVo vo) {
        String sql = "INSERT INTO NoticeProfessor (title, content, user_id, file_name, file_path, file_size) VALUES (?, ?, ?, ?, ?, ?)";
        try {
        	conn = DbcpBean.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, vo.getTitle());
            pstmt.setString(2, vo.getContent());
            pstmt.setInt(3, vo.getUserId());
            pstmt.setString(4, vo.getFileName());
            pstmt.setString(5, vo.getFilePath());
            pstmt.setLong(6, vo.getFileSize());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	DbcpBean.close(conn, pstmt, rs);
        }
	}
	// 공지사항(교수)에서 선택한 공지사항 삭제
	public boolean deleteProfessorNotice(String[] noticeIds) {
        try {
            conn = DbcpBean.getConnection();

            String sql = "DELETE FROM NoticeProfessor WHERE notice_id = ?";
            pstmt = conn.prepareStatement(sql);
            for (String noticeStr : noticeIds) {
                int noticeId = Integer.parseInt(noticeStr);
                pstmt.setInt(1, noticeId);
                pstmt.executeUpdate(); 
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt);
        }

        return false;
	}
	// 공지사항 아이디로 공지사항 상세 조회
	public NoticeProfessorVo getNoticeById(String noticeId) {
		NoticeProfessorVo vo = new NoticeProfessorVo();
		String sql = "select * from NoticeProfessor where notice_id = ?";
		
		try {
			conn = DbcpBean.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeId);
			rs = pstmt.executeQuery();
	
			if(rs.next()) {
	            vo.setNoticeId(rs.getInt("notice_id"));
	            vo.setTitle(rs.getString("title"));
	            vo.setContent(rs.getString("content"));
	            vo.setCreatedAt(rs.getTimestamp("created_at"));
	            vo.setUserId(rs.getInt("user_id"));
	            vo.setFileName(rs.getString("file_name"));
	            vo.setFilePath(rs.getString("file_path"));
	            vo.setFileSize(rs.getLong("file_size"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(conn, pstmt, rs);
		}
		return vo;
	}
}
