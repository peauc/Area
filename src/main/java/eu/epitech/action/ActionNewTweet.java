package eu.epitech.action;

import com.google.common.collect.ImmutableList;
import eu.epitech.API.ApiUtils;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ActionNewTweet extends AAction {
    List<String> params = ImmutableList.of();

    public ActionNewTweet() {
        super();
    }

    @Override
    public ApiUtils.Name getApi() {
        return super.getApi();
    }

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
    public JSONObject getPreviousDatas() {
        return super.getPreviousDatas();
    }

    @Override
    public void setPreviousDatas(JSONObject previousDatas) {
        super.setPreviousDatas(previousDatas);
    }

    @Override
    public void addToDatabase(DatabaseManager dbm, Area area) {
        super.addToDatabase(dbm, area);
    }

    @Override
    public void removeFromDatabase(DatabaseManager dbm, Area area) {
        super.removeFromDatabase(dbm, area);
    }

    @Override
    public int getDbId(DatabaseManager dbm, int areaId) {
        return super.getDbId(dbm, areaId);
    }

    @Override
    public int getDbId(DatabaseManager dbm, Area area) {
        return super.getDbId(dbm, area);
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
    public boolean setConfig(JSONObject conf) {
        return super.setConfig(conf);
    }
}
