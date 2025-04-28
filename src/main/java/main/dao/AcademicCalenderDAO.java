package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.DbcpBean;
import main.vo.AcademicCalendarVO;

public class AcademicCalenderDAO {
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
				vo.setCalendarId(rs.getInt("calendar_id"));
				vo.setTitle(rs.getString("title"));
				vo.setDescription(rs.getString("description"));
				vo.setStart(rs.getDate("start"));
				vo.setEnd(rs.getDate("end"));
				vo.setColor(rs.getString("color"));
				
				list.add(vo);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			DbcpBean.close(con,pstmt,rs);
		}
		
		return list;
		
	}

}
