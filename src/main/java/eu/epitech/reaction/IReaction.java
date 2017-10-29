package eu.epitech.reaction;

import eu.epitech.API.ApiUtils;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IReaction {
    void execute(Map<ApiUtils.Name, String> tokens, JSONObject actionOutput);

    boolean isExecutable(List<String> fields);

    Map<String, FieldType> configFields();

    List<String> requiredFields();

    boolean setConfig(JSONObject conf);

    void addToDatabase(DatabaseManager dbm, Area area);

    void removeFromDatabase(DatabaseManager dbm, Area area);

    int getDbId(DatabaseManager dbm, int areaId);

    int getDbId(DatabaseManager dbm, Area area);
}
