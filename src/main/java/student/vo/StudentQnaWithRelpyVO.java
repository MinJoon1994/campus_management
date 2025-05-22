package student.vo;

import java.sql.Timestamp;

public class StudentQnaWithRelpyVO {
    private int qnaId;               // QnA ID
    private String questionerTitle;  // 질문 제목
    private String question;         // 질문 내용
    private Timestamp questionTime;  // 질문 시간
    private String replyContent;     // 답변 내용
    private Timestamp replyTime;     // 답변 시간
	
    public StudentQnaWithRelpyVO() {
	}

	public StudentQnaWithRelpyVO(int qnaId, String questionerTitle, String question, Timestamp questionTime,
			String replyContent, Timestamp replyTime) {
		this.qnaId = qnaId;
		this.questionerTitle = questionerTitle;
		this.question = question;
		this.questionTime = questionTime;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
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

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Timestamp getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}
	
}
