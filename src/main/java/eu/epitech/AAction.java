package eu.epitech;

import com.google.common.collect.ImmutableMap;
import elemental.json.Json;
import javafx.util.Pair;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AAction implements IAction {
    protected String name;
    protected String description;
    static protected List<String> fields;
    protected JSONObject configuration = null;
    static private Map<String, FieldType> requiredConfigFields = null;


    public JSONObject getConfig() {
        return configuration;
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

    @Override
    public abstract boolean hasHappened();

    @Override
    public abstract List<JSONObject> whatHappened();

    @Override
    public abstract Map<String, FieldType> configFields();

    @Override
    public abstract List<String> returnedFields();

    @Override
    public boolean setConfiguration(JSONObject conf) {
        configuration = new JSONObject();
        for (Map.Entry<String, FieldType> field : requiredConfigFields.entrySet())
        {
            if (conf.has(field.getKey())) {
                configuration.put(field.getKey(), conf.get(field.getKey()));
            } else {
                configuration = null;
                return false;
            }
        }
        return true;
    }
}
