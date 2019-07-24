package show;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
public class ControlInterface extends JFrame implements ActionListener{
	Login login;
	private JPanel topBar,rightBar;
	private JLabel userName;
	private JButton quit;
	private JPanel operationP,buttonP,searchP;
	private JLabel operationT,search;
	private JComboBox operation;
	private JButton searchB,alert,insert,delete;
	private JTextField searchT;
	private JScrollPane scroll;
	private JTable content;
	private MyTableModel model;
	private String[]list=new String[]{"������Ϣ",
			"����ʹ�����"};
	private JLabel remind;
	public static void main(String[]args){
		new ControlInterface();
	}
	public ControlInterface(){
		login=new Login(this,"��½",true);
		topBar=new JPanel();
		userName=new JLabel();
		userName.setText(Login.powers+"��¼");
		userName.setFont(new Font("����",1,15));
		userName.setLocation(this.getWidth()/2-userName.getWidth()/2,userName.getHeight()/3);
		topBar.add(userName,BorderLayout.CENTER);
		quit=new JButton("�˳�");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		quit.setLocation((int)(this.getWidth()-getWidth()*6),userName.getHeight()/6);
		topBar.add(quit,BorderLayout.EAST);
		this.add(topBar,BorderLayout.SOUTH);
		rightBar=new JPanel();
		rightBar.setLayout(new GridLayout(4,1));
		this.add(rightBar,BorderLayout.EAST);
		operationP=new JPanel();
		operationP.setLayout(new FlowLayout());
		operationT=new JLabel("ѡ���");
		operation=new JComboBox(list);
		operation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0){
				searchT.setText("������"+operation.getSelectedItem().toString().substring(0,2)+"���");
				model=new MyTableModel(operation.getSelectedItem().toString());
				//remind.setText("������������Ϊ��"+MyTableModel.sum+"   "+"ÿ����ҿ�����ƽ������Ϊ:"+MyTableModel.avg);
				content.setModel(model);
				
			}
		});
		operationP.add(operationT);
		operationP.add(operation);
		buttonP=new JPanel();
		buttonP.setLayout(new GridLayout(3,1));
		alert=new JButton("�޸�");
		alert.addActionListener(this);
		alert.setActionCommand("alert");
		insert=new JButton("����");
		insert.addActionListener(this);
		insert.setActionCommand("insert");
		delete=new JButton("ɾ��");
		delete.addActionListener(this);
		delete.setActionCommand("delete");
		buttonP.add(alert);
		buttonP.add(insert);
		buttonP.add(delete);
		operationP.add(buttonP);
		//rightBar.add(operation);
		search=new JLabel("�ؼ���");
		searchT=new JTextField(10);
		searchT.setText("������"+operation.getSelectedItem().toString().substring(0,2)+"���");
		searchB=new JButton("����");
		searchB.addActionListener(this);
		searchB.setActionCommand("search");
		searchP=new JPanel();
		searchP.setLayout(new FlowLayout());
		searchP.add(search);
		searchP.add(searchT);
		searchP.add(searchB);
		rightBar.add(searchP,BorderLayout.CENTER);
		remind=new JLabel();
		rightBar.add(remind);
		rightBar.add(operationP);
		model=new MyTableModel(operation.getSelectedItem().toString());
		content=new JTable(model);
		JTableHeader contentH = content.getTableHeader();
		contentH.setPreferredSize(new Dimension(contentH.getWidth(),40));
		contentH.setBackground(new Color(100,150,200));
		scroll=new JScrollPane(content);
		this.add(scroll,BorderLayout.CENTER);
		this.setTitle("���ҹ���ϵͳ");
		this.setResizable(false);
		this.setLocation(180,60);
		this.setSize(1000,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getActionCommand()=="quit")
		{
			System.exit(0);
		}
		if(arg0.getActionCommand()=="search")
		{
			search2UnionTable();
			return;
		}
		if(((String)operation.getSelectedItem())=="���Ҿ���ʹ�����")
		{
			new RemindDialog(this,"��ʾ",true,0);
			return;
		}
		if(arg0.getActionCommand()=="insert")
		{
			String dL =login.loginName;
			new Insert(this,"����������",true,operation.getSelectedItem().toString(),dL,login.power);
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			return;
		}
		if(content.getSelectedRow()==-1)
		{
			new RemindDialog(this,"��ʾ",true,1);
			return;
		}
		if(arg0.getActionCommand()=="alert")
		{
			if(login.power==0)
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				if(operation.getSelectedItem().toString()=="����ʹ�����")
				{
					javax.swing.JOptionPane.showMessageDialog(null, "�ü�¼�����޸�!");
				}
				else
				{
					//Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
					new Update(this,"�޸�",true,operation.getSelectedItem().toString(),v);
					model=new MyTableModel(operation.getSelectedItem().toString());
					content.setModel(model);
				}
			}
			else
			{
				javax.swing.JOptionPane.showMessageDialog(null, "û��ʹ��Ȩ��!");
			}
			return;
		}
		if(arg0.getActionCommand()=="delete")
		{
			if(login.power==0)
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				if(operation.getSelectedItem().toString()=="����ʹ�����")
				{
					javax.swing.JOptionPane.showMessageDialog(null, "�ü�¼����ɾ��!");
				}
				else
				{
					new Delete(this,"ɾ��",true,v,operation.getSelectedItem().toString());
					model=new MyTableModel(operation.getSelectedItem().toString());
					content.setModel(model);
				}
			}
			else
			{
				javax.swing.JOptionPane.showMessageDialog(null, "û��ʹ��Ȩ��!");
			}
			return;
		}
	}
	private void search2UnionTable() {
		Vector v=MyTableModel.select(operation.getSelectedItem().toString());
		Vector rowData=new Vector();
		Iterator iterator=v.iterator();
		String rNum=searchT.getText().toString().trim();
		while(iterator.hasNext())
		{
			Vector tem=(Vector)iterator.next();
			if(((String)tem.get(0)).equals(rNum))
			{
				rowData.add(tem);
			}
		}
		if(rowData.size()==0)
		{
			remind.setText("----�޸ý�����Ϣ----");
			return;
		}
		else
		{
			  remind.setText("");
		}
		MyTableModel my=new MyTableModel(rowData,MyTableModel.columnNames);
		content.setModel(my);
	} 
}


