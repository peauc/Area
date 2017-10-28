package eu.epitech;

import com.github.scribejava.core.model.Token;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import eu.epitech.API.ApiInfo;
import org.vaadin.addon.oauthpopup.OAuthListener;

public class Listener implements OAuthListener {

    private final ApiInfo service;
    private final HorizontalLayout hola;

    public Listener(ApiInfo service, HorizontalLayout hola) {
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

        testButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 7561258877089832115L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
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

