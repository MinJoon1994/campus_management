package grade.vo;

import java.sql.Timestamp;

/**
 * grade 테이블 + JOIN 컬럼을 담는 VO
 */
public class GradeVO {
    private int    grade_id;        // PK
    private int    enrollment_id;   // FK
    private double score;          
    private String grade;           // letter_grade
    private Timestamp reg_date;     // 등록 시각

    // 화면용
    private String subject_code;
    private String subject_name;
    private String professor_name;
    
    
    // ────────────────── 기본 생성자
    public GradeVO() {}
    
    // ────────────────── getter / setter
    
	public int getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(int grade_id) {
		this.grade_id = grade_id;
	}
	public int getEnrollment_id() {
		return enrollment_id;
	}
	public void setEnrollment_id(int enrollment_id) {
		this.enrollment_id = enrollment_id;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
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
	public String getProfessor_name() {
		return professor_name;
	}
	public void setProfessor_name(String professor_name) {
		this.professor_name = professor_name;
	}


    
    
}
