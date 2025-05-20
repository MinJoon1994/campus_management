package professorvo;

public class GradeUpdateVo {
    private String subjectCode;
    private String studentNumber;
    private double totalScore;
    private String grade;

    // getter, setter
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public double getTotalScore() { return totalScore; }
    public void setTotalScore(double totalScore) { this.totalScore = totalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
