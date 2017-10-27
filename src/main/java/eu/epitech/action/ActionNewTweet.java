package eu.epitech.action;

import com.google.common.collect.ImmutableList;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ActionNewTweet extends AAction {
    List<String> params = ImmutableList.of();

    @Override
    public JSONObject getConfig() {
        return super.getConfig();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public boolean hasHappened() {
        return false;
    }

    @Override
    public List<JSONObject> whatHappened() {
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

    @Override
    public boolean setConfiguration(JSONObject conf) {
        return super.setConfiguration(conf);
    }
}
