package student.vo;

// 특정 학생이 수강중인 과목 코드와 과목 이름
public class StudentSubjectVO {
    private String subjectCode;   // 과목 코드 (예: "SUB101")
    private String subjectName;   // 과목 이름 (예: "자료구조")
	
    public StudentSubjectVO() {
	}
	public StudentSubjectVO(String subjectCode, String subjectName) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
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
}
