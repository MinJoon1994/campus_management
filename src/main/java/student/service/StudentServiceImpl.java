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
    public List getGrades(HttpServletRequest req) {
        // TODO: 성적 조회 구현
        return null;
    }

    @Override
    public List getGradesDetail(HttpServletRequest req) {
        // TODO: 성적 상세조회 구현
        return null;
    }

    @Override
    public List getTimeTable(HttpServletRequest req) {
        // TODO: 시간표 조회 구현
        return null;
    }

    @Override
    public StudentVO getStudent(HttpServletRequest req) {
        // 기존 구현 유지 또는 스키마에 맞게 수정하세요.
        return null;
    }

    @Override
    public void updateStudent(HttpServletRequest req) {
        // TODO: 개인정보 수정 구현
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
