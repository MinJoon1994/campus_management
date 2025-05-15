package qna.vo;

import java.sql.Timestamp;

public class QnaVO {
    private int qna_id;
    private String subject_code;
    private int questioner_id;
    private String questioner_title;
    private String question;
    private Timestamp question_time;

    // 화면 표시용
    private String subject_name;

    // ───────── 기본 생성자
    public QnaVO() {}

    // ───────── Getter / Setter

    public int getQna_id() {
        return qna_id;
    }

    public void setQna_id(int qna_id) {
        this.qna_id = qna_id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public int getQuestioner_id() {
        return questioner_id;
    }

    public void setQuestioner_id(int questioner_id) {
        this.questioner_id = questioner_id;
    }

    public String getQuestioner_title() {
        return questioner_title;
    }

    public void setQuestioner_title(String questioner_title) {
        this.questioner_title = questioner_title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Timestamp getQuestion_time() {
        return question_time;
    }

    public void setQuestion_time(Timestamp question_time) {
        this.question_time = question_time;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
