package admin.vo;

public class Admin_LectureVO {
	
	private String subject_code; //과목코드
	private String subject_name; //과목명
	private String subject_type; //과목유형
	private int open_grade; //개설학년
	private String division; //분반
	private int credit; //학점
	private int professor_id; //교수아이디
	private String professor_name; //교수명
	private String schedule; //시간표 강의요일/시간표
	private int capacity; //정원
	private boolean is_available; //강의 승인 여부
	
	public Admin_LectureVO() {}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getSubject_type() {
		return subject_type;
	}

	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}

	public int getOpen_grade() {
		return open_grade;
	}

	public void setOpen_grade(int open_grade) {
		this.open_grade = open_grade;
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

	public int getProfessor_id() {
		return professor_id;
	}

	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}

	public String getProfessor_name() {
		return professor_name;
	}

	public void setProfessor_name(String professor_name) {
		this.professor_name = professor_name;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isIs_available() {
		return is_available;
	}

	public void setIs_available(boolean is_available) {
		this.is_available = is_available;
	}
	
	
	
	
	
	
}
