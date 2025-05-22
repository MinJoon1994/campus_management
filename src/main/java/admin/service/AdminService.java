package admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import admin.vo.Admin_LectureVO;
import main.vo.NoticeVO;
import qna.vo.QnaVO;
import student.vo.LectureVO;

public interface AdminService {
	
	//학생 구성원 목록 조회
	public List getStudentList(HttpServletRequest req, int page);
	
	//교수 구성원 목록 조회
	public List getProfessorList(HttpServletRequest req, int page);
	
	//학생 수 조회
	public int getStudentCount();
	
	//교수 수 조회
	public int getProfessorCount();
	
	//학생 핕터링 요청
	public List getFilteredStudentList(String grade,String status, int offset, int pageSize);
	
	//학생 필터링 총 수
	public int getFilteredStudentCount(String grade, String status);
	
	//공지사항 등록폼 요청
	public void addNotice(HttpServletRequest req);
	
	//공지사항 등록 요청
	public void updateNotice(HttpServletRequest req);
	
	//공지사항 삭제 요청
	public void deleteNotice(HttpServletRequest req);
	
	//교수가 등록한 강의 목록 보기
	public List<Admin_LectureVO> getlectureList(HttpServletRequest req);
	
	//교수가 등록한 강의 승인 처리
	public void approveLecture(HttpServletRequest req);
	
	//교수가 등록한 강의 거부 처리
	public void rejectLecture(HttpServletRequest req);
	
	//전체 질문글 목록 조회
	public List<QnaVO> getQnaList(HttpServletRequest req);
	
	//특정 질문글 보기
	public QnaVO getDetailQna(HttpServletRequest req);
	
	//답변 작성 요청
	public void reply(HttpServletRequest req);
	
	//학사 일정 추가
	public boolean addCalendarEvent(HttpServletRequest req);
	
	//학사 일정 수정
	public boolean updateCalendarEvent(HttpServletRequest req);
	
	//학사 일정 삭제
	public boolean deleteCalendarEvent(HttpServletRequest req);
	


}
