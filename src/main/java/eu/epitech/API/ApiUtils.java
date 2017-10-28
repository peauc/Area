package eu.epitech.API;

import eu.epitech.action.AAction;
import eu.epitech.action.ActionExample;
import eu.epitech.reaction.AReaction;
import eu.epitech.reaction.ReactionExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiUtils {

	public enum Name {
		FACEBOOK,
		TWITTER,
		LINKEDIN,
		GOOGLE_CALENDAR
	}

	public static final Map<String, Name> corrTableName = new HashMap<String, Name>() {{
		put("FACEBOOK", Name.FACEBOOK);
		put("TWITTER", Name.TWITTER);
		put("LINKEDIN", Name.LINKEDIN);
		put("GOOGLE_CALENDAR", Name.GOOGLE_CALENDAR);
	}};

	public static final Map<String, String> corrTableActionName = new HashMap<String, String>() {{
		put("FACEBOOK : On friend request", "eu.epitech.action.ActionExample");
		put("GOOGLE CALENDAR : On event creation", "eu.epitech.action.ActionGCalendar");
	}};

	public static final Map<String, String> corrTableReactionName = new HashMap<String, String>() {{
		put("TWITTER : Send private message", "eu.epitech.reaction.ReactionExample");
		put("GOOGLE CALENDAR : Create an event", "eu.epitech.reaction.ReactionGCalendar");
	}};

	public static final ArrayList<AAction> availableActions = new ArrayList<AAction>() {{
		add(new ActionExample());
	}};

	public static final ArrayList<AReaction> availableReactions = new ArrayList<AReaction>() {{
		add(new ReactionExample());
	}};

	public static AAction createActionFromName(String name) {
		AAction action = null;
		String className;

		try {
			if ((className = ApiUtils.corrTableActionName.get(name)) == null) {
				System.err.println(name + " does not correspond to any action class");
				return (null);
			}
			action = (AAction) Class.forName (className).newInstance();
		} catch (Exception e) {
			System.err.println("Class couldn't be created");
		}
		return (action);
	}

	public static AReaction createReactionFromName(String name) {
		AReaction reaction = null;
		String className;

		try {
			if ((className = ApiUtils.corrTableReactionName.get(name)) == null) {
				System.err.println(name + " does not correspond to any reaction class");
				return (null);
			}
			reaction = (AReaction) Class.forName (className).newInstance();
		} catch (Exception e) {
			System.err.println("Class couldn't be created");
		}
		return (reaction);
	}

	private ApiUtils() {
	}
}
