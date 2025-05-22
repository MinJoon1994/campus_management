package student.vo;

public class StudentTimetableVO {
	private String subjectCode;     // 과목 코드 (예: "SUB101")
	private String subjectName;     // 과목 이름 (예: "자료구조")
	private String subjectType;     // 과목 유형 (예: "전공", "교양")
	private int openGrade;          // 개설 학년 (예: 1, 2, 3, 4)
	private String division;        // 분반 (예: "A", "B" 또는 "1반")
	private int credit;             // 학점 (예: 3학점)
	private String schedule;        // 시간표 정보 (예: "월 09:00~11:00 / 수 09:00~11:00")
	private String professorName;   // 담당 교수 이름 (예: "홍길동")
	
	public StudentTimetableVO() {
	}
	public StudentTimetableVO(String subjectCode, String subjectName, String subjectType, int openGrade,
			String division, int credit, String schedule, String professorName) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.subjectType = subjectType;
		this.openGrade = openGrade;
		this.division = division;
		this.credit = credit;
		this.schedule = schedule;
		this.professorName = professorName;
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
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public int getOpenGrade() {
		return openGrade;
	}
	public void setOpenGrade(int openGrade) {
		this.openGrade = openGrade;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
}
