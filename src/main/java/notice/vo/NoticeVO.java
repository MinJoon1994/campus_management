package notice.vo;

import java.sql.Timestamp;

import member.vo.UserVO;

public class NoticeVO {

	/*
	-- 공지사항 테이블
	CREATE TABLE Notice (
	    notice_id INT AUTO_INCREMENT PRIMARY KEY,
	    title VARCHAR(200) NOT NULL,
	    content TEXT NOT NULL,
	    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	    admin_id INT NOT NULL,
	    FOREIGN KEY (admin_id) REFERENCES User(user_id)
	);
	*/
	private int noticeID;
	private String title;
	private String content;
	private String adminName;
	private Timestamp createdAt;
    private UserVO userVO;
    
	// 기본 생성자
	public NoticeVO() {}

	public NoticeVO(int noticeID, String title, String content, String adminName) {
		super();
		this.noticeID = noticeID;
		this.title = title;
		this.content = content;
		this.adminName = adminName;
	}

	public NoticeVO(int noticeID, String title, String content, String adminName, Timestamp createdAt) {
		super();
		this.noticeID = noticeID;
		this.title = title;
		this.content = content;
		this.adminName = adminName;
		this.createdAt = createdAt;
	}

	public NoticeVO(int noticeID, String title, String content, String adminName, Timestamp createdAt, UserVO userVO) {
		super();
		this.noticeID = noticeID;
		this.title = title;
		this.content = content;
		this.adminName = adminName;
		this.createdAt = createdAt;
		this.userVO = userVO;
	}

	public int getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
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

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
    
}
