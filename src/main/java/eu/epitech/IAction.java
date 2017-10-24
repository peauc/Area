package eu.epitech;

import elemental.json.Json;

import java.util.List;
import java.util.Map;

public interface IAction {
    boolean hasHappened();
    Json whatHappened();
    Map<String, FieldType> configFields();
    List<String> returnedFields();
}
