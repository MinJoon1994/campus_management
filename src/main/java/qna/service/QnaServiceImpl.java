package qna.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import main.DbcpBean;
import member.vo.UserVO;
import qna.vo.QnaVO;

public class QnaServiceImpl implements QnaService {

    @Override
    public List<QnaVO> getQnaList(HttpServletRequest req) {
        List<QnaVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            HttpSession session = req.getSession();
            UserVO vo = (UserVO) session.getAttribute("vo");
            int studentId = vo.getId();

            conn = DbcpBean.getConnection();
            String sql = "SELECT q.qna_id, q.subject_code, q.questioner_title, q.question_time, " +
                         "s.subject_name " +
                         "FROM Qna_Student_Professor q " +
                         "JOIN Subject s ON q.subject_code = s.subject_code " +
                         "WHERE q.questioner_id = ? " +
                         "ORDER BY q.question_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                QnaVO qna = new QnaVO();
                qna.setQna_id(rs.getInt("qna_id"));
                qna.setSubject_code(rs.getString("subject_code"));
                qna.setQuestioner_title(rs.getString("questioner_title"));
                qna.setQuestion_time(rs.getTimestamp("question_time"));
                qna.setSubject_name(rs.getString("subject_name"));
                list.add(qna);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }

        return list;
    }

    @Override
    public QnaVO getQnaDetail(HttpServletRequest req) {
        QnaVO vo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            int qnaId = Integer.parseInt(req.getParameter("qna_id"));
            conn = DbcpBean.getConnection();
            String sql = "SELECT q.qna_id, q.subject_code, q.questioner_title, q.question, q.question_time, " +
                         "s.subject_name " +
                         "FROM Qna_Student_Professor q " +
                         "JOIN Subject s ON q.subject_code = s.subject_code " +
                         "WHERE q.qna_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, qnaId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new QnaVO();
                vo.setQna_id(rs.getInt("qna_id"));
                vo.setSubject_code(rs.getString("subject_code"));
                vo.setQuestioner_title(rs.getString("questioner_title"));
                vo.setQuestion(rs.getString("question"));
                vo.setQuestion_time(rs.getTimestamp("question_time"));
                vo.setSubject_name(rs.getString("subject_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }

        return vo;
    }

    @Override
    public void insertClassQuestion(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserVO vo = (UserVO) session.getAttribute("vo");
        int studentId = vo.getId();

        String subjectCode = req.getParameter("subject_code");
        String title = req.getParameter("questioner_title");
        String content = req.getParameter("question");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbcpBean.getConnection();
            String sql = "INSERT INTO Qna_Student_Professor (subject_code, questioner_id, questioner_title, question) " +
                         "VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(pstmt);
            DbcpBean.close(conn);
        }
    }

    @Override
    public void insertCampusQuestion(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserVO vo = (UserVO) session.getAttribute("vo");
        int studentId = vo.getId();

        String title = req.getParameter("question_title");
        String content = req.getParameter("question");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbcpBean.getConnection();
            String sql = "INSERT INTO Qna_User_Admin (questioner_id, question_title, question) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(pstmt);
            DbcpBean.close(conn);
        }
    }
}