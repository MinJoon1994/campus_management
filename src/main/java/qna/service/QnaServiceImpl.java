package qna.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import qna.dao.QnaDAO;
import qna.vo.QnaVO;

public class QnaServiceImpl implements QnaService{

	private QnaDAO qnadao = new QnaDAO();
	

	@Override
	public ArrayList<HashMap<String, Object>> getQnaAdminList() {
		return qnadao.getQnaAdminList();
	}

	@Override
	public HashMap<String, Object> getQnaAdminDetail(String QnaID) {
		return qnadao.getQnaAdminDetail(QnaID);
	}


}
