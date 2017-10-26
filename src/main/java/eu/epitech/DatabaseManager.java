package eu.epitech;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DatabaseManager {

	private Connection connection;

	public DatabaseManager() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost/area?" + "user=area&password=djh7.HDi5332jczj");
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	/*
	*** Checks if the database recognizes this username as part of the users pool.
	 */
	public boolean hasUser(String userName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = this.connection.prepareStatement("SELECT id FROM user WHERE name LIKE ?");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			return (rs.first());
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
		return (true);
	}

	/*
	*** Returns all stocked information about a user or null if the authentication failed.
	*** Better to call it after a call to hasUser().
	 */
	public User retrieveUserFromDatabase(String name, String password) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = this.connection.prepareStatement("SELECT * FROM user WHERE name LIKE ?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // If a corresponding user is found, checks password validity.
				if (!Objects.equals(rs.getString("password"), password))
					return (null);
				user = new User(rs.getString("name"), rs.getString("password"));
				this.retrieveUserTokens(user, rs.getInt("id"));
				this.retrieveUserAreas(user, rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
		return (user);
	}

	/*
	*** Fills the user's tokens based on those stored, regardless of their validity.
	 */
	public void retrieveUserTokens(User user, int userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = this.connection.prepareStatement("SELECT * FROM token WHERE fk_token_user = ?");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user.addIdToken(ApiUtils.corrTableName.get(rs.getString("api_name")), rs.getString("value"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	private void retrieveUserAreas(User user, int userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Area> areas = new ArrayList<>();

		try {
			pstmt = this.connection.prepareStatement("SELECT * FROM area WHERE fk_area_user = ?");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				areas.add(new Area(user, rs.getString("name")));
				this.retrieveAreaAction(areas.get(areas.size() - 1), areas.get(areas.size() - 1).getDbId(this, userId));
			}
			user.setAreas(areas);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	private void retrieveAreaAction(Area area, int areaId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AAction action = null;

		try {
			if (areaId == -1)
				return;
			pstmt = this.connection.prepareStatement("SELECT * FROM action WHERE fk_action_area = ?");
			pstmt.setInt(1, areaId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// TODO
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	private void retrieveAreaReaction(Area area, int areaId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AReaction reaction = null;

		try {
			if (areaId == -1)
				return;
			pstmt = this.connection.prepareStatement("SELECT * FROM reaction WHERE fk_reaction_area = ?");
			pstmt.setInt(1, areaId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// TODO
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements and results before returning.
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) { }
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
