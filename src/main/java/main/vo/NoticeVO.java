package main.vo;

import java.sql.Date;

public class NoticeVO {
	
	private int noticeId;		// 공지사항 ID
	private String title;		// 공지사항 제목
	private String content;		// 공지사항 내용
	private Date createdAt;		// 공지사항 작성일자
	private String writer;		// 공지사항 작성자
	
	public NoticeVO() {
		this.writer = "관리자";
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date date) {
		this.createdAt = date;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
	
}
