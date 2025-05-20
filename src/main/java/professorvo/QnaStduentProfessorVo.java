package professorvo;

import java.sql.Timestamp;

// 질문 테이블 + 학생 이름
public class QnaStduentProfessorVo {
    private int qnaId;               // 질문 ID
    private String subjectCode;      // 과목 코드
    private int questionerId;        // 학생 ID
    private String questionerTitle;  // 질문 제목
    private String question;         // 질문 내용
    private Timestamp questionTime;  // 질문 시간
    private String studentName;      // 학생 이름 (User.name)
    
    public QnaStduentProfessorVo() {
	}

	public QnaStduentProfessorVo(int qnaId, String subjectCode, int questionerId, String questionerTitle, String question,
			Timestamp questionTime, String studentName) {
		this.qnaId = qnaId;
		this.subjectCode = subjectCode;
		this.questionerId = questionerId;
		this.questionerTitle = questionerTitle;
		this.question = question;
		this.questionTime = questionTime;
		this.studentName = studentName;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

    
}
