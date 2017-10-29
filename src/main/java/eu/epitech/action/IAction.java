package eu.epitech.action;

import elemental.json.Json;
import eu.epitech.API.ApiUtils;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IAction {

    boolean hasHappened(Map<ApiUtils.Name, String> tokens);

    List<JSONObject> whatHappened();

    Map<String, FieldType> configFields();

    List<String> returnedFields();

    boolean setConfig(JSONObject conf);

    void addToDatabase(DatabaseManager dbm, Area area);

    void removeFromDatabase(DatabaseManager dbm, Area area);

    int getDbId(DatabaseManager dbm, int areaId);

    int getDbId(DatabaseManager dbm, Area area);
}
