package professorservice;

import java.util.Vector;

import professordao.ProfessorDao;
import professorvo.EnrolledStudentVo;
import professorvo.LectureListVo;
import professorvo.LecturePlanVo;
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
}
