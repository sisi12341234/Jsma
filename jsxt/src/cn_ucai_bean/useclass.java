package cn_ucai_bean;

public class useclass {
	private int id;
	private String classroomID;
	private String userID;
	private String starttime;
	private String endtime;
	public useclass(int id, String classroomID,
			String starttime, String endtime, String userID) {
		super();
		this.id = id;
		this.classroomID = classroomID;
		this.userID = userID;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	public useclass( String classroomID, String userID,
			String starttime, String endtime) {
		super();
		this.classroomID = classroomID;
		this.userID = userID;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	public useclass() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "useclass [id=" + id + ", classroomID=" + classroomID
				+ ", userID=" + userID + ", starttime=" + starttime
				+ ", endtime=" + endtime + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassroomID() {
		return classroomID;
	}
	public void setClassroomID(String classroomID) {
		this.classroomID = classroomID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
