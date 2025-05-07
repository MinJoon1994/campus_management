package professorvo;

public class SubjectVo {
    private String subjectCode;      // 과목 코드
    private String subjectName;      // 과목 이름
    private String subjectType;      // 과목 유형 (전공/교양)
    private int openGrade;           // 개설 학년
    private String division;         // 분반
    private int credit;              // 학점
    private int professorId;         // 담당 교수 ID
    private String professorName;    // 담당 교수 이름
    private String schedule;         // 강의 시간표
    private int currentEnrollment;   // 현재 수강 인원
    private int capacity;            // 수강 정원
    private boolean isAvailable;     // 수강 가능 여부
	
    public SubjectVo() {
	}

	public SubjectVo(String subjectCode, String subjectName, String subjectType, int openGrade, String division,
			int credit, int professorId, String professorName, String schedule, int currentEnrollment, int capacity,
			boolean isAvailable) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.subjectType = subjectType;
		this.openGrade = openGrade;
		this.division = division;
		this.credit = credit;
		this.professorId = professorId;
		this.professorName = professorName;
		this.schedule = schedule;
		this.currentEnrollment = currentEnrollment;
		this.capacity = capacity;
		this.isAvailable = isAvailable;
	}
	
	public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getSubjectType() { return subjectType; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }

    public int getOpenGrade() { return openGrade; }
    public void setOpenGrade(int openGrade) { this.openGrade = openGrade; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public int getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public boolean getisAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}
