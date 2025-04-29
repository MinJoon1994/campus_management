package main.service;

import java.util.List;

import main.vo.AcademicCalendarVO;
import main.vo.NoticeVO;
import main.vo.QnaVO;

public interface MainService {
	
	//1. 학사일정 DB에서 조회(AJAX요청)
	List<AcademicCalendarVO> getAllEvents();
	
	//2. 공지사항 DB에서 조회
	List<NoticeVO> getAllNotices(int page,int pageSize); //메인 Notice VO 입니다.
	
	//3. 공지사항 글 수 가져오기
	int getNoticeCount(); //공지사항 글 수 가져오기 메소드
	
	//4. QNA DB에서 조회
	List<QnaVO> getAllQnas(int page,int pageSize); //메인 QNA VO 입니다.
	
	//5. QNA 글 수 가져오기
	int getQnaCount();
	

}
