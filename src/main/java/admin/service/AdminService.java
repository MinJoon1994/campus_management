package admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import qna.vo.QnaVO;
import student.vo.LectureVO;

public interface AdminService {
	
	//전체 구성원 목록 조회
	public List getMemberList(HttpServletRequest req);
	
	//공지사항 등록폼 요청
	public void addNotice(HttpServletRequest req);
	
	//공지사항 등록 요청
	public void updateNotice(HttpServletRequest req);
	
	//공지사항 삭제 요청
	public void deleteNotice(HttpServletRequest req);
	
	//교수가 등록한 강의 목록 보기
	public List<LectureVO> getlectureList(HttpServletRequest req);
	
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

}
