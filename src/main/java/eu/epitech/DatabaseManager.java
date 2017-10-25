package eu.epitech;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	Connection connection;

	public DatabaseManager() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=area&password=djh7.HDi5332jczj");

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
