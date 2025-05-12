package notice.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import notice.dao.NoticeDAO;
import notice.vo.NoticeVO;

public class NoticeServiceImpl implements NoticeService{

	private NoticeDAO noticeDAO = new NoticeDAO();
	
	@Override
	public List<NoticeVO> getNoticeList(HttpServletRequest req) {
		
		
		return noticeDAO.getNoticeList(req);
	}

	@Override
	public NoticeVO getNoticeDetail(HttpServletRequest req) {

		return noticeDAO.getNoticeDetail(req);

	}

	@Override
	public int insertNotice(NoticeVO vo) {
	    return noticeDAO.insertNotice(vo);
	}	
	
	
}
