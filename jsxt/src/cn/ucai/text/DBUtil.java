package cn.ucai.text;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/*���ݿ����ӵĹ��߰�
 * 1.�������ݿ������
 * 2.�ر�������Դ
 */
public class DBUtil {
//��ȡ���ݿ������
	public static Connection getConn(){
		Connection conn=null;
		try{
			//1��������
			Class.forName("org.gjt.mm.mysql.Driver");
			//2.�������ݿ�
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	//�ر�������Դ
	public static void CloseConn(java.sql.Connection conn,PreparedStatement ps,ResultSet rs){
		try{
			if(rs!=null)
			{
				rs.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(ps!=null)
			{
				ps.close();
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
}
