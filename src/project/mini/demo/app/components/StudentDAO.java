package project.mini.demo.app.components;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDAO {
	
	private static String insertStudent = "insert into STUDENT_DET(name,college_name,department,contactNumber,registeration_no,college_year) values (?,?,?,?,?,?)";
	private static String selectStudent ="select * from STUDENT_DET";
	

	public int insertStudentDetails(StudentDetail student) throws SQLException {
		int rowsUpdated = 0;
		Connection con = null;
		try {
			con = DBListener.getInstance().getConnection();
			
			PreparedStatement ps = con.prepareStatement(insertStudent);
			
			ps.setString(1, student.getStudentName());
			ps.setString(2, student.getCollegeName());
			ps.setString(3, student.getDepartment());
			ps.setLong(4, student.getContactNumber());
			ps.setLong(5, student.getRegistrationNo());
			ps.setLong(6, student.getYear());
			
			rowsUpdated = ps.executeUpdate();
			
			System.out.println("No of rows updated: " +rowsUpdated);
			
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(con != null) {
				con.close();
			}
		}
		return rowsUpdated;
	}
	
	public ObservableList<StudentDetail> displayStudents() throws SQLException {
		int rowsUpdated = 0;
		Connection con = null;
		ObservableList<StudentDetail> students = FXCollections.observableArrayList();
		try {
			con = DBListener.getInstance().getConnection();
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(selectStudent);
			//CREATE TABLE STUDENT_DET (id bigint PRIMARY KEY AUTO_INCREMENT, name varchar(255),college_name varchar(255), department varchar(255), 
			//contactNumber bigint UNIQUE, registeration_no bigint, college_year bigint)
			while(rs.next()) {
				StudentDetail student = new StudentDetail();
				student.setId(rs.getLong("id"));
				student.setStudentName(rs.getString("name"));
				student.setCollegeName(rs.getString("college_name"));
				student.setDepartment(rs.getString("department"));
				student.setContactNumber(rs.getLong("contactNumber"));
				student.setRegistrationNo(rs.getLong("registeration_no"));
				student.setYear(rs.getLong("college_year"));
				students.add(student);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(con != null) {
				con.close();
			}
		}
		return students;
	}
}
