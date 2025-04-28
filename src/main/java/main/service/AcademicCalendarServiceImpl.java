package main.service;

import java.util.List;

import main.dao.AcademicCalenderDAO;
import main.vo.AcademicCalendarVO;

public class AcademicCalendarServiceImpl implements AcademicCalendarService {
	
	AcademicCalenderDAO academicCalenderDAO = new AcademicCalenderDAO();
	
	
	@Override
	public List<AcademicCalendarVO> getAllEvents() {
		
		return academicCalenderDAO.getAllEvents();
	}

}
