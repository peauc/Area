package eu.epitech.API;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

import java.io.IOException;
import java.time.Instant;

public abstract class AApi {
    private static com.github.scribejava.core.model.Token token = null;
    private static OAuthService oAuthService = null;
    private static ApiInfo apiInfo = null;
    private static long expiresAt = -1;

    public static Boolean getIsLoged() {
        return isLoged;
    }

    public static void setIsLoged(Boolean isLoged) {
        AApi.isLoged = isLoged;
    }

    private static Boolean isLoged = false;


    public static com.github.scribejava.core.model.Token getToken() {
        return token;
    }

    public static void setToken(com.github.scribejava.core.model.Token token) {
        AApi.token = token;
        if (oAuthService instanceof OAuth20Service) {
            expiresAt = Instant.now().toEpochMilli() + ((OAuth2AccessToken) token).getExpiresIn();
        }
    }

    public static OAuthService getoAuthService() {
        return oAuthService;
    }

    public static void setoAuthService(OAuthService oAuthService) {
        AApi.oAuthService = oAuthService;
    }

    public Boolean isTokenValid() {
        long now = Instant.now().toEpochMilli();
        if (expiresAt > now) {
            return (false);
        }
        return (true);
    }

    public static ApiInfo getApiInfo() {
        return apiInfo;
    }

    public static void setApiInfo(ApiInfo apiInfo) {
        AApi.apiInfo = apiInfo;
    }

    public static OAuthService createOAuthService() {
        ServiceBuilder sb = new ServiceBuilder().
                apiKey(apiInfo.apiKey).
                apiSecret(apiInfo.apiSecret).
                callback(apiInfo.exampleGetRequest);
        if (apiInfo.scribeApi instanceof DefaultApi10a) {
            return sb.build((DefaultApi10a) apiInfo.scribeApi);
        } else {
            return sb.build((DefaultApi20) apiInfo.scribeApi);
        }
    }

    public static String send(String URL, Verb mode) throws IOException {
        if (getoAuthService() == null || getToken() == null) {
            return null;
        }
        final OAuthRequest request = new OAuthRequest(mode, URL, getoAuthService());
        if (getoAuthService() instanceof OAuth20Service) {
            ((OAuth20Service) getoAuthService()).signRequest((OAuth2AccessToken)getToken(), request);
        } else {
            ((OAuth10aService) getoAuthService()).signRequest((OAuth1AccessToken)getToken(), request);
        }
        Response resp = request.send();
        try {
            return (resp.getBody());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return (resp.getBody());
        }
    }
}
