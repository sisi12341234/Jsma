package show;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

import cn.ucai.text.DBUtil;
public class Delete extends JDialog{
	private String sql,sql1,sql2;
	public Delete(Frame arg0,String arg1,boolean arg2,final Vector v,final String name) {
		super(arg0,arg1,arg2);
		JLabel content=new JLabel("提示：是否删除？");
		JButton sure=new JButton("确定");
		sure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(name=="教室信息")
				{
					String ts = null;
					sql1="select * from classroom";
					Connection connection=null;
					PreparedStatement ps=null;
					ResultSet rs=null;
					try{
						Class.forName("org.gjt.mm.mysql.Driver");
						connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
						ps=connection.prepareStatement(sql1);
						rs=(ResultSet) ps.executeQuery();
						while(rs.next())
						{
							if(v.get(0).toString().trim().equals(rs.getString(1)))
							{
								ts=rs.getString(2);
								break;
							}
						}
					}
					catch(Exception e1){
						e1.printStackTrace();
					}
					finally{
						try{
							rs.close();
							ps.close();
							connection.close();
						}
						catch(SQLException e1){
							e1.printStackTrace();
						}
					}
					sql="delete from classroom where classroomID = ?";
					addclassroom(sql,v);
					System.out.println(ts);
					sql2="delete from equip where sign = ?";
					addequip(sql2,ts);
				}
				Delete.this.dispose();
			}
		});
		JButton cancle=new JButton("取消");
		cancle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Delete.this.disable();
			}
		});
		this.setLayout(new FlowLayout());
		this.add(content);
		this.add(sure);
		this.add(cancle);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(550,230);
		this.setSize(150,100);
		this.setVisible(true);
	}
	
	public static boolean addclassroom(String sql,Vector v){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql);
			//5.得到操作结果
			ps.setString(1, v.get(0).toString().trim());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	public static boolean addequip(String sql,String ts){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql);
			//5.得到操作结果
			ps.setString(1, ts);
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}

}
