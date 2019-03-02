package sql;

import java.sql.*;

import data.SensorData;

public class OperateSql {
	
	public static Connection getConnect() {
		String user = "user";
		String pwd = "";
		String url = "jdbc:mysql://localhost:3306/yuan?characterEncoding=utf8&useSSL=true";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pwd);
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.print("驱动加载失败");
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("获取连接失败");
		}
		return con;
	}
	
	public static SensorData queryData(String id) {
		Connection con = getConnect();
		PreparedStatement ps = null;
		SensorData data = new SensorData();
		if(con != null) {
			try {
				String sql = "select * from sensorinfo where id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet out = ps.executeQuery();
				out.first();
				data.setId(id);
				data.setIp(out.getString("ip"));
				data.setHumi(out.getDouble("humi"));
				data.setTemp(out.getDouble("temp"));
			}catch(SQLException e){
				System.out.println("数据库查询失败,id is : "+id);
			}finally {
				try {
					con.close();
					ps.close();
				}catch(SQLException e) {
					
				}
			}
		}
		return data;
	}

	public static void updateData(SensorData data){
		Connection con = getConnect();
		PreparedStatement ps = null;
		if(con != null) {
			try{
				//更新数据库
				String sql = "update sensorinfo set "
						+ "ip = ?, humi = ?, temp = ? where id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, data.getIp());
				ps.setDouble(2, data.getHumi());
				ps.setDouble(3, data.getTemp());
				ps.setString(4, data.getId());
				int val = ps.executeUpdate();
				if(val <= 0)
					System.out.println("数据库更新失败");    
			}catch(SQLException e){
				System.out.println("数据库更新失败");
			}finally {
				try {
					con.close();
					ps.close();
				}catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
}
