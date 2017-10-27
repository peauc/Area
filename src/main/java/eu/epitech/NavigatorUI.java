package eu.epitech;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@Theme("mytheme")
public class NavigatorUI extends UI {
    public Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(),viewDisplay);

        navigator.addView("", new LoginView());
        navigator.addView("action", new ActionView());
        navigator.addView("reaction", new ReactionView());
        navigator.addView("config", new ConfigView());
        navigator.addView("account", new CreateAccountView());
        navigator.addView("login", new LoginView());

        ServletContext ctx = VaadinServlet.getCurrent().getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute("org.quartz.impl.StdSchedulerFactory.KEY");
        try {
            Scheduler scheduler = factory.getScheduler("LenartScheduler");
            if (scheduler != null) {
                JobDetail jobDetail =
                        newJob(MainJob.class).storeDurably().withIdentity("MAIN_JOB").withDescription("Main Job to Perform")
                                .build();

                Trigger trigger =
                        newTrigger().forJob(jobDetail).withIdentity("MAIN_JOB_TRIGG").withDescription("Trigger for Main Job")
                                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever()).startNow().build();

                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
