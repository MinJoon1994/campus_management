package student.vo;

public class LectureVO {
    private int enrollmentId;
    private String subjectCode;
    private String subjectName;
    private String professorName;
    private int credit;
    private String openGrade;

    // getter/setter
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public String getOpenGrade() { return openGrade; }
    public void setOpenGrade(String openGrade) { this.openGrade = openGrade; }
}
