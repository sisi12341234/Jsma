package cn.ucai.text;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/*数据库连接的工具包
 * 1.进行数据库的连接
 * 2.关闭所有资源
 */
public class DBUtil {
//获取数据库的连接
	public static Connection getConn(){
		Connection conn=null;
		try{
			//1加载驱动
			Class.forName("org.gjt.mm.mysql.Driver");
			//2.连接数据库
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	//关闭所有资源
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
