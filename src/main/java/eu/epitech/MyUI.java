//package eu.epitech;
//
//import javax.servlet.annotation.WebServlet;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.*;
//
///**
// * This UI is the application entry point. A UI may either represent a browser window
// * (or tab) or some part of an HTML page where a Vaadin application is embedded.
// * <p>
// * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
// * overridden to add component to the user interface and initialize non-component functionality.
// */
//@Theme("mytheme")
//public class MyUI extends UI {
//
//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//        final VerticalLayout layout = new VerticalLayout();
//
//        final TextField name = new TextField();
//        name.setCaption("Type your name here:");
//
//        Button button2 = new Button("Hello");
//        button2.setWidth("1000");
//        Button button = new Button("Click Me");
//        button.addClickListener(e -> {
//            layout.addComponent(new Label("Thanks " + name.getValue()
//                    + ", it works!"));
//        });
//
//        layout.addComponents(name, button);
//        layout.addComponent(button2);
//
//        setContent(layout);
//    }
//
//    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {
//    }
//}
