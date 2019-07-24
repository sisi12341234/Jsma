package cn_ucai_bean;

public class equip {
	private String sign;
	private String equipment;
	public equip() {
		super();
		// TODO Auto-generated constructor stub
	}
	public equip(String sign, String equipment) {
		super();
		this.sign = sign;
		this.equipment = equipment;
	}
	@Override
	public String toString() {
		return "sign [sign=" + sign + ", equipment="
				+ equipment + "]";
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String classroomID) {
		this.sign= classroomID;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
}
