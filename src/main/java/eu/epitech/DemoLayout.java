package eu.epitech;

import com.github.scribejava.apis.*;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Token;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.OAuthPopupOpener;
import org.vaadin.addon.oauthpopup.buttons.*;

import java.io.IOException;

public class DemoLayout extends VerticalLayout {

	private static final long serialVersionUID = -5419208604938947038L;
	public static String facebookTokenID = null;

	public DemoLayout() {
		setSpacing(true);

		addLinkedInButton();
		addFacebookButtons();
	}

	private void addFacebookButtons() {
		ApiInfo api = new ApiInfo(
				"Facebook",
                FacebookApi.instance(),
                "134598150528779",
				"6576b5e775d9f95f081ad635674a5d47",
				"https://graph.facebook.com/v2.10/me?fields=id,name");
		OAuthPopupButton button = new FacebookButton(api.apiKey, api.apiSecret);
		System.out.println("FacebookLogin");
        OAuthListener toto = new OAuthListener() {
		    @Override
			public void authSuccessful(Token token, boolean b) {
                System.out.println("token");
                Facebook.setToken(token);
                Facebook.setApiInfo(api);
                Facebook.setoAuthService(Facebook.createOAuthService());
                try {
                    String t = Facebook.sendGet(Facebook.getApiInfo().exampleGetRequest);
                    System.out.println(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			@Override
			public void authDenied(String s) {
				System.out.println("No token");
				Notification.show("authDenied" + s, Notification.Type.ERROR_MESSAGE);
			}
		};
        addComponent(button);
        button.addOAuthListener(toto);
    }

//	private void addTwitterButtons() {
//		ApiInfo api = readClientSecrets("/client_secret.twitter.json",
//				"Twitter",
//				TwitterApi.instance(),
//				"https://api.twitter.com/1.1/account/settings.json");
//		OAuthPopupButton button = new TwitterButton(api.apiKey, api.apiSecret);
//		addButton(api, button);
//
//		OAuthPopupOpener opener = new OAuthPopupOpener(TwitterApi.instance(), api.apiKey, api.apiSecret);
//		opener.addOAuthListener(new OAuthListener() {
//			@Override
//			public void authSuccessful(Token token, boolean isOAuth20) {
//				Notification.show("authSuccessful");
//			}
//
//			@Override
//			public void authDenied(String reason) {
//				Notification.show("authDenied");
//			}
//		});
//	}

	private void addLinkedInButton() {
		ApiInfo api = new ApiInfo(
				"LinkedIn",
				LinkedInApi.instance(),
				"86sylwa1zpw1su",
				"87IrVYtvU5t2q4fd",
				"https://api.linkedin.com/v1/people/~?format=json");
		OAuthPopupButton button = new LinkedInButton(api.apiKey, api.apiSecret);
		addButton(api, button);
	}

//	private void addGitHubButton() {
//		ApiInfo api = readClientSecrets("/client_secret.github.json",
//				"GitHub",
//				GitHubApi.instance(),
//				"https://api.github.com/user");
//		if (api == null) return;
//		OAuthPopupButton button = new GitHubButton(api.apiKey, api.apiSecret);
//		addButton(api, button);
//	}

//	private void addGoogleButton() {
//		ApiInfo api = readClientSecrets("/client_secret.google.json",
//				"Google",
//				GoogleApi20.instance(),
//				"https://www.googleapis.com/plus/v1/people/me");
//		/*api = new ApiInfo("Google", GoogleApi20.instance(),
//				"127486145149-q8or6g21t7hok8ngj83re7b1l06u22ff.apps.googleusercontent.com",
//				"Oth69gnVeJOevAoGbYRIxygA",
//				"https://www.googleapis.com/plus/v1/people/me");*/
//		if (api == null) return;
//		OAuthPopupButton button = new GoogleButton(api.apiKey, api.apiSecret, "https://www.googleapis.com/auth/plus.login");
//		addButton(api, button);
//	}

	private void addButton(final ApiInfo service, OAuthPopupButton button) {

		// In most browsers "resizable" makes the popup
		// open in a new window, not in a tab.
		// You can also set size with eg. "resizable,width=400,height=300"
		button.setPopupWindowFeatures("resizable,width=600,height=500");
		button.setWidth("150px");

		HorizontalLayout hola = new HorizontalLayout();
		hola.setSpacing(true);
		hola.addComponent(button);

		addComponent(hola);

		button.addOAuthListener(new Listener(service, hola));
	}

	private class Listener implements OAuthListener {

		private final ApiInfo service;
		private final HorizontalLayout hola;

		private Listener(ApiInfo service, HorizontalLayout hola) {
			this.service = service;
			this.hola = hola;
		}

		@Override
		public void authSuccessful(final Token token, final boolean isOAuth20) {
			Label l = new Label("Authorized.");
			hola.addComponent(l);
			hola.setComponentAlignment(l, Alignment.MIDDLE_CENTER);

			Button testButton = new Button("Test " + service.name + " API");
			testButton.addStyleName(ValoTheme.BUTTON_LINK);
			hola.addComponent(testButton);
			hola.setComponentAlignment(testButton, Alignment.MIDDLE_CENTER);

			testButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 7561258877089832115L;

				@Override
				public void buttonClick(ClickEvent event) {
					GetTestComponent get = new GetTestComponent(service, token);
					Window w = new Window(service.name, get);
					w.center();
					w.setWidth("75%");
					w.setHeight("75%");
					UI.getCurrent().addWindow(w);
				}
			});
		}

		@Override
		public void authDenied(String reason) {
			Label l = new Label("Auth failed.");
			hola.addComponent(l);
			hola.setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		}
	}
}
