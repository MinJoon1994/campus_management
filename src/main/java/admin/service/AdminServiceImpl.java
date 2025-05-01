package admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import admin.dao.AdminDAO;
import admin.vo.Admin_StudentVO;
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

	@Override
	public void addNotice(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNotice(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteNotice(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LectureVO> getlectureList(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approveLecture(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectLecture(HttpServletRequest req) {
		// TODO Auto-generated method stub

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







}
