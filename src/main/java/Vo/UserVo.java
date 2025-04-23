package Vo;

import java.sql.Date;

public class UserVo {
	/*
	CREATE TABLE user (
		    id INT AUTO_INCREMENT PRIMARY KEY,        -- 내부 식별자
		    login_id VARCHAR(50) UNIQUE NOT NULL,     -- 로그인 ID
		    password VARCHAR(100) NOT NULL,			  -- 로그인 패스워드
		    name VARCHAR(30),				  		  -- 사용자 이름
		    email VARCHAR(50),						  -- 사용자 이메일
		    gender VARCHAR(30),			  			  -- 사용자 성별
		    phone VARCHAR(50),						  -- 사용자 전화번호
		    role ENUM('STUDENT', 'PROFESSOR', 'STAFF', 'ADMIN') NOT NULL,
		    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
		);
	*/
	private String id, login_id, password, name;
	private String email, gender, phone,  role;
	private Date datetime;

	//기본생성자
	public UserVo() {}

	//날짜와 시간이 있는 UserVo 생성자
	public UserVo(String id, String login_id, String password, String name, String email, String gender, String phone,
			String role, Date datetime) {
		super();
		this.id = id;
		this.login_id = login_id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.role = role;
		this.datetime = datetime;
	}

	//날짜와 시간이 없는 UserVo 생성자
	public UserVo(String id, String login_id, String password, String name, String email, String gender, String phone,
			String role) {
		super();
		this.id = id;
		this.login_id = login_id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.role = role;
	}

	//Setter, Getter 메소드
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	
}
