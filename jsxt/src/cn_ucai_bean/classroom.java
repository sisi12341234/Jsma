package cn_ucai_bean;

public class classroom {
	private String classroomID;
	private String equipment;
	private String capacity;
	public classroom() {
		super();
		// TODO Auto-generated constructor stub
	}
	public classroom(String classroomID, String equipment, String capacity) {
		super();
		this.classroomID = classroomID;
		this.equipment = equipment;
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return "classroom [classroomID=" + classroomID + ", equipment="
				+ equipment + ", capacity=" + capacity + "]";
	}
	public String getClassroomID() {
		return classroomID;
	}
	public void setClassroomID(String classroomID) {
		this.classroomID = classroomID;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
}
