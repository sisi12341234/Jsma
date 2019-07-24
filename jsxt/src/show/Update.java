package show;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import cn.ucai.text.DBUtil;
import cn_ucai_bean.classroom;
import cn_ucai_bean.equip;
import cn_ucai_bean.useclass;
import cn_ucai_bean.user;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.ResultSet;
public class Update extends JDialog{
	private JLabel warn=new JLabel();
	private JButton sure=new JButton("确定");
	private JButton cancle=new JButton("取消");
	private String sql,sql1,sql2;
	final JTextField t51=new JTextField();
	final JTextField t52=new JTextField();
	final JTextField t53=new JTextField();
	final JTextField t54=new JTextField();
	public Update(Frame owenr,String title,boolean modal,String name,final Vector v){
		super(owenr,title,modal);
		warn.setBounds(0, 0, 300, 50);
		t51.setText(v.get(0).toString());
		t51.setEditable(false);
		t52.setText(v.get(1).toString());
		t53.setText(v.get(2).toString());
		if(name=="教室信息")
		{
			JLabel t1=new JLabel("教室编号:");
			JLabel t2=new JLabel("教师设备:");
			JLabel t3=new JLabel("教授容纳人数:");
			sure.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					String tNum=t51.getText();
					String tName=t52.getText().trim();
					String tCourse=t53.getText().trim();
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
							if(tNum.equals(rs.getString(1)))
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
					classroom cr=new classroom(tNum,ts,tCourse);
					equip cr1=new equip(ts,tName);
					System.out.println(cr);
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0)
					{
						warn.setText("信息不全");
						System.out.println("yinyinyinhuyhdgdgydgyd");
						return;
					}
					sql="update classroom set equipmentID='"+ts+"',capacity='"+tCourse+"' where classroomID = '"+tNum+"'";
					if(addclassroom(sql,cr))
					{
						System.out.println("gengxinchenggong");
					}
					else
					{
						System.out.println("******************");
					}
					sql2="update equip set equipment='"+tName+"' where sign = '"+ts+"'";
					if(equit(sql2,cr1))
					{
						System.out.println("gengxinchenggong");
					}
					else
					{
						System.out.println("******************");
					}
					Update.this.dispose();
				}

			});
			cancle.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					Update.this.dispose();
				}
			});
			this.add(t1);
			this.add(t51);
			this.add(t2);
			this.add(t52);
			this.add(t3);
			this.add(t53);
			this.add(sure);
			this.add(cancle);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(250,300);
			this.setLocation(550,100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	}

	public static boolean addclassroom(String sql,classroom cr){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql);
			//5.得到操作结果
			//ps.setString(1,tNum);
			//ps.setString(2, cr.getEquipment());
			//ps.setString(3,cr.getCapacity());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	public static boolean equit(String sql,equip cr1){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql);
			//5.得到操作结果
			//ps.setString(1,tNum);
			//ps.setString(2, cr.getEquipment());
			//ps.setString(3,cr.getCapacity());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	
	private boolean isDigital(String value)
	{
		if(value.length()!=19)
		{
			return false;
		}
		String tem=value.substring(0,4);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
			tem=value.substring(5,7);
			for(int i1=0;i1<tem.length();i1++)
			{
				char c1=tem.charAt(i1);
				if(i1>9||i1<0)
				{
					return false;
				}
				tem=value.substring(8,10);
				for(int i2=0;i2<tem.length();i2++)
				{
					char c2=tem.charAt(i2);
					if(i2>9||i2<0)
					{
						return false;
					}
				}
				if(value.charAt(10)!='/')
				{
					return false;
				}
				tem=value.substring(11,13);	
				for(int i3=0;i3<tem.length();i3++)
				{
					char c3=tem.charAt(i3);
					if(i3>9||i3<0)
					{
						return false;
					}
				}
				if(value.charAt(13)!=':')
				{
					return false;
				}
				tem=value.substring(14,16);
				for(int i4=0;i4<tem.length();i4++)
				{
					char c4=tem.charAt(i4);
					if(i4>9||i4<0)
					{
						return false;
					}
					tem=value.substring(17);
					for(int i5=0;i5<tem.length();i5++)
					{
						char c5=tem.charAt(i5);
						if(i5>9||i5<0)
						{
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}

