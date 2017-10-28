package eu.epitech;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import eu.epitech.API.Twitter;
import eu.epitech.action.ActionNewTweet;
import eu.epitech.reaction.ReactionNewTweet;
import eu.epitech.views.*;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.util.TimerTask;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@Push
@Theme("mytheme")
public class NavigatorUI extends UI {
    public Navigator navigator;
    ActionNewTweet action = new ActionNewTweet();
    ReactionNewTweet reaction = new ReactionNewTweet();
    /*
    ** The three following methods / attributes can be use to pass Object between different view
    * ex : Object => information User : UserInfo user;
    * pudData(getUI(), user);
    * getUI().getNavigator().navigateTO(view);
    *
    * Then in the other view, on the Override enter methods:
    *     public void enter(ViewChangeListener.ViewChangeEvent event) {
    *           Object obj = readData(getUI());
    *       }
     */
    private static final String TEMPORARY_VARIABLE = "temp";

    public static void putData(com.vaadin.ui.UI ui, Object object)
    {
        ui.getSession().setAttribute(TEMPORARY_VARIABLE, object);
    }

    public static Object readData(com.vaadin.ui.UI ui)
    {
        Object object = ui.getSession().getAttribute(TEMPORARY_VARIABLE);
        ui.getSession().setAttribute(TEMPORARY_VARIABLE, null);
        return object;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(),viewDisplay);

        try {
            navigator.addView("", new LoginView());
            navigator.addView("action", new ActionView());
            navigator.addView("reaction", new ReactionView());
            navigator.addView("config", new ConfigView());
            navigator.addView("account", new CreateAccountView());
            navigator.addView("login", new LoginView());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Twitter.getIsLoged()) {
                    if (action.hasHappened()) {
                        System.out.println("Something happened");
                        for (JSONObject object : action.getWhatHappened()) {
                            System.out.println("Executing");
                            reaction.execute("", object);
                        }
                    }
                }
            }
        }, 10000, 10000);
//
//        ServletContext ctx = VaadinServlet.getCurrent().getServletContext();
//        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute("org.quartz.impl.StdSchedulerFactory.KEY");
//        try {
//            Scheduler scheduler = factory.getScheduler("LenartScheduler");
//            if (scheduler != null) {
//                JobDetail jobDetail =
//                        newJob(MainJob.class).storeDurably().withIdentity("MAIN_JOB").withDescription("Main Job to Perform")
//                                .build();
//
//                Trigger trigger =
//                        newTrigger().forJob(jobDetail).withIdentity("MAIN_JOB_TRIGG").withDescription("Trigger for Main Job")
//                                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever()).startNow().build();
//
//                scheduler.scheduleJob(jobDetail, trigger);
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
