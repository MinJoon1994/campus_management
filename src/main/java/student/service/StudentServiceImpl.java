package student.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import grade.vo.GradeVO;
import main.DbcpBean;
import member.vo.StudentVO;
import member.vo.UserVO;
import student.vo.SubjectVO;

public class StudentServiceImpl implements StudentService {

    @Override
    public void enroll(HttpServletRequest req) {
        HttpSession session = req.getSession();
        // 로그인 시 "studentId" 또는 "userId"로 세션에 넣은 값을 가져옵니다.
        String studentId = (String) session.getAttribute("studentId");
        String subjectCode = req.getParameter("subject_code");

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            conn = DbcpBean.getConnection();

            // 1. 중복 수강신청 체크
            String checkSql = "SELECT COUNT(*) FROM Enrollment WHERE student_id = ? AND subject_code = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, studentId);
            checkStmt.setString(2, subjectCode);
            rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new Exception("이미 신청한 과목입니다.");
            }

            // 2. 정원 초과 체크
            String capacitySql = "SELECT capacity, current_enrollment FROM Subject WHERE subject_code = ?";
            PreparedStatement capacityStmt = conn.prepareStatement(capacitySql);
            capacityStmt.setString(1, subjectCode);
            ResultSet capRs = capacityStmt.executeQuery();
            if (capRs.next()) {
                int capacity = capRs.getInt("capacity");
                int enrolled = capRs.getInt("current_enrollment");
                if (enrolled >= capacity) {
                    throw new Exception("정원이 초과된 과목입니다.");
                }
            }
            capRs.close();
            capacityStmt.close();

            // 3. 수강신청 INSERT
            String insertSql = "INSERT INTO Enrollment (student_id, subject_code) VALUES (?, ?)";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, studentId);
            insertStmt.setString(2, subjectCode);
            insertStmt.executeUpdate();

            // 4. 과목 현재 수강인원 증가
            String updateSql = "UPDATE Subject SET current_enrollment = current_enrollment + 1 WHERE subject_code = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, subjectCode);
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
    public List<SubjectVO> getLectureList(HttpServletRequest req) {
        List<SubjectVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbcpBean.getConnection();
            String sql = "SELECT subject_code, subject_name, professor_name, schedule, credit, " +
                         "capacity AS max_students, current_enrollment AS current_students " +
                         "FROM Subject WHERE is_available = TRUE";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectVO sv = new SubjectVO();
                sv.setSubjectCode(        rs.getString("subject_code"));
                sv.setSubjectName(        rs.getString("subject_name"));
                sv.setProfessorName(      rs.getString("professor_name"));
                sv.setSchedule(           rs.getString("schedule"));
                sv.setCredit(             rs.getInt   ("credit"));
                sv.setMaxStudents(        rs.getInt   ("max_students"));
                sv.setCurrentEnrollment(  rs.getInt   ("current_students"));
                list.add(sv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }
        return list;
    }

    @Override
    public List<SubjectVO> getRegisteredLectures(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String studentId = (String) session.getAttribute("studentId");
        List<SubjectVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbcpBean.getConnection();
            String sql = "SELECT s.subject_code, s.subject_name, s.professor_name, s.schedule, " +
                         "s.credit, s.current_enrollment AS current_students, s.capacity AS max_students " +
                         "FROM Enrollment e JOIN Subject s ON e.subject_code = s.subject_code " +
                         "WHERE e.student_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	 SubjectVO sv = new SubjectVO();
                 sv.setSubjectCode(        rs.getString("subject_code"));
                 sv.setSubjectName(        rs.getString("subject_name"));
                 sv.setProfessorName(      rs.getString("professor_name"));
                 sv.setSchedule(           rs.getString("schedule"));
                 sv.setCredit(             rs.getInt   ("credit"));
                 sv.setMaxStudents(        rs.getInt   ("max_students"));
                 sv.setCurrentEnrollment(  rs.getInt   ("current_students"));
                 list.add(sv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }
        return list;
    }

    @Override
    public void enrollDelete(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String studentId = (String) session.getAttribute("studentId");
        String subjectCode = req.getParameter("subject_code");

        Connection conn = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement updateStmt = null;
        try {
            conn = DbcpBean.getConnection();
            String deleteSql = "DELETE FROM Enrollment WHERE student_id = ? AND subject_code = ?";
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setString(1, studentId);
            deleteStmt.setString(2, subjectCode);
            deleteStmt.executeUpdate();

            String updateSql = "UPDATE Subject SET current_enrollment = current_enrollment - 1 WHERE subject_code = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, subjectCode);
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
    public List<GradeVO> getGrades(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String studentId = (String) session.getAttribute("studentId");
        List<GradeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbcpBean.getConnection();
            String sql =
                "SELECT s.subject_code, s.subject_name, s.professor_name, s.credit, " +
                "       g.score, g.grade AS letter_grade, g.reg_date " +
                "FROM Enrollment e " +
                "JOIN Subject s ON e.subject_code = s.subject_code " +
                "JOIN Grade g   ON e.enrollment_id  = g.enrollment_id " +
                "WHERE e.student_id = ? " +
                "ORDER BY g.reg_date DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                GradeVO gv = new GradeVO();
                gv.setSubject_code(    rs.getString("subject_code"));
                gv.setSubject_name(    rs.getString("subject_name"));
                gv.setProfessor_name(  rs.getString("professor_name"));
                gv.setCredit(          rs.getInt   ("credit"));
                gv.setScore(rs.getDouble("score"));
                gv.setGrade(rs.getString("letter_lettergrade"));
                gv.setReg_date(rs.getTimestamp("reg_date"));
                list.add(gv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }
        return list;
    }


    @Override
    public List<GradeVO> getGradesDetail(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String studentId = (String) session.getAttribute("studentId");
        String subjectCode = req.getParameter("subject_code");

        List<GradeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbcpBean.getConnection();
            String sql =
                "SELECT s.subject_code, s.subject_name, s.professor_name, s.credit, " +
                "       g.score, g.grade AS letter_grade, g.reg_date " +
                "FROM Enrollment e " +
                "JOIN Subject s ON e.subject_code = s.subject_code " +
                "JOIN Grade g   ON e.enrollment_id  = g.enrollment_id " +
                "WHERE e.student_id = ? AND s.subject_code = ? " +
                "ORDER BY g.reg_date DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, subjectCode);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                GradeVO gv = new GradeVO();
                gv.setSubject_code(   rs.getString("subject_code"));
                gv.setSubject_name(   rs.getString("subject_name"));
                gv.setProfessor_name( rs.getString("professor_name"));
                gv.setCredit(         rs.getInt("credit"));
                gv.setScore(          rs.getDouble("score"));
                gv.setGrade(          rs.getString("letter_grade"));
                gv.setReg_date(       rs.getTimestamp("reg_date"));
                list.add(gv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> getTimeTable(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserVO vo = (UserVO) session.getAttribute("vo");
        int studentId = vo.getId();

        List<Map<String, Object>> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbcpBean.getConnection();
            String sql = "SELECT s.subject_code, s.subject_name, s.schedule"+
            			 " FROM Enrollment e " +
                         "JOIN Subject s ON e.subject_code = s.subject_code " +
                         "WHERE e.student_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            rs = pstmt.executeQuery();
            String subjectCode = rs.getString("subject_code");

            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                String schedule = rs.getString("schedule"); // 예: "월 10:00~12:00, 수 14:00~16:00"

                if (schedule != null && !schedule.isEmpty()) {
                    String[] entries = schedule.split(",");
                    for (String entry : entries) {
                        entry = entry.trim();
                        if (entry.length() < 3) continue;

                        String dayStr = entry.substring(0, 1);
                        String timeStr = entry.substring(2); // "10:00~12:00"

                        String[] timeParts = timeStr.split("~");
                        if (timeParts.length != 2) continue;

                        String startTime = timeParts[0].trim();
                        String endTime = timeParts[1].trim();

                        String dayNum = convertDayToNumber(dayStr);
                        if (dayNum == null) continue;

                        Map<String, Object> event = new HashMap<>();
                        event.put("title", subjectName);
                        event.put("daysOfWeek", List.of(dayNum));
                        event.put("startTime", startTime);
                        event.put("endTime", endTime);
                        event.put("subjectCode", subjectCode);
                        events.add(event);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }

        return events;
    }

    private String convertDayToNumber(String day) {
        return switch (day) {
            case "월" -> "1";
            case "화" -> "2";
            case "수" -> "3";
            case "목" -> "4";
            case "금" -> "5";
            case "토" -> "6";
            case "일" -> "0";
            default -> null;
        };
    }


    @Override
    public StudentVO getStudent(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserVO vo = (UserVO) session.getAttribute("vo");
        int userId = vo.getId();

        StudentVO student = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbcpBean.getConnection();
            String sql = "SELECT u.name, s.department, s.grade, s.status " +
                         "FROM User u JOIN Student s ON u.user_id = s.user_id " +
                         "WHERE u.user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new StudentVO();
                student.setStudent_id(String.valueOf(userId));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setGrade(String.valueOf(rs.getInt("grade")));
                student.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }

        return student;
    }

    @Override
    public void updateStudent(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserVO vo = (UserVO) session.getAttribute("vo");
        int userId = vo.getId();

        String department = req.getParameter("department");
        int grade = Integer.parseInt(req.getParameter("grade"));
        String status = req.getParameter("status");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbcpBean.getConnection();
            String sql = "UPDATE Student SET department = ?, grade = ?, status = ? WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, department);
            pstmt.setInt(2, grade);
            pstmt.setString(3, status);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(pstmt);
            DbcpBean.close(conn);
        }
    }

    @Override
    public void qnaClass(HttpServletRequest req) {
        // TODO: QnA 클래스 구현
    }

    @Override
    public void qnaCampus(HttpServletRequest req) {
        // TODO: QnA 캠퍼스 구현
    }

}
