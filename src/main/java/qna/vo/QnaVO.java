package qna.vo;

public class QnaVO {
	
	private int qna_level;
    private int qnaID;
    private String title;
    private String question;
    private String writerName;
    private String questionTime;
    private String category; // "학생→교수" 또는 "사용자→관리자"

    public QnaVO() {}

    
    public QnaVO(int qnaID, String title, String question, String writerName) {
		super();
		this.qnaID = qnaID;
		this.title = title;
		this.question = question;
		this.writerName = writerName;
	}

	public QnaVO(int qnaID, String title, String question, String writerName, String questionTime) {
		super();
		this.qnaID = qnaID;
		this.title = title;
		this.question = question;
		this.writerName = writerName;
		this.questionTime = questionTime;
	}

	public QnaVO(int qnaID, String title, String question, String writerName, String questionTime, String category) {
		super();
		this.qnaID = qnaID;
		this.title = title;
		this.question = question;
		this.writerName = writerName;
		this.questionTime = questionTime;
		this.category = category;
	}

	public int getQna_level() {	return qna_level;}
	public void setQna_level(int qna_level) { this.qna_level = qna_level; }

	public int getQnaID() { return qnaID; }
	public void setQnaID(int qnaID) { this.qnaID = qnaID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getWriterName() { return writerName; }
    public void setWriterName(String writerName) { this.writerName = writerName; }

    public String getQuestionTime() { return questionTime; }
    public void setQuestionTime(String questionTime) { this.questionTime = questionTime; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }


}