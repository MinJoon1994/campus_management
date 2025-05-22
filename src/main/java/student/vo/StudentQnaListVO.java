package student.vo;

import java.sql.Timestamp;

public class StudentQnaListVO {
	private int qnaId;             // 질문 ID
	private String subjectCode;    // 과목 코드
	private int questionerId;      // 질문자 ID
	private String questionerTitle; // 질문 제목
	private String question;       // 질문 내용
	private Timestamp questionTime; // 질문 시간
	private String questionerName;   // 질문자 이름
	
	public StudentQnaListVO() {
	}
	
	public StudentQnaListVO(int qnaId, String subjectCode, int questionerId, String questionerTitle, String question,
			Timestamp questionTime, String questionerName) {
		this.qnaId = qnaId;
		this.subjectCode = subjectCode;
		this.questionerId = questionerId;
		this.questionerTitle = questionerTitle;
		this.question = question;
		this.questionTime = questionTime;
		this.questionerName = questionerName;
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getQuestionerId() {
		return questionerId;
	}

	public void setQuestionerId(int questionerId) {
		this.questionerId = questionerId;
	}

	public String getQuestionerTitle() {
		return questionerTitle;
	}

	public void setQuestionerTitle(String questionerTitle) {
		this.questionerTitle = questionerTitle;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Timestamp getQuestionTime() {
		return questionTime;
	}

	public void setQuestionTime(Timestamp questionTime) {
		this.questionTime = questionTime;
	}

	public String getQuestionerName() {
		return questionerName;
	}

	public void setQuestionerName(String questionerName) {
		this.questionerName = questionerName;
	}
	
}
