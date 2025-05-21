package qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import main.DbcpBean;
import member.vo.UserVO;
import qna.vo.QnaVO;

public class QnaDAO {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    String sql = null;

	
	public ArrayList<HashMap<String, Object>> getQnaAdminList() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		  try {
		        con = DbcpBean.getConnection();
		        sql ="select q.qna_id, q.question_title AS title, q.question, u.name AS writerName, "
	        		+ "q.question_time, '사용자→관리자' AS category "
		            + "from Qna_User_Admin q "
                    + "join User u ON q.questioner_id = u.user_id "
		            + "order by question_time desc ";

          pstmt = con.prepareStatement(sql);

          rs = pstmt.executeQuery();

          while (rs.next()) {

        	  HashMap<String, Object> map = new HashMap<String, Object>();
              QnaVO vo = new QnaVO();
              vo.setQnaID(rs.getInt("qna_id"));
              vo.setTitle(rs.getString("title"));
              vo.setQuestion(rs.getString("question"));
              vo.setWriterName(rs.getString("writerName"));
              vo.setQuestionTime(rs.getString("question_time"));
              vo.setCategory(rs.getString("category"));
              
              UserVO member = new UserVO();
              map.put("vo", vo);
              map.put("member", member);
              
              list.add(map);
          }
      } catch (Exception e) {
			System.out.println("QnaDAO클래스의 QanAdminList메소드 내부에서 SQL실행 오류:" + e);
          e.printStackTrace();
      } finally {
          DbcpBean.close(con, pstmt, rs);
      }
      return list;
	}
	
	public HashMap<String, Object> getQnaAdminDetail(String QnaID) {
		QnaVO vo = null;
	     
		try {
			con = DbcpBean.getConnection();
			sql = "SELECT q.qna_id, q.question_title AS title, q.question, u.name AS writerName, q.question_time, "
					+ "'사용자 질문' AS category "
					+ "FROM Qna_User_Admin q "
					+ "JOIN User u ON q.questioner_id = u.user_id "
					+ "WHERE q.qna_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(QnaID));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				HashMap<String, Object> qnadetail = new HashMap<String, Object>();

				vo = new QnaVO();
				vo.setQnaID(rs.getInt("qna_id"));
				vo.setTitle(rs.getString("title"));
				vo.setQuestion(rs.getString("question"));
				vo.setWriterName(rs.getString("writerName"));
				vo.setQuestionTime(rs.getString("question_time"));
				vo.setCategory(rs.getString("category"));
				UserVO member = new UserVO();
				qnadetail.put("vo", vo);
	            qnadetail.put("member", member);
				return qnadetail;
			}

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            DbcpBean.close(con, pstmt, rs);
	        }

	        return null;
	}

}