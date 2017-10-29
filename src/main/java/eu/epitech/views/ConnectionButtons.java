package eu.epitech.views;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Token;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import eu.epitech.API.ApiInfo;
import eu.epitech.API.ApiUtils;
import eu.epitech.Listener;
import eu.epitech.NavigatorUI;
import eu.epitech.User;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.OAuthPopupOpener;

public class ConnectionButtons {

    public static OAuthPopupOpener addTwitterButtons(User u) {

        OAuthPopupOpener opener = new OAuthPopupOpener((DefaultApi10a) ApiInfo.TwitterInfo.scribeApi, ApiInfo.TwitterInfo.apiKey, ApiInfo.TwitterInfo.apiSecret);
        opener.addOAuthListener(new OAuthListener() {
            @Override
            public void authSuccessful(Token token, boolean isOAuth20) {
                u.setIdToken(ApiUtils.Name.TWITTER, ((OAuth1AccessToken) token).getToken());
                u.setIdToken(ApiUtils.Name.TWITTER_SECRET, ((OAuth1AccessToken) token).getTokenSecret());
                u.addTokenToDatabase(NavigatorUI.dbm, ApiUtils.Name.TWITTER);
                u.addTokenToDatabase(NavigatorUI.dbm, ApiUtils.Name.TWITTER_SECRET);
            }

            @Override
            public void authDenied(String reason) {
                Notification.show("Twitter login denied :" + reason);
            }
        });
        return (opener);
    }

    private Button makeTestButton(final ApiInfo service, OAuthPopupButton button) {

        // In most browsers "resizable" makes the popup
        // open in a new window, not in a tab.
        // You can also set size with eg. "resizable,width=400,height=300"
        button.setPopupWindowFeatures("resizable,width=600,height=500");
        button.setWidth("150px");

        HorizontalLayout hola = new HorizontalLayout();
        hola.setSpacing(true);
        hola.addComponent(button);

        button.addOAuthListener(new Listener(service, hola));
        return (button);
    }
}
