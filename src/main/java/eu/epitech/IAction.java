package eu.epitech;

import elemental.json.Json;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IAction {
    boolean hasHappened();
    List<JSONObject> whatHappened();
    Map<String, FieldType> configFields();
    List<String> returnedFields();
    boolean setConfiguration(JSONObject conf);
}
