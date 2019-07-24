package show;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import cn.ucai.text.DBUtil;
import cn_ucai_bean.classroom;
import cn_ucai_bean.equip;
import cn_ucai_bean.useclass;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
public class Insert extends JDialog{
	private JLabel t1,t2,t3,t4,warn;
	private JTextField e1,e2,e3,e4;
	private JButton sure,cancle;
	private String sql,sql1;
	private Vector tVector=new Vector();
	private Vector rVector=new Vector();
	public Insert(Frame owenr,String title,boolean modal,String name,final String dL,int power){
		super(owenr,title,modal);
		e1=new JTextField(15);
		e2=new JTextField(15);
		e3=new JTextField(15);
		e4=new JTextField(15);
		sure=new JButton("确定");
		cancle=new JButton("取消");
		warn=new JLabel();
		if(name=="教室信息")
		{
			if(power==0)
			{
				tVector=MyTableModel.select(name);
				t1=new JLabel("教室编号:");
				t2=new JLabel("教室设备:");
				t3=new JLabel("教室容纳人数:");
				t4=new JLabel("教室设备编号");
				sure.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						String tNum=e1.getText();
						String tName=e2.getText().trim();
						String tCourse=e3.getText().trim();
						String teqs=e4.getText().trim();
						classroom cr=new classroom(tNum,teqs,tCourse);
						equip cr1=new equip(teqs,tName);
						if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||teqs.length()<=0)
						{
							warn.setText("信息不全");
							return;
						}
						for(int i=0;i<tVector.size();i++)
						{
							Vector tem=(Vector)tVector.get(i);
							if(tem.get(0).equals(tNum))
							{
								warn.setText("教室编号已存在");
								return;
							}
						}
						sql="insert into classroom(classroomID,equipmentID,capacity) values(?,?,?)";
						addclassroom(sql,cr);
						sql1="insert into equip(sign,equipment) values(?,?)";
						addequip(sql1,cr1);
						Insert.this.dispose();
						return;
					}

				});
				cancle.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						Insert.this.dispose();
					}
				});
				this.add(t1);
				this.add(e1);
				this.add(t2);
				this.add(e2);
				this.add(t3);
				this.add(e3);
				this.add(t4);
				this.add(e4);
				this.add(sure);
				this.add(cancle);
				this.add(warn);
				this.setLayout(new FlowLayout());
				this.setSize(200,300);
				this.setLocation(550,100);
				this.setResizable(false);
				this.setVisible(true);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
			else
			{
				javax.swing.JOptionPane.showMessageDialog(null, "没有使用权限!");
			}
		}
		else if(name=="教室使用情况")
		{
			JLabel remind=new JLabel("时间填写形式yyyy-mm-dd hh:mm:ss");
			t1=new JLabel("教室编号:");
			t2=new JLabel("上课开始时间:");
			t3=new JLabel("结束时间:");
			t4=new JLabel("教师编号:");
			final JComboBox rNum;
			//Vector tVector=MyTableModel.select("教师信息");
			Vector rVector=MyTableModel.select("教室信息");
			final Vector cVector=MyTableModel.select("教室使用情况");
			final ArrayList rList=new ArrayList();
			for(int i=0;i<rVector.size();i++)
			{
				rList.add(((Vector)rVector.get(i)).get(0));
			}
			rNum=new JComboBox(rList.toArray());
			e4.setText(dL);
			sure.addActionListener(new ActionListener(){
			@Override
		 	public void actionPerformed(ActionEvent e){
				String start=e2.getText().trim();
				String end=e3.getText().trim();
				String rnum=rNum.getSelectedItem().toString().trim();
				useclass uc=new useclass(MyTableModel.ID,rnum,start,end,dL);
				if(start.length()<=0||end.length()<=0)
				{
					warn.setText("信息不全");
					return;
				}
				if(!isDigital(start)||!isDigital(end))
				{
					warn.setText("提示：上课时间书写不规范");
					return;
				}
				start=start.substring(0,10)+""+start.substring(11);
				end=end.substring(0,10)+""+end.substring(11);
				System.out.println(start);
				if(checkCourse(start,end)==1)
				{
					warn.setText("时间：还没上课就下课了!!");
					return;
				}
				else if(checkCourse(start,end)==0)
				{
					warn.setText("提示：上下课时间一致!!!");
					return;
				}
				System.out.println(checkCourse(start,end));
				for(int i=0;i<cVector.size();i++)
				{
					String tem1=(String)((Vector)cVector.get(i)).get(1);
					tem1=tem1.substring(0,10)+""+tem1.substring(11);
					String tem2=(String)((Vector)cVector.get(i)).get(2);
					tem2=tem2.substring(0,10)+""+tem2.substring(11);
					int startInt=checkCourse(start,tem2);
					int endInt=checkCourse(end,tem1);
					System.out.println(startInt);
					System.out.println(endInt);
					if(((Vector)cVector.get(i)).get(0).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
					{
						warn.setText("该时刻该教室已有课，请重新选择!");
						return;
					}
				}
				sql="insert into useclass(ID,classroomID,starttime,endtime,userID) values(?,?,?,?,?)";//"insert into ClassRoomInfo values(""+rNum.getSelectedItem().toString().trim()+"",""+strat+"",""+end+"",""+tNum.getSelectedItem().toString().trim()+"");
				addUseclass(sql,uc);
				Insert.this.dispose();
				return;
				}

			/*private int checkCourse(String end, String tem1) {
				// TODO Auto-generated method stub
				return 0;
			}*/
			private int checkCourse(String strat, String end)
			{
				DateFormat df=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				try{
					System.out.println(df.parse(strat));
					java.util.Date one=null;
					java.util.Date two=null;
					one = df.parse(strat);
					two = df.parse(end);
					if(one.getTime()>two.getTime())
					{
						return 1;
					}
					else if(one.getTime()<two.getTime())
					{
						return -1;
					}
					else if(one.getTime()==two.getTime())
					{
						return 0;
					}
				}
				catch(ParseException e)
				{
					e.printStackTrace();
				}
				return 100;
			}
			});
			cancle.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Insert.this.dispose();
				}
			});
			this.add(t1);
			this.add(rNum);
			this.add(t2);
			this.add(e2);
			this.add(t3);
			this.add(e3);
			this.add(t4);
			this.add(e4);
			this.add(sure);
			this.add(cancle);
			this.add(remind);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(230,300);
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
			ps.setString(1, cr.getClassroomID());
			ps.setString(2, cr.getEquipment());
			ps.setString(3, cr.getCapacity());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	public static boolean addequip(String sql1,equip cr1){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql1);
			//5.得到操作结果
			ps.setString(1, cr1.getSign());
			ps.setString(2, cr1.getEquipment());
			int count=ps.executeUpdate();//返回几条记录被影响
			return count==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{//关闭数据库
			DBUtil.CloseConn(conn,ps,null);
		}
		return false;
	}
	public static boolean addUseclass(String sql,useclass uc){
		Connection conn=DBUtil.getConn();
		PreparedStatement ps=null;
		try{
			ps=(PreparedStatement) conn.prepareStatement(sql);
			//5.得到操作结果
			ps.setInt(1, uc.getId()+1);
			ps.setString(2, uc.getClassroomID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date1=null;
			java.util.Date date2=null;
			try
			{
			date1 = sdf.parse(uc.getStarttime());
			date2 = sdf.parse(uc.getEndtime());
			} catch (ParseException e)
			{
			e.printStackTrace();
			}
			String strDate = sdf.format(date1);
			String strDate1= sdf.format(date2);
			ps.setTimestamp(3, Timestamp.valueOf(strDate));
			ps.setTimestamp(4, Timestamp.valueOf(strDate1));
			//ps.setString(3, uc.getStarttime());
			//ps.setString(4, uc.getEndtime());
			ps.setString(5, uc.getUserID());
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
					tem=value.substring(11,13);
					for(int i3=0;i3<tem.length();i3++)
					{
						char c3=tem.charAt(i3);
						if(i3>9||i3<0)
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
						}
						if(value.charAt(16)!=':')
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
		}
		return true;
	}
}
