package student.vo;

public class LectureVO {
    private int lecture_id;
    private String subject_name;
    private String professor_name;
    private String schedule;
    private int credit;
    private int max_students;
    private int current_students;

    // 기본 생성자
    public LectureVO() {}

    // Getters and Setters
    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getMax_students() {
        return max_students;
    }

    public void setMax_students(int max_students) {
        this.max_students = max_students;
    }

    public int getCurrent_students() {
        return current_students;
    }

    public void setCurrent_students(int current_students) {
        this.current_students = current_students;
    }
}
