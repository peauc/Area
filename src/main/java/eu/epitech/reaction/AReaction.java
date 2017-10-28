package eu.epitech.reaction;

import eu.epitech.API.ApiUtils;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AReaction implements IReaction {
	protected ApiUtils.Name api;
    protected String name;
    protected String description;
    protected JSONObject config = null;
	protected List<String> requiredActionFields = null;
	protected Map<String, FieldType> requiredConfigFields = null;

	public AReaction() {

	}

	public ApiUtils.Name getApi() {
		return api;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject getConfig() {
        return config;
    }

	public Map<String, FieldType> getRequiredConfigFields() {
		return requiredConfigFields;
	}

	/**
	 * Adds the reaction to the database or update it's config if one is already present.
	 * @param dbm
	 * @param area
	 */
	@Override
	public void addToDatabase(DatabaseManager dbm, Area area) {
		PreparedStatement pstmt = null;
		int areaId = -1;
		int reactionId = -1;

		try {
			areaId = area.getDbId(dbm);
			if (areaId == -1) // area not found
				return;
			if ((reactionId = this.getDbId(dbm, area)) == -1) { // action not found -> create a new db entry
				pstmt = dbm.getConnection().prepareStatement("INSERT INTO reaction(fk_reaction_area, api_name, name, description, config) VALUES (?, ?, ?, ?, ?)");
				pstmt.setInt(1, areaId);
				pstmt.setString(2, this.api.name());
				pstmt.setString(3, this.name);
				pstmt.setString(4, this.description);
				pstmt.setString(5, (this.config != null) ? this.config.toString() : null);
				pstmt.executeUpdate();
			} else { // action found -> update config
				pstmt = dbm.getConnection().prepareStatement("UPDATE action SET config = ? WHERE fk_action_area = ?");
				pstmt.setString(1, (this.config != null) ? this.config.toString() : null);
				pstmt.setInt(2, reactionId);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements before returning.
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	/**
	 * Removes the reaction from the database
	 * Should only be called by the Area that contains it
	 * @param dbm
	 * @param area
	 */
	@Override
	public void removeFromDatabase(DatabaseManager dbm, Area area) {
		PreparedStatement pstmt = null;
		int reactionId = -1;

		try {
			if ((reactionId = this.getDbId(dbm, area)) == -1)
				return;
			pstmt = dbm.getConnection().prepareStatement("DELETE FROM reaction WHERE id = ?");
			pstmt.setInt(1, reactionId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally { // Close statements before returning.
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignored) { }
			}
		}
    }

	@Override
	public int getDbId(DatabaseManager dbm, int areaId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int reactionId = -1;

		try {
			if (areaId == -1) // area not found
				return (-1);
			pstmt = dbm.getConnection().prepareStatement("SELECT id FROM reaction WHERE fk_reaction_area = ? AND name LIKE ?");
			pstmt.setInt(1, areaId);
			pstmt.setString(2, this.name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				reactionId = rs.getInt("id");
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
		return (reactionId);
	}

	@Override
	public int getDbId(DatabaseManager dbm, Area area) {
		return this.getDbId(dbm, area.getDbId(dbm));
	}

	@Override
	public boolean isExecutable(List<String> fields) {
		for (String requiredField : requiredActionFields) {
			if (fields.indexOf(requiredField) == -1)
				return false;
		}
		return true;
	}

	@Override
	public List<String> requiredFields() {
		return requiredActionFields;
	}

	@Override
	public Map<String, FieldType> configFields() {
		return requiredConfigFields;
	}



	@Override
	public boolean setConfig(JSONObject conf) {
		config = new JSONObject();
		for (Map.Entry<String, FieldType> field : requiredConfigFields.entrySet())
		{
			if (conf.has(field.getKey())) {
				config.put(field.getKey(), conf.get(field.getKey()));
			} else {
				config = null;
				return false;
			}
		}
		return true;
	}
}
