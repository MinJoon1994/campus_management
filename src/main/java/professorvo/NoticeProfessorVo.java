package professorvo;

import java.sql.Timestamp;

public class NoticeProfessorVo {
    private int noticeId; // 공지사항 ID
    private String title; // 제목
    private String content; // 내용
    private Timestamp createdAt; // 작성 일시
    private int userId; // 교수 ID
    private String fileName; // 업로드된 파일명
    private String filePath; // 파일 경로
    private long fileSize; // 파일 크기 (바이트 단위)
    
	public NoticeProfessorVo() {}

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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
    
    
}
