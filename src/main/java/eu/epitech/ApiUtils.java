package eu.epitech;

import java.util.HashMap;
import java.util.Map;

/*
*** Static constant class providing a way to identify a token based on an enum's value as well as a string.
 */
public final class ApiUtils {

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

	private ApiUtils() {
	}
}
