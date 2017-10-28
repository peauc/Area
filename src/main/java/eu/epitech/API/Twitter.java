package eu.epitech.API;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import org.json.JSONObject;

import java.io.IOException;

public class Twitter extends AApi {
    private static String handle;

    public static String getHandle(Token token) {
        try {
            String resp = send("https://api.twitter.com/1.1/account/settings.json", Verb.GET, token);
            if (resp == null)
                return (null);
            System.err.println(resp);
            JSONObject jsonObject = new JSONObject(resp);
            return (jsonObject.getString("screen_name"));
        } catch (IOException e) {
            e.printStackTrace();
            return (null);
        }
    }
}
