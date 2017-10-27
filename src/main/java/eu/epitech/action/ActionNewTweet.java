package eu.epitech.action;

import com.github.scribejava.core.model.Verb;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.ImmutableList;
import eu.epitech.API.ApiUtils;
import eu.epitech.API.Twitter;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ActionNewTweet extends AAction {
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

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
        if (previousDatas == null) {
            try {
                initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private JSONObject initialize() throws IOException {
        String tmp = Twitter.send("https://api.twitter.com/1.1/statuses/mentions_timeline.json?count=1", Verb.GET);
        if (tmp == null)
            return null;
        JSONObject response = new JSONObject(tmp);
        if (response.isNull("text")) {
            return null;
        }
        previousDatas = response;

        return (response);
    }
    List<String> params = ImmutableList.of("text", "id");
}
