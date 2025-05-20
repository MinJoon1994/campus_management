package professorvo;

import java.sql.Timestamp;

public class ReplyProfessorVo {
    private int replyId;              // 답변 ID
    private int qnaId;                // 질문 ID
    private int professorNumber;   // 교수 번호
    private String replyContent;      // 답변 내용
    private Timestamp replyTime;      // 답변 시간
	
    public ReplyProfessorVo() {
	}
    
	public ReplyProfessorVo(int replyId, int qnaId, int professorNumber, String replyContent, Timestamp replyTime) {
		this.replyId = replyId;
		this.qnaId = qnaId;
		this.professorNumber = professorNumber;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public int getProfessorNumber() {
		return professorNumber;
	}

	public void setProfessorNumber(int professorNumber) {
		this.professorNumber = professorNumber;
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
