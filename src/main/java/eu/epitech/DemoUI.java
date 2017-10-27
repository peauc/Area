package eu.epitech;
import javax.servlet.annotation.WebServlet;

import com.github.scribejava.apis.FacebookApi;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.TimerTask;

@Push
@PreserveOnRefresh
@Theme("valo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
	public static class Servlet extends VaadinServlet {  }

	private VerticalLayout layout;

	@Override
	public void doRefresh(VaadinRequest request) {
		super.doRefresh(request);
		System.out.println("Refresh");
	}

	@Override
	protected void init(VaadinRequest request) {
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);

		layout.addComponent(new DemoLayout());
		new test().start();
	}
}

class test extends Thread {
	@Override
	public void run() {
			java.util.Timer t = new java.util.Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					if (DemoLayout.facebookTokenID != null)
						System.out.println(DemoLayout.facebookTokenID);
				}}, 1000,1000);
	}
}
