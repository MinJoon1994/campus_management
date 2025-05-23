package professorservice;

import java.util.Vector;

import professordao.ProfessorDao;
import professorvo.AttendanceViewVo;
import professorvo.EnrolledStudentVo;
import professorvo.GradeInsertVo;
import professorvo.GradeUpdateVo;
import professorvo.GradeVo;
import professorvo.LectureListVo;
import professorvo.LecturePlanVo;
import professorvo.NoticeProfessorVo;
import professorvo.QnaStduentProfessorVo;
import professorvo.QnaVo;
import professorvo.QnaWithReplyVo;
import professorvo.ReplyProfessorVo;
import professorvo.SubjectVo;

public class ProfessorService {
	ProfessorDao professorDao = new ProfessorDao();
	public Vector<LectureListVo> getAllLectureList(String id) {
		return professorDao.getAllLectureList(id);
	}

	public boolean addLecturePlan(LecturePlanVo planvo) {
		return professorDao.addLecturePlan(planvo);
	}

	public boolean updateLecturePlan(LecturePlanVo planvo) {
		return professorDao.updateLacturePlan(planvo);
	}

	public boolean deleteLecturePlan(String id) {;
		return professorDao.deleteLecturePlan(id);
	}

	public LecturePlanVo getAllLecturePlanList(String subjectCode) {
		return professorDao.getAllLecturePlanList(subjectCode);
	}

	public Vector<EnrolledStudentVo> getAllEnrolledStudentList(String professor_id) {
		return professorDao.getAllEnrolledStudentList(professor_id);
	}

	public boolean addSubject(SubjectVo subjectVo) {
		return professorDao.addLectureForm(subjectVo);
	}
	// subject table 조회 (요청한 강의 확인 + 교수 강의 시간표 조회) 
	public Vector<SubjectVo> getAllSubject(String id) {
		return professorDao.getAllSubject(id);
	}
	// 교수 담당 학생의 점수 조회
	public Vector<GradeVo> getAllGrade(String professor_id) {
		return professorDao.getAllGrade(professor_id);
	}
	// 과목 조회(수강신청 승인된)
	public Vector<LectureListVo> getAllLectureList2(String professor_id) {
		return professorDao.getAllLectureList2(professor_id);
	}
	// 수정 입력 화면 조회
	public Vector<GradeVo> getInsertGrade(String professor_id) {
		return professorDao.getInsertGrade(professor_id);
	}
	// 성적 수정 화면 조회
	public Vector<GradeVo> getUpdateGrade(String professor_id) {
		return professorDao.getUpdatetGrade(professor_id);
	}
	// 성적 등록
	public boolean insertGrade(GradeInsertVo data) {
		return professorDao.insertGrade(data);
	}
	// 성적 수정
	public boolean updateGrade(GradeUpdateVo data) {
		return professorDao.getUpdatetGrade(data);
	}
	// 질문 모두 조회
	public Vector<QnaStduentProfessorVo> getAllQna(String professor_id) {
		return professorDao.getAllQna(professor_id);
	}
	// 선택 질문 조회
	public QnaVo getQna(int qnaId) {
		return professorDao.getQna(qnaId);
	}
	// 선택 질문에 대한 질문과 답변 조회
	public QnaWithReplyVo getQnaWithReply(int qnaId) {
		return professorDao.getQnaWithReply(qnaId);
	}

	public boolean insertReply(ReplyProfessorVo vo, String professor_id) {
		return professorDao.insertReply(vo, professor_id);
	}

	public boolean updateReply(ReplyProfessorVo vo) {
		return professorDao.updateReply(vo);
	}

	public boolean deleteReply(int qnaId) {
		return professorDao.deleteReply(qnaId);
	}

	public boolean deleteStudentQna(String[] qnaIds) {
		return professorDao.deleteStudentQna(qnaIds);
	}
	// 과목과 날짜가 선택된 경우에만 출결 목록 조회
	public Vector<AttendanceViewVo> getAttendanceListBySubjectAndDate(String subjectCode, String date) {
		return professorDao.getAttendanceListBySubjectCode(subjectCode, date);
	}

	public void saveOrUpdateAttendance(int enrollmentId, String date, String status, int professorId) {
		professorDao.saveOrUpdateAttendance(enrollmentId, date, status, professorId);
	}
	// 교수 공지사항 화면 요청(공지사항 조회)
	public Vector<NoticeProfessorVo> getAllNoticeProfessorList(String professor_id) {
		return professorDao.getAllNoticeProfessorList(professor_id);
	}
	// 교수 공지사항 등록
	public void insertNoticeProfessor(NoticeProfessorVo vo) {
		professorDao.insertNoticeProfessor(vo);
		
	}
	// 교수 공지사항 수정
	public boolean updateNoticeProfessor(NoticeProfessorVo vo) {
		return professorDao.updateNoticeProfessor(vo);
		
	}
	// 교수 공지사항 삭제
	public boolean deleteProfessorNotice(String[] noticeIds) {
		return professorDao.deleteProfessorNotice(noticeIds);
	}
	// 교수 공지사항 내용 조회
	public NoticeProfessorVo getNoticeById(String noticeId) {
		return professorDao.getNoticeById(noticeId);
	}
	// 교수 과목 등록시 과목코드 중복체크
	public boolean subjectCodeDuplicateCheck(String subjectCode) {
		return professorDao.subjectCodeDuplicateCheck(subjectCode);
	}
}
