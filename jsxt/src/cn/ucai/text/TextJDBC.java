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
 * 1.打开cmd窗口
 * 2.连接数据库mysql -hlocalhost -p3306 -uroot -proot
 * 3.向服务器端发送指令
 * 4.接收服务器返回的结果
 * 5.将结果显示在界面上
 * 6.断开连接
 * 
 * 使用java代码操作数据库步骤
 * 1.加载驱动（做准备性工作）
 * 2.连接数据库
 * 3.得到操作数据库的类
 * 4.使用该类的对象操作数据库
 * 5.得到操作结果
 * 6.如果是查询结果，遍历结果集
 * 7.断开连接
 * 
 * 
 * 
 * 对于数据表的查询，使用executeQuery方法
 * 对于数据表的增，删，改,使用executeUpdata方法
 * */
public class TextJDBC {
	public static void main(String[] args){
		user us=new user("1","123456");
		if(login(us)){
			System.out.println("登陆成功");
			
		}else
		{
			System.out.println("登陆失败");
		}
		/*user us1=new user("3","456789",2);
		if(addUser(us1)){
			System.out.println("添加成功");
			
		}else
		{
			System.out.println("添加失败");
		}*/
		if(deleteUser("3")){
			System.out.println("删除成功");
			
		}else
		{
			System.out.println("删除失败");
		}
		
	}
	
	/*
	 * 插入数据
	 * insert into
	 */
	public static boolean addUser(user us){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement("insert into user(id,password,can) values(?,?,?)");
			//5.得到操作结果
			ps.setString(1, us.getId());
			ps.setString(2, us.getPassword());
			ps.setInt(3, us.getCan());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	
	
	
	/*
	 * 删除数据
	 * 
	 */
	public static boolean deleteUser(String id){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement("delete from user where id = ?");
			//5.得到操作结果
			ps.setString(1, id);
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	
	
	
	
	/*模拟登录
	 * select * form user where id=''and password='';
	 * */
	public static boolean login(user us){
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			//1加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//2.连接数据库
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			//3.得到操作数据库的类
			st=(Statement) conn.createStatement();
			//4.使用该类的对象操作数据库
			rs=(ResultSet) st.executeQuery("select * from user where id='"+us.getId()+"'and password='"+us.getPassword()+"'");
			//5.得到操作结果
			if(rs.next())
			{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{//关闭数据库
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
	 *获取user表中的全部数据，并封装为List集合 
	 */
	public static ArrayList<user> getAll(){
		ArrayList<user> list =new ArrayList<user>();
		try{
			//1加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//2.连接数据库
			Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			//3.得到操作数据库的类
			Statement st=(Statement) conn.createStatement();
			//4.使用该类的对象操作数据库
			ResultSet rs=(ResultSet) st.executeQuery("select * from user");
			//5.得到操作结果
			while(rs.next())
			{
				String id=rs.getString(1);
				String password=rs.getString(2);
				int can=rs.getInt(3);
				//System.out.println(id+":"+password+":"+can);
				//封装成对象
				user us=new user(id,password,can);
				list.add(us);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}
