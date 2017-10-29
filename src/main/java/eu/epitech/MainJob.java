package eu.epitech;

import eu.epitech.API.ApiUtils;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static java.lang.System.setOut;


public class MainJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		ArrayList<User> users;
		DatabaseManager dbm;

		try {
			dbm = new DatabaseManager();
			if (dbm.getConnection() == null) {
				System.out.println("Cannot connect to database, ABORT");
				return;
			}
			out.println("Executing job in background...");
			users = dbm.retrieveAllUsersFromDatabase();
			for (User user : users) {
				System.out.println("Username : " + user.getName());
				System.out.println("Tokens :");
				for (Map.Entry<ApiUtils.Name, String> entry : user.getIdTokens().entrySet()) {
					System.out.println("    Api : " + entry.getKey().name() + "; Token : " + entry.getValue());
				}
				for (Area area : user.getAreas()) {
					if (area.getAction() != null && area.getReaction() != null) {
						System.out.println("    Name : " + area.getName());
						System.out.println("    Action :");
						System.out.println("        Api : " + area.getAction().getApi().name());
						System.out.println("        Name : " + area.getAction().getName());
						System.out.println("        Description : " + area.getAction().getDescription());
						System.out.println("    Reaction :");
						System.out.println("        Api : " + area.getReaction().getApi().name());
						System.out.println("        Name : " + area.getReaction().getName());
						System.out.println("        Description : " + area.getReaction().getDescription());

						if (area.getAction().hasHappened(user.getIdTokens())) { // Checks if action has been triggered
							System.out.println("HAS HAPPENED ! GONNA SPAM BROUT !");
							List<JSONObject> events = area.getAction().whatHappened();
							if (events != null) {
								for (JSONObject event : events) {
									area.getReaction().execute(user.getIdTokens(), event);
								}
							}
						}
					}
				}
			}
			out.println("Done executing job.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}