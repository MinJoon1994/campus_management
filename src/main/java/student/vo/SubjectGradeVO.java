package student.vo;



//특정 학기의 과목별 성적 상세 정보
public class SubjectGradeVO {

	private String subjectName; // 과목명
	private String professorName; // 교수명
	private int credit; // 학점
	private Double score; // 성적
	private String grade; // 등급(A+,B0 등)
	
	//getter, setter
	public String getSubjectName() {
		return subjectName;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	
	
}
