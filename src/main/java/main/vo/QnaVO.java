package main.vo;

import java.sql.Date;

public class QnaVO {
	
	private int qnaId;				// 질문 ID
	private String title;			// 질문 제목
	private String question;		// 질문 내용
	private String questioner_id;	// 질문자 ID
	private String questioner_name;	// 질문자 이름
	private Date QuestionTime;		// 질문 날짜
	
	public QnaVO() {}

	public QnaVO(int qnaId, String title, String question, String questioner_id, String questioner_name	, Date QuestionTime) {

		this.qnaId = qnaId;
		this.title = title;
		this.question = question;
		this.questioner_id = questioner_id;
		this.questioner_name = questioner_name;
		this.QuestionTime = QuestionTime;
	}
	
	public String getQuestioner_name() {
		return questioner_name;
	}

	public void setQuestioner_name(String questioner_name) {
		this.questioner_name = questioner_name;
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestioner_id() {
		return questioner_id;
	}

	public void setQuestioner_id(String questioner_id) {
		this.questioner_id = questioner_id;
	}

	public Date getQuestionTime() {
		return QuestionTime;
	}

	public void setQuestionTime(Date questionTime) {
		QuestionTime = questionTime;
	}
	
}
