package eu.epitech;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AAction implements IAction {
	protected ApiUtils.Name api;
    protected String name;
    protected String description;
    protected JSONObject config = null;
    protected JSONObject previousDatas = null;
	protected List<String> fields;
    protected Map<String, FieldType> requiredConfigFields = null;

    public AAction() {

	}

	public ApiUtils.Name getApi() {
		return api;
	}

	public JSONObject getConfig() {
        return config;
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

	public JSONObject getPreviousDatas() {
		return previousDatas;
	}

	public void setPreviousDatas(JSONObject previousDatas) {
		this.previousDatas = previousDatas;
	}

	/**
	 * Adds the action to the database or update it if one is already present.
	 * @param dbm
	 * @param area
	 */
	@Override
	public void addToDatabase(DatabaseManager dbm, Area area) {
		PreparedStatement pstmt = null;
		int areaId;
		int actionId;

		try {
			areaId = area.getDbId(dbm);
			if (areaId == -1) // area not found
				return;
			if ((actionId = this.getDbId(dbm, area)) == -1) { // action not found -> create a new db entry
				pstmt = dbm.getConnection().prepareStatement("INSERT INTO action(fk_action_area, api_name, name, description, config, previous_state) VALUES (?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, areaId);
				pstmt.setString(2, this.api.name());
				pstmt.setString(2, this.name);
				pstmt.setString(3, this.description);
				pstmt.setString(4, this.config.toString());
				pstmt.setString(5, (this.previousDatas != null) ? this.previousDatas.toString() : null);
				pstmt.executeUpdate();
			} else { // action found -> update config and previous_state
				pstmt = dbm.getConnection().prepareStatement("UPDATE action SET config = ?, previous_state = ? WHERE fk_action_area = ?");
				pstmt.setString(1, this.config.toString());
				pstmt.setString(2, (this.previousDatas != null) ? this.previousDatas.toString() : null);
				pstmt.setInt(3, actionId);
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
	 * Removes the action from the database
	 * Should only be called by the Area that contains it
	 * @param dbm
	 * @param area
	 */
	@Override
	public void removeFromDatabase(DatabaseManager dbm, Area area) {
		PreparedStatement pstmt = null;
		int actionId;

		try {
			if ((actionId = this.getDbId(dbm, area)) == -1)
				return;
			pstmt = dbm.getConnection().prepareStatement("DELETE FROM action WHERE id = ?");
			pstmt.setInt(1, actionId);
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
		int actionId = -1;

		try {
			if (areaId == -1) // area not found
				return (-1);
			pstmt = dbm.getConnection().prepareStatement("SELECT id FROM action WHERE fk_action_area = ? AND name LIKE ?");
			pstmt.setInt(1, areaId);
			pstmt.setString(2, this.name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				actionId = rs.getInt("id");
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
		return (actionId);
	}

	@Override
	public int getDbId(DatabaseManager dbm, Area area) {
		return this.getDbId(dbm, area.getDbId(dbm));
	}

    @Override
    public abstract boolean hasHappened();

    @Override
    public abstract List<JSONObject> whatHappened();

    @Override
    public abstract Map<String, FieldType> configFields();

    @Override
    public abstract List<String> returnedFields();

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
