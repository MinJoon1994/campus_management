package professorvo;

import java.sql.Timestamp;

public class LecturePlanVo {
    private int planId;
    private String subjectCode;
    private String subjectName;
    private String professorId;
    private String professorName;
    private String lecturePeriod;
    private String targetStudents;
    private String mainContent;
    private String goal;
    private String method;
    private String content;
    private String evaluation;
    private Timestamp createdAt;
    private Timestamp updatedAt;
	
    public LecturePlanVo() {
	}

	public LecturePlanVo(int planId, String subjectCode, String subjectName, String professorId, String professorName, String lecturePeriod,
			String targetStudents, String mainContent, String goal, String method, String content, String evaluation,
			Timestamp createdAt, Timestamp updatedAt) {
		this.planId = planId;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.professorId = professorId;
		this.professorName = professorName;
		this.lecturePeriod = lecturePeriod;
		this.targetStudents = targetStudents;
		this.mainContent = mainContent;
		this.goal = goal;
		this.method = method;
		this.content = content;
		this.evaluation = evaluation;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getPlanId() { return planId; }
	public void setPlanId(int planId) { this.planId = planId; }

	public String getSubjectCode() { return subjectCode; }
	public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

	public String getProfessorId() { return professorId; }
	public void setProfessorId(String professorId) { this.professorId = professorId; }
	
	public String getSubjectName() {return subjectName;}
	public void setSubjectName(String subjectName) {this.subjectName = subjectName;}

	public String getProfessorName() { return professorName; }
	public void setProfessorName(String professorName) { this.professorName = professorName; }

	public String getLecturePeriod() { return lecturePeriod; }
	public void setLecturePeriod(String lecturePeriod) { this.lecturePeriod = lecturePeriod; }

	public String getTargetStudents() { return targetStudents; }
	public void setTargetStudents(String targetStudents) { this.targetStudents = targetStudents; }

	public String getMainContent() { return mainContent; }
	public void setMainContent(String mainContent) { this.mainContent = mainContent; }

	public String getGoal() { return goal; }
	public void setGoal(String goal) { this.goal = goal; }

	public String getMethod() { return method; }
	public void setMethod(String method) { this.method = method; }

	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }

	public String getEvaluation() { return evaluation; }
	public void setEvaluation(String evaluation) { this.evaluation = evaluation; }

	public Timestamp getCreatedAt() { return createdAt; }
	public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

	public Timestamp getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
