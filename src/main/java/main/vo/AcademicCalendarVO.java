package main.vo;

import java.sql.Date;

public class AcademicCalendarVO {
	
	private int calendarId;
	private String title;
	private Date start;
	private Date end;
	private String color;
	private String description;
	
	public AcademicCalendarVO() {}

	public AcademicCalendarVO(int calendarId, String title, Date start, Date end, String color, String description) {

		this.calendarId = calendarId;
		this.title = title;
		this.start = start;
		this.end = end;
		this.color = color;
		this.description = description;
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
