package student.vo;

public class StudentGradeVO {
	private int studentId;         // 학생의 고유 ID
	private String studentName;    // 학생 이름
	private String subjectCode;    // 수강 과목 코드 (예: SUB101)
	private String subjectName;    // 수강 과목 이름 (예: 자료구조)
	private double score;          // 성적 점수 (예: 85.5)
	private String grade;          // 성적 등급 (예: A0, B+, 등)
	
	public StudentGradeVO() {
	}

	public StudentGradeVO(int studentId, String studentName, String subjectCode, String subjectName, double score,
			String grade) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.score = score;
		this.grade = grade;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
