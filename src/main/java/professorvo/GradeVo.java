package professorvo;

public class GradeVo {
	private String subjectCode;   // 과목 코드 (예: CS101)
	private String subjectName;   // 과목명 (예: 자료구조)
	private int studentId;        // 학생 ID (User.user_id)
	private String studentName;   // 학생 이름 (예: 홍길동)
	private String studentNumber; // 학번 (예: 20230001)
	private String department;    // 학과 (예: 컴퓨터공학과)
	private int enrollmentId;     // 수강신청 ID (Enrollment.enrollment_id)
	private int openGrade;        // 개설 학년 (1~4학년)
	private double score;         // 점수 (예: 89.50)
	private String grade;         // 등급 (예: A+, B0)
	
	public GradeVo(String subjectCode, String subjectName, int studentId, String studentName, String studentNumber,
			String department, int enrollmentId, int openGrade, double score, String grade) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentNumber = studentNumber;
		this.department = department;
		this.enrollmentId = enrollmentId;
		this.openGrade = openGrade;
		this.score = score;
		this.grade = grade;
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
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public int getOpenGrade() {
		return openGrade;
	}
	public void setOpenGrade(int openGrade) {
		this.openGrade = openGrade;
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

