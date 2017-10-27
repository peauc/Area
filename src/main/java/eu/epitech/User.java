package eu.epitech;

import eu.epitech.API.ApiUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
	private final String name;
	private final String password;
	private Map<ApiUtils.Name, String> idTokens;
	private ArrayList<Area> areas;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.idTokens = new HashMap<>();
		this.areas = new ArrayList<>();
	}

	/*
	*** Add the user to database.
	*** Will not update anything if the user is already present.
	 */
	public void  addToDatabase(DatabaseManager dbm) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = dbm.getConnection().prepareStatement("SELECT id FROM user WHERE name LIKE ?");
			pstmt.setString(1, this.name);
			rs = pstmt.executeQuery();
			if (!rs.first()) { // Add the user to database if he's not already present.
				rs.close();
				pstmt.close();
				pstmt = dbm.getConnection().prepareStatement("INSERT INTO user(name, password) VALUES (?, ?)");
				pstmt.setString(1, this.name);
				pstmt.setString(2, this.password);
				pstmt.executeUpdate();
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

	/*
	*** Add a token to the database.
	*** Will update a token's value if it finds one already belonging to the user.
	 */
	public void addTokenToDatabase(DatabaseManager dbm, ApiUtils.Name apiName, String token) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int clientId;
		int logId;

		try {
			pstmt = dbm.getConnection().prepareStatement("SELECT id FROM user WHERE name LIKE ?");
			pstmt.setString(1, this.name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				clientId = rs.getInt("id");
				rs.close();
				pstmt.close();
				pstmt = dbm.getConnection().prepareStatement("SELECT id FROM token WHERE fk_token_user = ? AND api_name LIKE ?");
				pstmt.setInt(1, clientId);
				pstmt.setString(2, apiName.name());
				rs = pstmt.executeQuery();
				if (!rs.next()) { // no token found -> add one
					pstmt.close();
					pstmt = dbm.getConnection().prepareStatement("INSERT INTO token(fk_token_user, api_name, value) VALUES (?, ?, ?)");
					pstmt.setInt(1, clientId);
					pstmt.setString(2, apiName.name());
					pstmt.setString(3, token);
					pstmt.executeUpdate();
				} else { // token already set -> update it's value
					logId = rs.getInt("id");
					pstmt.close();
					pstmt = dbm.getConnection().prepareStatement("UPDATE token SET value = ? WHERE id = ?");
					pstmt.setString(1, token);
					pstmt.setInt(2, logId);
					pstmt.executeUpdate();
				}
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

	public String getName() {
		return name;
	}

	public String getIdToken(ApiUtils.Name api) {
		return (this.idTokens.get(api));
	}

	public Map<ApiUtils.Name, String> getIdTokens() {
		return idTokens;
	}

	public void addIdToken(ApiUtils.Name api, String token) {
		if (api != null) {
			if (this.idTokens.get(api) != null) {
				this.idTokens.remove(api);
			}
			this.idTokens.put(api, token);
		}
	}

	public void removeIdToken(ApiUtils.Name api) {
		this.idTokens.remove(api);
	}

	public void setIdTokens(Map<ApiUtils.Name, String> idTokens) {
		this.idTokens = idTokens;
	}

	public ArrayList<Area> getAreas() {
		return areas;
	}

	public void removeArea(Area area) {
		this.areas.remove(area);
	}

	public void addArea(Area area) {
		this.areas.add(area);
	}

	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
}
