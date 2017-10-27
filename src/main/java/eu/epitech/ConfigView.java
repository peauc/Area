package eu.epitech;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class ConfigView extends VerticalLayout implements View {
    private final TextField attr1 = new TextField("Mail");
    private final TextField attr2 = new TextField("Name");
    private final Label label = new Label();
    private int count = 0;
    public ConfigView() {
        setSizeFull();
        setSpacing(true);
        addComponent(attr1);
        count++;
        addComponent(attr2);
        count++;
        addComponent(validateButton());
        count++;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() != null)
        {
            addComponent(new Label("Configuration of : " + event.getParameters()), 0);
        }
        Notification.show("Welcome to Configuration Page :)");

    }

    private Button validateButton() {
        Button button = new Button("Validate", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (!attr1.getValue().equals("") && !attr2.getValue().equals(""))
                    getUI().getNavigator().navigateTo("action");
                else {
                    if (getComponentCount() > count)
                        removeComponent(label);
                    label.setCaption("Please fill all fields of configuration");
                    addComponent(label);
                }

            }
        });
        return button;
    }
}
