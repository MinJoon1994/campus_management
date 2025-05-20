package student.vo;

public class SemesterGradeVO {

	//한 학기별 전체 성적(평균, 이수학점 등)
	private String semester; //예) 2023-1학기
	private int subjectCount; //해당 학기 수강한 과목수
	private int totalCredit; //해당 학기 이수학점
	private double averageScore; //해당 학기 평균학점
	
	
	//getter, setter
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getSubjectCount() {
		return subjectCount;
	}
	public void setSubjectCount(int subjectCount) {
		this.subjectCount = subjectCount;
	}
	public int getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(int totalCredit) {
		this.totalCredit = totalCredit;
	}
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	
	
	
	
}
