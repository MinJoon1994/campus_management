package professorvo;

public class EnrolledStudentVo {
    private int professorId;         // 교수 ID
    private String professorNumber;    // 교수 이름
    private String subjectCode;       // 과목 코드
    private String subjectName;       // 과목 이름
    private int studentId;            // 학생 ID
    private String studentName;       // 학생 이름
    private String studentNumber;     // 학번
    private String department;        // 학과
	
    public EnrolledStudentVo() {
	}
	
    public EnrolledStudentVo(int professorId, String professorNumber, String subjectCode, String subjectName,
			int studentId, String studentName, String studentNumber, String department) {
		this.professorId = professorId;
		this.professorNumber = professorNumber;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentNumber = studentNumber;
		this.department = department;
	}

    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }

    public String getProfessorNumber() { return professorNumber; }
    public void setProfessorNumber(String professorNumber) { this.professorNumber = professorNumber; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
