package eu.epitech.views;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.themes.ValoTheme;
import eu.epitech.*;

import static eu.epitech.NavigatorUI.dbm;

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
public class LoginView extends AbsoluteLayout implements View {
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private Label labelTmp = new Label();

    // Count nb of object on the View
    private int countObject = 5;

    public LoginView() throws ClassNotFoundException {
        setSizeFull();
        setHeight("500px");
        setWidth("500px");

        addComponent(new Label("Welcome to the AREA"), "left: 50px; top: 0px;");
        addComponent(username, "left: 50px; top: 50px;");
        addComponent(password, "left: 50px; top: 120px;");
        addComponent(loginButton(), "left: 100px; top: 190px;");
        addComponent(accountButton(), "left: 35px; top: 250px;");
        addComponent(new DemoLayout(), "left: 50px; top: 350px;");
        setResponsive(true);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        System.out.println("Enter in loginView");

        try {
            Stock stock = (Stock) NavigatorUI.readData(getUI());
            if (stock != null) {
                if (stock.getPrompt() != null) {
                    addComponent(new Label(stock.getPrompt()), "left: 50px; top: 300;");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    ** Test if user have set a valid username, and the good password associate
     */
    public void testBadPassword() {
        User user;

        if (dbm == null || dbm.getConnection() == null) {
            if (!labelTmp.getValue().equals("Error : Cannot access database")) {
                labelTmp.setCaption("Error : Cannot access database");
                addComponent(labelTmp, "top: 350px; left: 50px;");
            }
        } else if (username.getValue().equals("") ||
                password.getValue().equals("")) {
            if (getComponentCount() > countObject)
                removeComponent(labelTmp);
            labelTmp.setCaption("Please Username or password missing");
            addComponent(labelTmp, "top: 350px; left: 50px;");
        } else if ((user = dbm.retrieveUserFromDatabase(username.getValue(), password.getValue())) == null) { // Condition to test, is the username exist on the DB, and if the password is good
            if (getComponentCount() > countObject)
                removeComponent(labelTmp);
            labelTmp.setCaption("The username or password is incorrect");
            addComponent(labelTmp, "top: 350px; left: 50px;");
        } else {
            NavigatorUI.putData(getUI(), new Stock(user, null, null, null));
            getUI().getNavigator().navigateTo("action");
        }
    }

    private Button loginButton()
    {
        Button button = new Button("Login", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                testBadPassword();
            }
        });
        return button;
    }

    private Button accountButton()
    {
        Button button = new Button("Create Account", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (dbm == null || dbm.getConnection() == null) {
                    if (!labelTmp.getValue().equals("Error : Cannot access database")) {
                        labelTmp.setCaption("Error : Cannot access database");
                        addComponent(labelTmp, "top: 350px; left: 50px;");
                    }
                    return;
                }
                NavigatorUI.putData(getUI(), new Stock(null, null, null, null));
                getUI().getNavigator().navigateTo("account");
            }
        });
        button.setStyleName(ValoTheme.BUTTON_LINK);
        return button;
    }
}
