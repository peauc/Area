package eu.epitech.reaction;

import com.github.scribejava.core.model.Verb;
import com.google.api.client.util.ArrayMap;
import eu.epitech.API.ApiUtils;
import eu.epitech.API.Twitter;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReactionNewTweet extends AReaction {
    private String handle;
    public ReactionNewTweet() {
        super();
        setName("ReactionNewTweet");
        setDescription("Fire a new tweet with the text \"text\"");
        requiredActionFields = new ArrayList<>();
        requiredActionFields.add("text");
        requiredConfigFields = new ArrayMap<>();
        config = new JSONObject();
        config.put("target", "@Nova4u");
        handle = Twitter.getHandle(Twitter.getToken());
    }

    @Override
    public Map<String, FieldType> configFields() {
        return requiredConfigFields;
    }

    @Override
    public List<String> requiredFields() {
        return requiredActionFields;
    }

    @Override
    public ApiUtils.Name getApi() {
        return super.getApi();
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
    public JSONObject getConfig() {
        return super.getConfig();
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
    public void execute(Map<ApiUtils.Name, String> tokens, JSONObject actionOutput) {
        if (Twitter.getIsLoged()) {
            if (handle == null)
                handle = Twitter.getHandle(Twitter.getToken());
            String text = actionOutput.getString("text");
            System.out.println("Execute " + text);
            if (text != null) {
                try {
                    String string = (String) getConfig().get("target");
                    if (string == null || string.isEmpty())
                        string = "";
                    else
                        string += "";
                    System.err.println(handle);
                    System.err.println(text);
                    text = text.replace("@"+handle, "");
                    System.err.println(text);
                    String URLEncodedString = URLEncoder.encode(string + text, "UTF-8");
                    Twitter.send("https://api.twitter.com/1.1/statuses/update.json?status=" + URLEncodedString, Verb.POST, Twitter.getToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isExecutable(List<String> fields) {
        return false;
    }

    @Override
    public boolean setConfig(JSONObject conf) {
        return super.setConfig(conf);
    }
}
