package student.vo;

public class LectureVO {
	
	private String enrollmentId;
    private String subjectCode;
    private String subjectName;
    private String subjectType;
    private String professorName;
    private String division;
    private int credit;
    private int openGrade;
    private int capacity;
    private int currentEnrollment;
    private String schedule;

    public LectureVO() {}
    
    
	public int getCurrentEnrollment() {
		return currentEnrollment;
	}


	public void setCurrentEnrollment(int currentEnrollment) {
		this.currentEnrollment = currentEnrollment;
	}
	

	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
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

	public int getOpenGrade() {
		return openGrade;
	}

	public void setOpenGrade(int openGrade) {
		this.openGrade = openGrade;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
    
    
}
