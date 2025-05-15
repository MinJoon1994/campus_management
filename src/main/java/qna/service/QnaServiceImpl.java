package qna.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.DbcpBean;
import qna.vo.QnaVO;

public class QnaServiceImpl implements QnaService {

    @Override
    public List<QnaVO> getQnaList(int studentId) {
        List<QnaVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
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
                QnaVO vo = new QnaVO();
                vo.setQna_id(rs.getInt("qna_id"));
                vo.setSubject_code(rs.getString("subject_code"));
                vo.setQuestioner_title(rs.getString("questioner_title"));
                vo.setQuestion_time(rs.getTimestamp("question_time"));
                vo.setSubject_name(rs.getString("subject_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbcpBean.close(conn, pstmt, rs);
        }

        return list;
    }

    @Override
    public QnaVO getQnaDetail(int qnaId) {
        QnaVO vo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
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
}
