package cn.ucai.text;

import java.awt.List;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import cn_ucai_bean.user;
/**
 * 1.��cmd����
 * 2.�������ݿ�mysql -hlocalhost -p3306 -uroot -proot
 * 3.��������˷���ָ��
 * 4.���շ��������صĽ��
 * 5.�������ʾ�ڽ�����
 * 6.�Ͽ�����
 * 
 * ʹ��java����������ݿⲽ��
 * 1.������������׼���Թ�����
 * 2.�������ݿ�
 * 3.�õ��������ݿ����
 * 4.ʹ�ø���Ķ���������ݿ�
 * 5.�õ��������
 * 6.����ǲ�ѯ��������������
 * 7.�Ͽ�����
 * 
 * 
 * 
 * �������ݱ�Ĳ�ѯ��ʹ��executeQuery����
 * �������ݱ������ɾ����,ʹ��executeUpdata����
 * */
public class TextJDBC {
	public static void main(String[] args){
		user us=new user("1","123456");
		if(login(us)){
			System.out.println("��½�ɹ�");
			
		}else
		{
			System.out.println("��½ʧ��");
		}
		/*user us1=new user("3","456789",2);
		if(addUser(us1)){
			System.out.println("��ӳɹ�");
			
		}else
		{
			System.out.println("���ʧ��");
		}*/
		if(deleteUser("3")){
			System.out.println("ɾ���ɹ�");
			
		}else
		{
			System.out.println("ɾ��ʧ��");
		}
		
	}
	
	/*
	 * ��������
	 * insert into
	 */
	public static boolean addUser(user us){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement("insert into user(id,password,can) values(?,?,?)");
			//5.�õ��������
			ps.setString(1, us.getId());
			ps.setString(2, us.getPassword());
			ps.setInt(3, us.getCan());
			int count=ps.executeUpdate();//���ؼ�����¼��Ӱ��
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//�ر����ݿ�
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	
	
	
	/*
	 * ɾ������
	 * 
	 */
	public static boolean deleteUser(String id){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement("delete from user where id = ?");
			//5.�õ��������
			ps.setString(1, id);
			int count=ps.executeUpdate();//���ؼ�����¼��Ӱ��
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//�ر����ݿ�
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	
	
	
	
	/*ģ���¼
	 * select * form user where id=''and password='';
	 * */
	public static boolean login(user us){
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			//1��������
			Class.forName("com.mysql.jdbc.Driver");
			//2.�������ݿ�
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			//3.�õ��������ݿ����
			st=(Statement) conn.createStatement();
			//4.ʹ�ø���Ķ���������ݿ�
			rs=(ResultSet) st.executeQuery("select * from user where id='"+us.getId()+"'and password='"+us.getPassword()+"'");
			//5.�õ��������
			if(rs.next())
			{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{//�ر����ݿ�
			try{
				if(rs!=null)
				{
					rs.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st!=null)
				{
					st.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(conn!=null)
				{
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	
	
	
	/**
	 *��ȡuser���е�ȫ�����ݣ�����װΪList���� 
	 */
	public static ArrayList<user> getAll(){
		ArrayList<user> list =new ArrayList<user>();
		try{
			//1��������
			Class.forName("com.mysql.jdbc.Driver");
			//2.�������ݿ�
			Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			//3.�õ��������ݿ����
			Statement st=(Statement) conn.createStatement();
			//4.ʹ�ø���Ķ���������ݿ�
			ResultSet rs=(ResultSet) st.executeQuery("select * from user");
			//5.�õ��������
			while(rs.next())
			{
				String id=rs.getString(1);
				String password=rs.getString(2);
				int can=rs.getInt(3);
				//System.out.println(id+":"+password+":"+can);
				//��װ�ɶ���
				user us=new user(id,password,can);
				list.add(us);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}
