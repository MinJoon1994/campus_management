package notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import main.DbcpBean;
import notice.vo.NoticeVO;
import member.vo.UserVO;
public class NoticeDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	String sql = null;

	UserVO uservo = null;

	public List<NoticeVO> getNoticeList(HttpServletRequest req) {

		List<NoticeVO> list = new ArrayList<>();

		

		try {

				con = DbcpBean.getConnection();
				sql =	"SELECT n.notice_id, n.title, n.created_at, u.name AS admin_name " +
						"FROM Notice n JOIN User u ON n.admin_id = u.user_id " +
						"ORDER BY n.created_at DESC";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				

				while(rs.next()) {
					
					NoticeVO vo = new NoticeVO();
					vo.setNoticeID(rs.getInt("notice_id"));
					vo.setTitle(rs.getString("title"));
					vo.setAdminName(rs.getString("admin_name"));
					vo.setCreatedAt(rs.getTimestamp("created_at"));	

					list.add(vo);
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return list;
	}

	
	public NoticeVO getNoticeDetail(HttpServletRequest req) {

		int noticeId = Integer.parseInt(req.getParameter("noticeID"));
	    NoticeVO vo = null;

	    try {
	        con = DbcpBean.getConnection();
	        sql = "SELECT n.notice_id, n.title, n.content, n.created_at, u.name AS admin_name " +
	              "FROM Notice n JOIN User u ON n.admin_id = u.user_id WHERE n.notice_id = ?";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, noticeId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            vo = new NoticeVO();
	            vo.setNoticeID(rs.getInt("notice_id"));
	            vo.setTitle(rs.getString("title"));
	            vo.setContent(rs.getString("content"));
	            vo.setAdminName(rs.getString("admin_name"));
	            vo.setCreatedAt(rs.getTimestamp("created_at"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs);
	    }

	    return vo;
	}


//	public int getNoticeInsert(HttpServletRequest req) {
//		// TODO Auto-generated method stub
//		return 0;
//	}


	public int insertNotice(NoticeVO vo) {
		int result = 0;
	    try {
	        con = DbcpBean.getConnection();
	        sql = "INSERT INTO Notice (title, content, admin_id ) VALUES (?, ?, ?)";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, vo.getTitle());
	        pstmt.setString(2, vo.getContent());
	        pstmt.setInt(3, new UserVO().getId());
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs);
	    }
	    return result;
	}
}
