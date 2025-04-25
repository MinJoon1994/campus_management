package notice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import notice.vo.NoticeVO;

public interface NoticeService {
	
	//공지사항 목록 조회요청
	public List<NoticeVO> getNoticeList(HttpServletRequest req);
	
	//특정 공지사항 조회
	public NoticeVO getNoticeDetail(HttpServletRequest req);

}
