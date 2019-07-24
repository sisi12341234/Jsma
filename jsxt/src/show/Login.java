package show;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
public class Login extends JDialog implements ActionListener{
	private JLabel name;
	private JLabel psw;
	public JTextField nameV;
	private JPasswordField pswV;
	private JButton login,quit;
	private JPanel top;
	private JPanel center;
	private JPanel bottom;
	private JLabel prompt;
	public static String loginName;
	public static String powers;
	int power;
	public Login(Frame owner, String title, boolean modal){
		super(owner,title, modal);
		init();
	}
	private void init(){
		name=new JLabel("��¼��:");
		nameV=new JTextField(10);
		top=new JPanel();
		top.add(name);
		top.add(nameV);
		this.add(top);
		psw=new JLabel("����:");
		pswV=new JPasswordField(10);
		center=new JPanel();
		center.add(psw);
		center.add(pswV);
		this.add(center);
		login=new JButton();
		login.setText("��¼");
		login.setActionCommand("login");
		login.addActionListener(this);
		quit=new JButton("�˳�");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		bottom=new JPanel();
		bottom.add(login);
		bottom.add(quit);
		this.add(bottom);
		prompt=new JLabel();
		this.add(prompt);
		this.setLayout(new GridLayout(4,1));
		this.setLocation(550, 230);
		this.setSize(300,250);
		this.setVisible(true);
		//this.setLocationRelativeTo(null);//�����ڷ�����Ļ����λ��
	}
	@Override
	public void actionPerformed(ActionEvent argo){
		if(argo.getActionCommand()=="login")
		{
		String name=nameV.getText();
		String psw=pswV.getText();
		if(name.length()<=0||psw.length()<=0)
		{
			prompt.setText("��ʾ���û���������Ϊ��!");
			return;
		}
		if(checkCount(name,psw))
		{
			this.dispose();
		}
		else 
		{
			prompt.setText("��ʾ���û������������");
			return;
		}
		}
		else if(argo.getActionCommand()=="quit")
		{
			System.exit(0);
		}
	}
	private boolean checkCount(String name, String psw)
	{
		Connection connection=null;//
		PreparedStatement ps=null,ps1=null;
		ResultSet rs=null,rs1=null;
		try {
			//��������
			Class.forName("org.gjt.mm.mysql.Driver");
			connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			if(!connection.isClosed())
                System.out.println("Succeeded connecting to the Database!");
			ps=connection.prepareStatement("select * from user");
			//4.ʹ�ø���Ķ���������ݿ�
			rs=(ResultSet) ps.executeQuery();
			System.out.println("���ݿ�������");
			while(rs.next())
			{
				System.out.println("���ݿ�");
				String namet=rs.getString(1);
				String pswt=rs.getString (2);
				System.out.println(namet);
				System.out.println(pswt);
				power=rs.getInt(3);
				if(power==0)
					powers="����Ա";
				if(power==1)
					powers="��ʦ";
				if(power==2)
					powers="ѧ��";
				/*String sql="select canname from user ;//where canID=can and can='"+rs.getInt(3)+"'";
				ps1=connection.prepareStatement(sql);
				rs1=(ResultSet) ps.executeQuery();*/
				//powers=rs1.getString(2);
				System.out.println(namet+"<>"+pswt+"-----"+name+"<>"+psw+"-----");
				if(namet.equals(name)&&pswt.equals(psw))
				{
					loginName=namet;
					return true;
				}
			}
			System.out.println("false");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				rs.close();
				ps.close();
				connection.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	protected void processWindowEvent(WindowEvent arg0){
		super.processWindowEvent(arg0);
		if(arg0.getID()==WindowEvent.WINDOW_CLOSING)
		{
			System. exit(0);
		}
	}
}

