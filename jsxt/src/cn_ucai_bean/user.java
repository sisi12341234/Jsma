package cn_ucai_bean;
/**
 * �����ݿ��е�user���Ӧ��ʵ����
 * 
 * */
public class user {
	private String id;
	private String password;
	private static int can;//Ȩ��
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}
	public user(String id, String password, int can) {
		super();
		this.id = id;
		this.password = password;
		this.can = can;
	}
	public user(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static int getCan() {
		return can;
	}
	public void setCan(int can) {
		this.can = can;
	}
	@Override
	public String toString() {
		return "user [id=" + id + ", password=" + password + ", can=" + can
				+ "]";
	}
	
	
}
