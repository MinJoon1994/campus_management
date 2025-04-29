package main.service;

import java.util.List;

import main.dao.MainDAO;
import main.vo.AcademicCalendarVO;
import main.vo.NoticeVO;
import main.vo.QnaVO;

public class MainServiceImpl implements MainService {
	
	MainDAO mainDAO = new MainDAO();
	
	//학사일정 DB에서 조회(AJAX요청)
	@Override
	public List<AcademicCalendarVO> getAllEvents() {
		
		return mainDAO.getAllEvents();
	}

	//공지사항 DB에서 조회
	@Override
	public List<NoticeVO> getAllNotices(int page, int pageSize) {
		
		return mainDAO.getNotices(page,pageSize);
	}
	
	//공지사항 글 수 가져오기	
	@Override
	public int getNoticeCount() {
		
		return mainDAO.getNoticeCount();
	}
	
	//QNA DB에서 조회
	@Override
	public List<QnaVO> getAllQnas(int page, int pageSize) {

		return mainDAO.getAllQnas(page, pageSize);
	}
	
	//QNA 글 수 가져오기
	@Override
	public int getQnaCount() {
		
		return mainDAO.getQnaCount();
	}




}
