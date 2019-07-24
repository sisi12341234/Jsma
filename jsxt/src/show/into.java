package show;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import cn_ucai_bean.user;
import cn.ucai.*;
import cn.ucai.text.DBUtil;

import java.awt.List;
import java.io.File;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;


public class into  extends JFrame implements ActionListener{
	JPanel p0=new JPanel();
	JPanel p1=new JPanel();
	JLabel jlArray[]={new JLabel("�û���"),new JLabel("����"),new JLabel("")};
	JButton jbArray[]={new JButton("��¼"),new JButton("����")};
	JTextField jtf=new JTextField();
	JPasswordField jpf=new JPasswordField();
	public static String loginName;
	public static int power;
	
	public into(){
		p1.setLayout(null);
		for(int i=0;i<2;i++)
		{
			jlArray[i].setBounds(30,20+i*50,80,26);
			jbArray[i].setBounds(50+i*110,130,80,26);
			p1.add(jlArray[i]);
			p1.add(jbArray[i]);
			jbArray[i].addActionListener(this);
			
		}
		jtf.setBounds(80,20,180,30);
		p1.add(jtf);
		jtf.addActionListener(this);
		jpf.setBounds(80,70,180,30);
		p1.add(jpf);
		jpf.setEchoChar('*');
		jpf.addActionListener(this);
		jlArray[2].setBounds(150,180,300,30);
		p1.add(jlArray[2]);
		
		/*JLabel jl=new JLabel();
		Icon icon=new ImageIcon("1.jpg");     //�ڴ�ֱ�Ӵ�������
		jl.setIcon(icon);
		jl.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());
		p1.add(jl,new Integer(Integer.MIN_VALUE));
		*/
	    this.add(p1);
	    this.setTitle("���ҹ���ϵͳ");
		this.setResizable(false);//�����Ƿ�����ı䴰���С
		this.setBounds(100,100,300,250);
		this.setVisible(true);
		this.setLocationRelativeTo(null);//�����ڷ�����Ļ����λ��
	}
	//���ڼ������̺����
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtf)
		{
			jpf.requestFocus();//�����뽹����ڵ�����������Ŀؼ��ϡ�
			
		}
		else if(e.getSource()==jbArray[1])
		{
			jlArray[2].setText("");
			jtf.setText("");
			jpf.setText("");
			jtf.requestFocus();
		}
		else
		{
			user us=new user(jtf.getText(),String.valueOf(jpf.getPassword()));
			if(login(us)){
				dispose();
				new ControlInterface();
			}else
			{
				jlArray[2].setText("��½ʧ�ܣ������µ�½");
			}
		}
		
	}
	
	
	public static boolean login(user us){
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			conn=DBUtil.getConn();
			//3.�õ��������ݿ����
			st=(Statement) conn.createStatement();
			//4.ʹ�ø���Ķ���������ݿ�
			rs=(ResultSet) st.executeQuery("select * from user where id='"+us.getId()+"'and password='"+us.getPassword()+"'");
			//5.�õ��������
			if(rs.next())
			{
				String id=rs.getString(1);
				String password=rs.getString(2);
				int can=rs.getInt(3);
				user loginuser=new user(id,password,can);
				power=user.getCan();
				System.out.println(loginuser);
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
	
	protected void processWindowEvent(WindowEvent arg0){
		super.processWindowEvent(arg0);
		if(arg0.getID()==WindowEvent.WINDOW_CLOSING)
		{
			System. exit(0);
		}
	}
	public static void main(String[]args){
		new into();
	}
}

