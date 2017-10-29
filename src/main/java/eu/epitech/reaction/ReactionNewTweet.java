package eu.epitech.reaction;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Verb;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ArrayMap;
import com.google.common.collect.ImmutableList;
import eu.epitech.API.ApiInfo;
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
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    List<String> params = ImmutableList.of("text", "id");
    List<JSONObject> whatHappened = new ArrayList<>();

    public ReactionNewTweet() {
        super();
        this.api = ApiUtils.Name.TWITTER;
        setName("TWITTER : On mention, send the same tweet");
        setDescription("Fire a new tweet with the text \"text\" and to the user(s) in \"target\"");
        requiredActionFields = new ArrayList<>();
        requiredActionFields.add("text");
        requiredConfigFields = new ArrayMap<>();
        requiredConfigFields.put("target", FieldType.STRING);
        config = new JSONObject();
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
        if (tokens == null || tokens.size() == 0 || tokens.get(ApiUtils.Name.TWITTER_SECRET) == null || tokens.get(ApiUtils.Name.TWITTER) == null)
            return;
        if (tokens.get(ApiUtils.Name.TWITTER) != null) {
            if (handle == null)
                handle = Twitter.getHandle(new OAuth1AccessToken(tokens.get(ApiUtils.Name.TWITTER), tokens.get(ApiUtils.Name.TWITTER_SECRET)));
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
                    Twitter.send("https://api.twitter.com/1.1/statuses/update.json?status=" + URLEncodedString, Verb.POST, new OAuth1AccessToken(tokens.get(ApiUtils.Name.TWITTER), tokens.get(ApiUtils.Name.TWITTER_SECRET)), ApiInfo.TwitterInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean setConfig(JSONObject conf) {
        return super.setConfig(conf);
    }
}
