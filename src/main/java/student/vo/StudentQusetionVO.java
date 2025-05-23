package student.vo;

public class StudentQusetionVO {
	private String subjectCode;       // 질문이 작성된 과목의 코드 (예: "SUB101")
	private String questionerTitle;   // 질문 제목 (예: "과제에 대한 질문입니다")
	private String question;          // 질문 내용 (본문 텍스트)
    private String questionId;
	
    public StudentQusetionVO() {
	}

	public StudentQusetionVO(String subjectCode, String questionerTitle, String question, String questionId) {
		this.subjectCode = subjectCode;
		this.questionerTitle = questionerTitle;
		this.question = question;
		this.questionId = questionId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
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

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}
