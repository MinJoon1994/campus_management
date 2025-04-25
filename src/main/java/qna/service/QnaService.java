package qna.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import qna.vo.QnaVO;

public interface QnaService {
	
	//질문글 목록 요청
	List<QnaVO> getQnaList(HttpServletRequest req);
	
	
	//특정 질문글 조회 요청
	QnaVO getQnaDetail(HttpServletRequest req);

}
