package professorvo;

public class GradeInsertVo {
    private int enrollmentId;
    private int registeredBy;
    private double score;
    private String grade;

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public int getRegisteredBy() { return registeredBy; }
    public void setRegisteredBy(int registeredBy) { this.registeredBy = registeredBy; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
