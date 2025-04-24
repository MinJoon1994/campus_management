package Vo;

public class StaffVO {
	private String staff_id;	//직원 ID

	public StaffVO() {
	}

	public StaffVO(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
}
