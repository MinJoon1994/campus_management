package student.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import member.vo.StudentVO;
import student.vo.lectureVO;

public interface StudentService {
	
	// 학생 수강신청 요청
	public void enroll(HttpServletRequest req);
	
	//학생 수강목록 확인
	public List<lectureVO> getLectureList(HttpServletRequest req);

	//학생 수강신청 취소요청
	public void enrollDelete(HttpServletRequest req);
	
	//학생 전체 학기 성적 조회
	public List getGrades(HttpServletRequest req);
	
	//학생 성적 상세 조회
	public List getGradesDetail(HttpServletRequest req);
	
	//학생 시간표조회
	public List getTimeTable(HttpServletRequest req);
	
	//학생 개인정보 조회
	public StudentVO getStudent(HttpServletRequest req);
	
	//학생 개인정보 수정 요청
	public void updateStudent(HttpServletRequest req);
	
	//학생 강의관련 질문글 등록 요청
	public void qnaClass(HttpServletRequest req);
	
	//학생 학교관련 질문글 등록 요청
	public void qnaCampus(HttpServletRequest req);
	
	

}
