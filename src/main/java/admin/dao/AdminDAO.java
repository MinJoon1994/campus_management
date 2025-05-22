package admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import admin.vo.Admin_LectureVO;
import admin.vo.Admin_ProfessorVO;
import admin.vo.Admin_StudentVO;
import main.DbcpBean;
import main.vo.NoticeVO;

public class AdminDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//학생 목록 조회
	public List getStudentList(int page) {
		List<Admin_StudentVO> list = new ArrayList<>();
		
		int pageSize = 10;
		int offset = (page - 1) * pageSize;

		try {
			con = DbcpBean.getConnection();
			String sql = "SELECT * FROM user u " +
			             "JOIN student s ON u.user_id = s.user_id " +
			             "WHERE u.role='STUDENT' " +
			             "ORDER BY u.user_id " +
			             "LIMIT ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Admin_StudentVO studentVO = new Admin_StudentVO();
				studentVO.setUser_id(rs.getInt("user_id"));
				studentVO.setName(rs.getString("name"));
				studentVO.setEmail(rs.getString("email"));
				studentVO.setPassword(rs.getString("password"));
				studentVO.setRole(rs.getString("role"));
				studentVO.setStudent_number(rs.getString("student_number"));
				studentVO.setDepartment(rs.getString("department"));
				studentVO.setGrade(rs.getInt("grade"));
				studentVO.setStatus(rs.getString("status"));
				list.add(studentVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return list;
	}
	//학생 수 전체 조회
	public int getStudentCount() {
		int count = 0;
		try {
			con = DbcpBean.getConnection();
			String sql = "SELECT COUNT(*) FROM user u JOIN student s ON u.user_id = s.user_id WHERE u.role='STUDENT'";
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
	
	//교수 목록 조회
	public List getProfessorList(int page) {
		List<Admin_ProfessorVO> list = new ArrayList<>();
		int pageSize = 10;
		int offset = (page - 1) * pageSize;

		try {
			con = DbcpBean.getConnection();
			String sql = "SELECT * FROM user u " +
			             "JOIN professor p ON u.user_id = p.user_id " +
			             "WHERE u.role='PROFESSOR' " +
			             "ORDER BY u.user_id " +
			             "LIMIT ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Admin_ProfessorVO professorVO = new Admin_ProfessorVO();
				professorVO.setUser_id(rs.getInt("user_id"));
				professorVO.setName(rs.getString("name"));
				professorVO.setEmail(rs.getString("email"));
				professorVO.setPassword(rs.getString("password"));
				professorVO.setRole(rs.getString("role"));
				professorVO.setProfessor_number(rs.getString("professor_number"));
				professorVO.setDepartment(rs.getString("department"));
				list.add(professorVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}

		return list;
	}
	//교수 수 전체 조회
	public int getProfessorCount() {
		int count = 0;
		try {
			con = DbcpBean.getConnection();
			String sql = "SELECT COUNT(*) FROM user u JOIN professor p ON u.user_id = p.user_id WHERE u.role='PROFESSOR'";
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
	
	//학생 필터링 Ajax 요청
	public List<Admin_StudentVO> getFilteredStudentList(String grade, String status, int offset, int limit) {
		List<Admin_StudentVO> list = new ArrayList<>();

		try {
			con = DbcpBean.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user u ");
			sql.append("JOIN student s ON u.user_id = s.user_id ");
			sql.append("WHERE u.role = 'STUDENT' ");

			if (grade != null && !grade.isEmpty()) {
				sql.append("AND s.grade = ? ");
			}
			if (status != null && !status.isEmpty()) {
				sql.append("AND s.status = ? ");
			}

			sql.append("ORDER BY u.user_id ASC ");
			sql.append("LIMIT ? OFFSET ?");

			pstmt = con.prepareStatement(sql.toString());

			// 파라미터 바인딩
			int idx = 1;
			if (grade != null && !grade.isEmpty()) {
				pstmt.setInt(idx++, Integer.parseInt(grade));
			}
			if (status != null && !status.isEmpty()) {
				pstmt.setString(idx++, status);
			}
			pstmt.setInt(idx++, limit);
			pstmt.setInt(idx, offset);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Admin_StudentVO vo = new Admin_StudentVO();
				vo.setUser_id(rs.getInt("user_id"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setPassword(rs.getString("password"));
				vo.setRole(rs.getString("role"));
				vo.setStudent_number(rs.getString("student_number"));
				vo.setDepartment(rs.getString("department"));
				vo.setGrade(rs.getInt("grade"));
				vo.setStatus(rs.getString("status"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}

		return list;
	}
	
	//학생 필터링 총 카운트 수
	public int getFilteredStudentCount(String grade, String status) {
		int count = 0;

		try {
			con = DbcpBean.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) AS cnt FROM user u ");
			sql.append("JOIN student s ON u.user_id = s.user_id ");
			sql.append("WHERE u.role = 'STUDENT' ");

			if (grade != null && !grade.isEmpty()) {
				sql.append("AND s.grade = ? ");
			}
			if (status != null && !status.isEmpty()) {
				sql.append("AND s.status = ? ");
			}

			pstmt = con.prepareStatement(sql.toString());

			int idx = 1;
			if (grade != null && !grade.isEmpty()) {
				pstmt.setInt(idx++, Integer.parseInt(grade));
			}
			if (status != null && !status.isEmpty()) {
				pstmt.setString(idx++, status);
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}

		return count;
	}
	
	//공지사항 추가
	public void addNotice(HttpServletRequest req) {
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "INSERT INTO notice (title, content, created_at,admin_id) VALUES (?, ?, NOW(),6)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, req.getParameter("title"));
			pstmt.setString(2, req.getParameter("content"));
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
	}
	
	//교수가 등록한 강의 목록 보기
	public List<Admin_LectureVO> getLectureList(HttpServletRequest req) {
		
		List<Admin_LectureVO> list = new ArrayList<>();
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "SELECT * FROM subject";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				Admin_LectureVO lectureVO = new Admin_LectureVO();
				
				lectureVO.setSubject_code(rs.getString("subject_code"));
				lectureVO.setSubject_name(rs.getString("subject_name"));
				lectureVO.setSubject_type(rs.getString("subject_type"));
				lectureVO.setOpen_grade(rs.getInt("open_grade"));
				lectureVO.setDivision(rs.getString("division"));
				lectureVO.setCredit(rs.getInt("credit"));
				lectureVO.setProfessor_id(rs.getInt("professor_id"));
				lectureVO.setProfessor_name(rs.getString("professor_name"));
				lectureVO.setSchedule(rs.getString("schedule"));
				lectureVO.setCapacity(rs.getInt("capacity"));
				lectureVO.setIs_available(rs.getBoolean("is_available"));
				
				list.add(lectureVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return list;
	}
	
	//교수가 등록한 강의 승인 처리
	public void approveLecture(HttpServletRequest req) {
		
		try {
			con = DbcpBean.getConnection();
			String sql = "UPDATE subject SET is_available = ? WHERE subject_code  = ?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setString(2, req.getParameter("subject_code"));
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
	}
	
	//교수가 등록한 강의 거부 처리
	public void rejectLecture(HttpServletRequest req) {
		
		try {
			con = DbcpBean.getConnection();
			String sql = "UPDATE subject SET is_available = ? WHERE subject_code  = ?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			pstmt.setString(2, req.getParameter("subject_code"));
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
	}
	//학사 일정 추가
	public boolean addCalendarEvent(HttpServletRequest req) {
		
		boolean result = false;
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "INSERT INTO academiccalendar(title,start,end,description,color,admin_id) VALUES(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, req.getParameter("title"));
			pstmt.setString(2, req.getParameter("start"));
			pstmt.setString(3, req.getParameter("end"));
			pstmt.setString(4, req.getParameter("description"));
			pstmt.setString(5, req.getParameter("color"));
			pstmt.setInt(6, 6); // 관리자 ID를 하드코딩
			
			int num  = pstmt.executeUpdate();
			
			// 성공적으로 추가되었다면 true 리턴
			if(num > 0) {
				result = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//학사 일정 수정
	public boolean updateCalendarEvent(HttpServletRequest req) {
		
		boolean result = false;
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "update academiccalendar set title=?,description=? where calendar_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, req.getParameter("title"));
			pstmt.setString(2, req.getParameter("description"));
			pstmt.setInt(3, Integer.parseInt(req.getParameter("id")));
			
			int num = pstmt.executeUpdate();
			
			// 성공적으로 수정되었다면 true 리턴
			if(num > 0) {
				result = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//학사 일정 삭제
	public boolean deleteCalendarEvent(HttpServletRequest req) {
		
		boolean result = false;
		
		try {
			
			con = DbcpBean.getConnection();
			String sql = "delete from academiccalendar where calendar_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(req.getParameter("id")));
			
			int num = pstmt.executeUpdate();
			
			// 성공적으로 삭제되었다면 true 리턴
			if(num > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbcpBean.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//공지사항 수정
	public void updateNotice(HttpServletRequest req) {
		
		int noticeId = Integer.parseInt(req.getParameter("noticeID"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
		
        try {
        	
        	
        	String sql = "update notice set title=? , content=? where notice_id= ?";
        	
        	con = DbcpBean.getConnection();
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1, title);
        	pstmt.setString(2, content);
        	pstmt.setInt(3, noticeId);
        	
        	pstmt.executeUpdate();

        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
        	DbcpBean.close(con,pstmt);
        }
		
	}
	
	//공지사항 삭제
	public void deleteNotice(HttpServletRequest req) {
		
		int noticeId = Integer.parseInt(req.getParameter("noticeID"));
		
		try {
			
			String sql = "delete from notice where notice_id= ?";
			
			con=DbcpBean.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, noticeId);
			
			pstmt.executeUpdate();
					
					
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con,pstmt);
		}
	}
}
