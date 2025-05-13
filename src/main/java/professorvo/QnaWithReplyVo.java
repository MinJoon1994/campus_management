package professorvo;

public class QnaWithReplyVo {
    private QnaVo qna;
    private ReplyProfessorVo reply;
    
	public QnaWithReplyVo() {
	}

	public QnaWithReplyVo(QnaVo qna, ReplyProfessorVo reply) {
		this.qna = qna;
		this.reply = reply;
	}

	public QnaVo getQna() {
		return qna;
	}

	public void setQna(QnaVo qna) {
		this.qna = qna;
	}

	public ReplyProfessorVo getReply() {
		return reply;
	}

	public void setReply(ReplyProfessorVo reply) {
		this.reply = reply;
	}
    
    
}
