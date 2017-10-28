package eu.epitech;

import com.vaadin.ui.VerticalLayout;
import eu.epitech.views.ConnectionButtons;

public class DemoLayout extends VerticalLayout {

    private static final long serialVersionUID = -5419208604938947038L;
    public static String facebookTokenID = null;

    public DemoLayout() {
        ConnectionButtons toto = new ConnectionButtons();
        addComponent(toto.addTwitterButtons());
    }
}

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

