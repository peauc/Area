package eu.epitech;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IReaction {

    void execute(Token token, JSONObject actionOutput);

    boolean isExecutable(List<String> fields);

    Map<String, FieldType> configFields();

    List<String> requiredFields();

    boolean setConfig(JSONObject conf);

    // Adds the reaction to the database or update its config if one is already present.
    void addToDatabase(DatabaseManager dbm, Area area);

    void removeFromDatabase(DatabaseManager dbm, Area area);

    int getDbId(DatabaseManager dbm, int areaId);

    int getDbId(DatabaseManager dbm, Area area);
}
