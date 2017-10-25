package eu.epitech;

import elemental.json.Json;

import java.util.List;
import java.util.Map;

public class ActionGCalendar extends AAction {

    @Override
    public boolean hasHappened() {
        return false;
    }

    @Override
    public Json whatHappened() {
        return null;
    }

    @Override
    public Map<String, FieldType> configFields() {
        return null;
    }

    @Override
    public List<String> returnedFields() {
        return null;
    }
}
