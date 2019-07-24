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
	private String[]list=new String[]{"教室信息",
			"教室使用情况"};
	private JLabel remind;
	public static void main(String[]args){
		new ControlInterface();
	}
	public ControlInterface(){
		login=new Login(this,"登陆",true);
		topBar=new JPanel();
		userName=new JLabel();
		userName.setText(Login.powers+"登录");
		userName.setFont(new Font("黑体",1,15));
		userName.setLocation(this.getWidth()/2-userName.getWidth()/2,userName.getHeight()/3);
		topBar.add(userName,BorderLayout.CENTER);
		quit=new JButton("退出");
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
		operationT=new JLabel("选择表");
		operation=new JComboBox(list);
		operation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0){
				searchT.setText("请输入"+operation.getSelectedItem().toString().substring(0,2)+"编号");
				model=new MyTableModel(operation.getSelectedItem().toString());
				//remind.setText("可容纳总人数为："+MyTableModel.sum+"   "+"每间教室可容纳平均人数为:"+MyTableModel.avg);
				content.setModel(model);
				
			}
		});
		operationP.add(operationT);
		operationP.add(operation);
		buttonP=new JPanel();
		buttonP.setLayout(new GridLayout(3,1));
		alert=new JButton("修改");
		alert.addActionListener(this);
		alert.setActionCommand("alert");
		insert=new JButton("增加");
		insert.addActionListener(this);
		insert.setActionCommand("insert");
		delete=new JButton("删除");
		delete.addActionListener(this);
		delete.setActionCommand("delete");
		buttonP.add(alert);
		buttonP.add(insert);
		buttonP.add(delete);
		operationP.add(buttonP);
		//rightBar.add(operation);
		search=new JLabel("关键字");
		searchT=new JTextField(10);
		searchT.setText("请输入"+operation.getSelectedItem().toString().substring(0,2)+"编号");
		searchB=new JButton("搜索");
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
		this.setTitle("教室管理系统");
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
		if(((String)operation.getSelectedItem())=="教室具体使用情况")
		{
			new RemindDialog(this,"提示",true,0);
			return;
		}
		if(arg0.getActionCommand()=="insert")
		{
			String dL =login.loginName;
			new Insert(this,"增加新数据",true,operation.getSelectedItem().toString(),dL,login.power);
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			return;
		}
		if(content.getSelectedRow()==-1)
		{
			new RemindDialog(this,"提示",true,1);
			return;
		}
		if(arg0.getActionCommand()=="alert")
		{
			if(login.power==0)
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				if(operation.getSelectedItem().toString()=="教室使用情况")
				{
					javax.swing.JOptionPane.showMessageDialog(null, "该记录不能修改!");
				}
				else
				{
					//Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
					new Update(this,"修改",true,operation.getSelectedItem().toString(),v);
					model=new MyTableModel(operation.getSelectedItem().toString());
					content.setModel(model);
				}
			}
			else
			{
				javax.swing.JOptionPane.showMessageDialog(null, "没有使用权限!");
			}
			return;
		}
		if(arg0.getActionCommand()=="delete")
		{
			if(login.power==0)
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				if(operation.getSelectedItem().toString()=="教室使用情况")
				{
					javax.swing.JOptionPane.showMessageDialog(null, "该记录不能删除!");
				}
				else
				{
					new Delete(this,"删除",true,v,operation.getSelectedItem().toString());
					model=new MyTableModel(operation.getSelectedItem().toString());
					content.setModel(model);
				}
			}
			else
			{
				javax.swing.JOptionPane.showMessageDialog(null, "没有使用权限!");
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
			remind.setText("----无该教室信息----");
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


