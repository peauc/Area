package eu.epitech;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IAction {

    boolean hasHappened();

    List<JSONObject> whatHappened();

    Map<String, FieldType> configFields();

    List<String> returnedFields();

    boolean setConfig(JSONObject conf);


    // Adds the action to the database or update it if one is already present.
    void addToDatabase(DatabaseManager dbm, Area area);

    void removeFromDatabase(DatabaseManager dbm, Area area);

    int getDbId(DatabaseManager dbm, int areaId);

    int getDbId(DatabaseManager dbm, Area area);
}
