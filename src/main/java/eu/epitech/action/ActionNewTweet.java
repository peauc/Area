package eu.epitech.action;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Verb;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.ImmutableList;
import eu.epitech.API.ApiInfo;
import eu.epitech.API.ApiUtils;
import eu.epitech.API.Twitter;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionNewTweet extends AAction {
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    List<String> params = ImmutableList.of("text", "id");
    List<JSONObject> whatHappened = new ArrayList<>();

    public ActionNewTweet() {
        super();
        setName("TWITTER : On mention");
        setApi(ApiUtils.Name.TWITTER);
        setDescription("Fire whenever you are mentioned on twitter");
        this.fields = new ArrayList<>();
        this.fields.add("text");
    }

    public List<JSONObject> getWhatHappened() {
        return whatHappened;
    }

    public void setWhatHappened(List<JSONObject> whatHappened) {
        this.whatHappened = whatHappened;
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
    public boolean hasHappened(Map<ApiUtils.Name, String> tokens) {
        whatHappened.clear();
        if (previousDatas == null) {
            try {
                initialize(tokens);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return (false);
            }
        } else {
            try {
                String tmp = Twitter.send("https://api.twitter.com/1.1/statuses/mentions_timeline.json", Verb.GET, new OAuth1AccessToken(tokens.get(ApiUtils.Name.TWITTER), tokens.get(ApiUtils.Name.TWITTER_SECRET)), ApiInfo.TwitterInfo);
                if (tmp != null) {
                    JSONArray array = new JSONArray(tmp);
                    for (Integer i = 0; i < array.length(); i++) {
                        System.out.println(array.getJSONObject(i));
                        System.out.println(previousDatas);
                        System.out.println("\n\n");
                        if (array.getJSONObject(i).get("id").equals(previousDatas.get("id"))) {
                            System.err.println("Found matching data " + i);
                            for (Integer j = 0; j < i; j++) {
                                getWhatHappened().add((JSONObject) array.get(j));
                            }
                            if (whatHappened.size() > 0)
                                setPreviousDatas(getWhatHappened().get(0));
                            else
                                return (false);
                            return (true);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private void initialize(Map<ApiUtils.Name, String> tokens) throws IOException {
        String tmp = Twitter.send("https://api.twitter.com/1.1/statuses/mentions_timeline.json?", Verb.GET, new OAuth1AccessToken(tokens.get(ApiUtils.Name.TWITTER), tokens.get(ApiUtils.Name.TWITTER_SECRET)), ApiInfo.TwitterInfo);
        if (tmp == null) {
            System.out.println("tmp is null");
            setInitialized(false);
            return;
        }
        JSONArray response = new JSONArray(tmp);
        if (response.length() == 0) {
            System.out.println("Json is empty");
            setInitialized(true);
        }
        System.out.println(previousDatas);
        System.out.println("Initalized\n\n");
        setInitialized(true);
        JSONObject object = response.getJSONObject(0);
        previousDatas = object;
    }
    @Override
    public List<JSONObject> whatHappened() {
        return whatHappened;
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
