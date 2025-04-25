package member.vo;

public class ProfessorVO {
	
	private String professor_id;			//교수 ID
	private String professor_department;	//교수 소속 학과
	
	public ProfessorVO() {

	}

	public ProfessorVO(String professor_id, String professor_department) {
		this.professor_id = professor_id;
		this.professor_department = professor_department;
	}

	public String getProfessor_id() {
		return professor_id;
	}

	public void setProfessor_id(String professor_id) {
		this.professor_id = professor_id;
	}

	public String getProfessor_department() {
		return professor_department;
	}

	public void setProfessor_department(String professor_department) {
		this.professor_department = professor_department;
	}
}
