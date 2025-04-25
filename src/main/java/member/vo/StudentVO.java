package member.vo;

public class StudentVO {
	
	private String student_id;
	private String department;
	private String grade;
	private String status;
	
	public StudentVO() {}
	
	public StudentVO(String student_id, String student_department, String grade, String status) {
		this.student_id = student_id;
		this.department = student_department;
		this.grade = grade;
		this.status = status;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
