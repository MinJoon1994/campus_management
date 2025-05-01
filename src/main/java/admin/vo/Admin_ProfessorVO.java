package admin.vo;

public class Admin_ProfessorVO {
	
	private int user_id;
	private String name;
	private String email;
	private String password;
	private String role;
	private String professor_number;
	private String department;
	
	public Admin_ProfessorVO() {}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfessor_number() {
		return professor_number;
	}

	public void setProfessor_number(String professor_number) {
		this.professor_number = professor_number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
