package eu.epitech.action;

import elemental.json.Json;
import eu.epitech.FieldType;
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
