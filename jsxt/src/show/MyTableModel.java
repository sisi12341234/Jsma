package show;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
public class MyTableModel extends AbstractTableModel{
	public static Vector rowData;
	public static Vector columnNames;
	public static int ID;
	static int sum;
	static int avg;
	public MyTableModel(String value){
		select(value);
	}
	public MyTableModel(Vector rowData,Vector columnNames){
		this.rowData=rowData;
		this.columnNames=columnNames;
	}
	public static Vector select(String value){
		rowData=new Vector();
		columnNames=new Vector();
		Connection connection=null;
		PreparedStatement ps=null,ps1=null,ps2=null;
		ResultSet rs=null,rs1=null,rs2=null;
		String sql=null,sql1=null;
		if(value=="������Ϣ")
		{
			columnNames.add("���ұ��");
			columnNames.add("�����豸");
			columnNames.add("������������");
			//columnNames.add("���ҹ���Ա���");
			sql="select classroomID,equipment,capacity from classroom,equip where equipmentID=sign";
			sql1="select sum(capacity) as sumvalue from classroom";
			try{
				Class.forName("org.gjt.mm.mysql.Driver");
				connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
				ps1=connection.prepareStatement(sql1);
				rs1=(ResultSet) ps1.executeQuery();
				if(rs1.next())
				{
					System.out.print("����������������");
					System.out.println(rs1.getString(1));
					
				}
			   else 
			    {
				   System.out.println("������������Ϊ��");
			    }
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try{
					rs1.close();
					ps1.close();
					connection.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		else if(value=="����ʹ�����")
		{
			columnNames.add("���ұ��");
			columnNames.add("�Ͽο�ʼʱ��");
			columnNames.add("����ʱ��");
			columnNames.add("������");
			sql="select * from useclass";
	//"select���ұ��,convert(varchar(20),�Ͽο�ʼʱ�䣬111),convert(varchar(20),�Ͽο�ʼʱ��,108),convert(varchar(20),����ʱ��,111),convert(varchar(20),����ʱ��,108),��ʦ���  from ClassRoomInfo";
		}
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2?user=root&password=si13044");
			ps=connection.prepareStatement(sql);
			rs=(ResultSet) ps.executeQuery();
			if(value=="������Ϣ")
			{
				while(rs.next())
				{
					System.out.println("sum:"+sum+"-----"+"avg:"+avg+"-----");
					Vector tem=new Vector();
					tem.add(rs.getString(1));
					tem.add(rs.getString(2));
					tem.add(rs.getString(3));
					//tem.add(rs.getString(4));
					rowData.add(tem);
				}
			}
		   else if(value=="����ʹ�����")
		    {
		    	while(rs.next())
				{
					Vector tem=new Vector();
					ID=rs.getInt(1);
					tem.add(rs.getString(2));
					tem.add(rs.getString(3));
					tem.add(rs.getString(4)); 
					tem.add(rs.getString(5));
					rowData.add(tem);
				}
		    }
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
		return rowData;
	}
	@Override
	public String getColumnName(int arg0){
		return(String)
		columnNames.get(arg0);
	}
	@Override
	public int getColumnCount(){
		return columnNames.size();
	}
	@Override
	public int getRowCount(){
		return rowData.size();
	}
	@Override
	public Object getValueAt(int rowIndex,int columnIndex){
		return ((Vector)rowData.get(rowIndex)).get(columnIndex);
	}
}

