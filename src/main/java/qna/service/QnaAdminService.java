package qna.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import qna.vo.QnaVO;

public interface QnaAdminService {
	

	//관리자 질문글 목록 요청
	ArrayList<HashMap<String, Object>> getQnaAdminList();	

	//특정 질문글 조회 요청
	HashMap<String, Object> getQnaAdminDetail(String QnaID);

}
