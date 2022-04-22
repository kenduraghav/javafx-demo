package project.mini.demo.app.components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBListener {

	private Connection con;
	
	
	public static DBListener getInstance() {
		return new DBListener();
	}
	
	public void createTable() {
		
		try {
			con = getConnection();
			
			Statement stmt = con.createStatement();
			ResultSet rsmd = con.getMetaData().getTables(null, null, "STUDENT_DET", null);
			if(!rsmd.next()) {
				String sql = "CREATE TABLE STUDENT_DET  (id bigint PRIMARY KEY AUTO_INCREMENT, name varchar(255),college_name varchar(255), department varchar(255), contactNumber bigint UNIQUE, registeration_no bigint, college_year bigint)";
				
				int i = stmt.executeUpdate(sql);
				
				if(i == 0) {
					System.out.println("Table Created Successfully");
				}
			} else {
				System.out.println("Table exists");
			}
			
			
		}catch(SQLException e) {
			System.out.println("DBListener.createTable()" + e.getMessage());;
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Connection getConnection() throws SQLException {
		if(con == null) {
			con = DriverManager.getConnection("jdbc:h2:~/testdb", "sa", "");
		}
		return con;
	}
}
