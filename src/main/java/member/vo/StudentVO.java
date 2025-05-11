package member.vo;

public class StudentVO {

    private String student_id;
    private String department;  // 학과 코드
    private String grade;
    private String status;

    // 추가: 출력용 필드
    private String name;               // 학생 이름
    private String departmentName;     // 학과명

    public StudentVO() {}

    public StudentVO(String student_id, String department, String grade, String status) {
        this.student_id = student_id;
        this.department = department;
        this.grade = grade;
        this.status = status;
    }

    // Getter & Setter (기존 필드)
    public String getStudent_id() { return student_id; }
    public void setStudent_id(String student_id) { this.student_id = student_id; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getter & Setter (추가 필드)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
