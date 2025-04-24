package Vo;

public class UserVO {
	private int id;
	private String password;
	private String name;
	private String email;
	private String role;
	
	private StudentVO studentVO;
	private ProfessorVO professorVO;
	
	public UserVO() {}

	public UserVO(int id, String password, String name, String email, String role, StudentVO studentVO) {

		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.role = role;
		this.studentVO = studentVO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public StudentVO getStudentVO() {
		return studentVO;
	}

	public void setStudentVO(StudentVO studentVO) {
		this.studentVO = studentVO;
	}

	public ProfessorVO getProfessorVO() {
		return professorVO;
	}

	public void setProfessorVO(ProfessorVO professorVO) {
		this.professorVO = professorVO;
	}
	
	
}
