package professorvo;

import java.sql.Timestamp;

public class QnaVo {
    private int qnaId;               // QnA ID
    private String subjectCode;      // 과목 코드
    private int questionerId;        // 질문자 ID (Student.user_id)
    private String questionerTitle;  // 질문 제목
    private String question;         // 질문 내용
    private Timestamp questionTime;  // 질문 시간
	
    public QnaVo() {
	}
    
	public QnaVo(int qnaId, String subjectCode, int questionerId, String questionerTitle, String question,
			Timestamp questionTime) {
		this.qnaId = qnaId;
		this.subjectCode = subjectCode;
		this.questionerId = questionerId;
		this.questionerTitle = questionerTitle;
		this.question = question;
		this.questionTime = questionTime;
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
}
