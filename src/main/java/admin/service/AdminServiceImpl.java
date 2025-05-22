package admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import admin.dao.AdminDAO;
import admin.vo.Admin_StudentVO;
import main.vo.NoticeVO;
import admin.vo.Admin_LectureVO;
import qna.vo.QnaVO;
import student.vo.LectureVO;

public class AdminServiceImpl implements AdminService {
	
	AdminDAO adminDAO = new AdminDAO();
	
	//학생 목록 조회
	@Override
	public List getStudentList(HttpServletRequest req, int page) {
		return adminDAO.getStudentList(page);
	}
	
	//교수 목록 조회
	@Override
	public List getProfessorList(HttpServletRequest req, int page) {
		return adminDAO.getProfessorList(page);
	}
	
	//학생 수 조회
	@Override
	public int getStudentCount() {
		return adminDAO.getStudentCount();
	}
	
	//교수 수 조회
	@Override
	public int getProfessorCount() {
		return adminDAO.getProfessorCount();
	}
	
	//학생 필터링 Ajax 요청
	@Override
	public List getFilteredStudentList(String grade, String status, int offset, int pageSize) {
		
		return adminDAO.getFilteredStudentList(grade, status, offset, pageSize);
	}

	@Override
	public int getFilteredStudentCount(String grade, String status) {
		
		return adminDAO.getFilteredStudentCount(grade, status);
	}
	
	//공지사항 추가
	@Override
	public void addNotice(HttpServletRequest req) {
		
		adminDAO.addNotice(req);

	}

	@Override
	public void updateNotice(HttpServletRequest req) {
		
		adminDAO.updateNotice(req);

	}

	@Override
	public void deleteNotice(HttpServletRequest req) {

		adminDAO.deleteNotice(req);

	}

	@Override
	public List<Admin_LectureVO> getlectureList(HttpServletRequest req) {
		
		return adminDAO.getLectureList(req);
	}

	@Override
	public void approveLecture(HttpServletRequest req) {
		adminDAO.approveLecture(req);
	}

	@Override
	public void rejectLecture(HttpServletRequest req) {
		adminDAO.rejectLecture(req);
	}

	@Override
	public List<QnaVO> getQnaList(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaVO getDetailQna(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reply(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}
	
	//학사 일정 추가
	@Override
	public boolean addCalendarEvent(HttpServletRequest req) {
		
		return adminDAO.addCalendarEvent(req);
	}
	
	//학사 일정 수정
	@Override
	public boolean updateCalendarEvent(HttpServletRequest req) {
		
		return adminDAO.updateCalendarEvent(req);
	}
	
	//학사 일정 삭제
	@Override
	public boolean deleteCalendarEvent(HttpServletRequest req) {
		
		return adminDAO.deleteCalendarEvent(req);
	}
	








}
