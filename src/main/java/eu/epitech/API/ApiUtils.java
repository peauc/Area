package eu.epitech.API;

import eu.epitech.action.AAction;
import eu.epitech.action.ActionGCalendar;
import eu.epitech.action.ActionNewTweet;
import eu.epitech.reaction.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiUtils {

	public enum Name {
		TWITTER,
		TWITTER_SECRET,
		LINKEDIN,
		GOOGLE_CALENDAR,
		GOOGLE_GMAIL
	}
	public static final Map<String, Name> corrTableName = new HashMap<String, Name>() {{
		put("TWITTER", Name.TWITTER);
		put("TWITTER_SECRET", Name.TWITTER_SECRET);
		put("LINKEDIN", Name.LINKEDIN);
		put("GOOGLE_CALENDAR", Name.GOOGLE_CALENDAR);
		put("GOOGLE_GMAIL", Name.GOOGLE_GMAIL);
	}};

	public static final Map<String, String> corrTableActionName = new HashMap<String, String>() {{
		put("TWITTER : On mention", "eu.epitech.action.ActionNewTweet");
		put("GOOGLE CALENDAR : On event creation", "eu.epitech.action.ActionGCalendar");
	}};

	public static final Map<String, String> corrTableReactionName = new HashMap<String, String>() {{
		put("GOOGLE CALENDAR : Create an event", "eu.epitech.reaction.ReactionGCalendar");
		put("GOOGLE GMAIL : Send mail", "eu.epitech.reaction.ReactionSendGmail");
		put("TWITTER : On mention, send the same tweet", "eu.epitech.reaction.ReactionNewTweet");
		put("TWITTER : Retweet on mention", "eu.epitech.reaction.ReactionRetweet");
	}};

	public static final ArrayList<AAction> availableActions = new ArrayList<AAction>() {{
		add(new ActionNewTweet());
		add(new ActionGCalendar());
	}};

	public static final ArrayList<AReaction> availableReactions = new ArrayList<AReaction>() {{
		add(new ReactionNewTweet());
		add(new ReactionGCalendar());
		add(new ReactionSendGmail());
		add(new ReactionRetweet());
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
