package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.DbcpBean;
import main.vo.AcademicCalendarVO;
import main.vo.NoticeVO;
import main.vo.QnaVO;

public class MainDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	public List<AcademicCalendarVO> getAllEvents() {
		
		List<AcademicCalendarVO> list = new ArrayList<AcademicCalendarVO>();
	
		try{
			con = DbcpBean.getConnection();
			//학사일정 조회
			String sql = "select * from AcademicCalendar";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				AcademicCalendarVO vo = new AcademicCalendarVO();
				vo.setCalendarId(rs.getInt("calendar_id"));			//학사일정 ID
				vo.setTitle(rs.getString("title"));					//학사일정 제목		
				vo.setDescription(rs.getString("description"));		//학사일정 설명
				vo.setStart(rs.getDate("start"));					//학사일정 시작일	
				vo.setEnd(rs.getDate("end"));						//학사일정 종료일	
				vo.setColor(rs.getString("color")); 				//학사일정 표시색상
				list.add(vo);
			}//while문 끝	
		} catch (Exception e) {e.printStackTrace();}
		finally {DbcpBean.close(con,pstmt,rs);}//자원해제
		
		return list;		
	}
	
	//공지사항 글 수 가져오기 메소드
	public int getNoticeCount() {
	    int count = 0;
	    
	    try {
	        con = DbcpBean.getConnection();
	        
	        String sql = "SELECT COUNT(*) FROM notice";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs);
	    }
	    
	    return count;
	}
	
	//공지사항 페이징 처리 메소드
	public List<NoticeVO> getNotices(int page, int pageSize) {
	    
	    List<NoticeVO> noticeList = new ArrayList<>();
	    
	    try {
	        con = DbcpBean.getConnection();
	        
	        // 페이징 처리된 공지사항 조회
	        String sql = "SELECT * FROM notice ORDER BY created_at DESC LIMIT ? OFFSET ?";
	        pstmt = con.prepareStatement(sql);

	        int offset = (page - 1) * pageSize;

	        pstmt.setInt(1, pageSize); // 가져올 개수
	        pstmt.setInt(2, offset);   // 건너뛸 개수

	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            NoticeVO vo = new NoticeVO();
	            vo.setNoticeId(rs.getInt("notice_id"));     // 공지사항 ID
	            vo.setTitle(rs.getString("title"));         // 공지사항 제목
	            vo.setContent(rs.getString("content"));     // 공지사항 내용
	            vo.setCreatedAt(rs.getDate("created_at"));  // 공지사항 작성일

	            noticeList.add(vo);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs); // 자원 해제
	    }
	    
	    return noticeList;
	}
	
	public List<QnaVO> getAllQnas(int page, int pageSize) {

	    List<QnaVO> qnaList = new ArrayList<>();

	    try {
	        con = DbcpBean.getConnection();

	        String sql = 
	            "SELECT " +
	            "    q.qna_id, " +
	            "    q.question_title, " +
	            "    q.question, " +
	            "    q.questioner_id, " +
	            "    u.name AS questioner_name, " +
	            "    q.question_time " +
	            "FROM " +
	            "    qna_user_admin q " +
	            "JOIN " +
	            "    user u ON q.questioner_id = u.user_id " +
	            "ORDER BY " +
	            "    q.question_time DESC " +
	            "LIMIT ? OFFSET ?";

	        pstmt = con.prepareStatement(sql);

	        int offset = (page - 1) * pageSize;

	        pstmt.setInt(1, pageSize);  // LIMIT
	        pstmt.setInt(2, offset);    // OFFSET

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            QnaVO vo = new QnaVO();

	            vo.setQnaId(rs.getInt("qna_id"));               		// 질문 ID
	            vo.setTitle(rs.getString("question_title"));        	// 질문 제목
	            vo.setQuestion(rs.getString("question"));       		// 질문 내용
	            vo.setQuestioner_id(rs.getString("questioner_id")); 	// 질문자 ID
	            vo.setQuestioner_name(rs.getString("questioner_name")); // 질문자 이름
	            vo.setQuestionTime(rs.getDate("question_time")); 		// 질문 작성일

	            qnaList.add(vo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs); // 자원 해제
	    }

	    return qnaList;
	}


	public int getQnaCount() {
		
	    int count = 0;
	    
	    try {
	        con = DbcpBean.getConnection();
	        
	        String sql = "SELECT COUNT(*) FROM qna_user_admin";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DbcpBean.close(con, pstmt, rs);
	    }
	    
	    return count;
	}

}
