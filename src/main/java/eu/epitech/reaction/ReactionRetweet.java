package eu.epitech.reaction;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Verb;
import eu.epitech.API.ApiInfo;
import eu.epitech.API.ApiUtils;
import eu.epitech.API.Twitter;
import eu.epitech.Area;
import eu.epitech.DatabaseManager;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReactionRetweet extends AReaction {
    public ReactionRetweet() {
        super();
        this.api = ApiUtils.Name.TWITTER;
        setName("TWITTER : Retweet on mention");
        setDescription("Each time you are mentioned, the tweet is automatically retweeted");
        requiredActionFields = new ArrayList<>();
        requiredActionFields.add("id");
        requiredActionFields.add("tweet");
        config =  new JSONObject();
    }

    @Override
    public void execute(Map<ApiUtils.Name, String> tokens, JSONObject actionOutput) {
          BigInteger id = actionOutput.getBigInteger("id");
        try {
            Twitter.send("https://api.twitter.com/1.1/statuses/retweet/" + id + ".json", Verb.POST, new OAuth1AccessToken(tokens.get(ApiUtils.Name.TWITTER), tokens.get(ApiUtils.Name.TWITTER_SECRET)), ApiInfo.TwitterInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public Map<String, FieldType> getRequiredConfigFields() {
        return super.getRequiredConfigFields();
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
    public List<String> requiredFields() {
        return super.requiredFields();
    }

    @Override
    public Map<String, FieldType> configFields() {
        return super.configFields();
    }

    @Override
    public boolean setConfig(JSONObject conf) {
        return super.setConfig(conf);
    }
}
