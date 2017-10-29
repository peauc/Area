package eu.epitech.views;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.Token;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import eu.epitech.API.ApiInfo;
import eu.epitech.API.Twitter;
import eu.epitech.Listener;
import eu.epitech.action.ActionNewTweet;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.OAuthPopupOpener;

public class ConnectionButtons {

    public OAuthPopupOpener addTwitterButtons() {

        OAuthPopupOpener opener = new OAuthPopupOpener((DefaultApi10a) ApiInfo.TwitterInfo.scribeApi, ApiInfo.TwitterInfo.apiKey, ApiInfo.TwitterInfo.apiSecret);
        opener.addOAuthListener(new OAuthListener() {
            @Override
            public void authSuccessful(Token token, boolean isOAuth20) {
                Notification.show("authSuccessful");
                Twitter.setToken(token);
                Twitter.setApiInfo(ApiInfo.TwitterInfo);
                Twitter.setoAuthService(Twitter.createOAuthService());
                Twitter.setIsLoged(true);
                Twitter.getHandle(Twitter.getToken());
                ActionNewTweet actionNewTweet = new ActionNewTweet();
                actionNewTweet.hasHappened();
            }

            @Override
            public void authDenied(String reason) {
                Notification.show("authDenied");
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
