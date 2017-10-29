package eu.epitech.reaction;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import eu.epitech.API.ApiGCalendar;
import eu.epitech.API.ApiUtils;
import eu.epitech.FieldType;
import org.json.JSONObject;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class
ReactionGCalendar extends AReaction {
    public ReactionGCalendar() {
        this.api = ApiUtils.Name.GOOGLE_CALENDAR;
        this.name = "GOOGLE CALENDAR : Create an event";
        this.description = "Create a new event in the associated Calendar account";
        this.requiredActionFields = new ArrayList<>();
        this.requiredActionFields.add("start");
        this.requiredActionFields.add("end");
        this.requiredConfigFields = new HashMap<>();
        this.requiredConfigFields.put("email", FieldType.EMAIL);
        this.config = null;
    }

    private void trySetField(String key, JSONObject o, Event e, String alternative) {
        if (o.has(key))
            e.set(key, o.getString(key));
        else
            e.set(key, alternative);
    }

    private EventDateTime setDateEvent(String key, JSONObject o) {
        EventDateTime e = new EventDateTime()
                .setDate((DateTime) o.get(key));
        if (o.has("timezone"))
            e.setTimeZone(o.getString("timezone"));
        return e;
    }

    private void setAttendees(JSONObject o, Event e) {
        if (o.has("attendees")) {
            List<EventAttendee> attendees = new ArrayList<>();

            for (String attendee : (List<String>)o.get("attendees")) {
                attendees.add(new EventAttendee().setEmail(attendee));
            }
            if (!attendees.isEmpty())
                e.setAttendees(attendees);
        }
    }

    private void setRemainders(JSONObject o, Event e) {
        if (o.has("remindersMinutes") && o.has("remindersMethods")) {
            List<Integer> remaindersMinutes = (List<Integer>) o.get("remindersMinutes");
            List<String> remaindersMethods = (List<String>) o.get("remindersMethods");
            List<EventReminder> reminders = new ArrayList<>();

            if (remaindersMethods.size() == remaindersMinutes.size() &&
                    !remaindersMethods.isEmpty()) {
                for (int i = 0; i < remaindersMethods.size(); ++i) {
                    reminders.add(new EventReminder()
                            .setMethod(remaindersMethods.get(i))
                            .setMinutes(remaindersMinutes.get(i)));
                }

                e.setReminders(new Event.Reminders().setUseDefault(false)
                        .setOverrides(reminders));
            } else {
                e.setReminders(new Event.Reminders().setUseDefault(true));
            }
        } else {
            e.setReminders(new Event.Reminders().setUseDefault(true));
        }
    }

    private void setRecurrences(JSONObject o, Event e) {
        if (o.has("recurrences")) {
            e.setRecurrence((List<String>) o.get("recurrences"));
        }
    }

    private Event translate(JSONObject o) {
        Event e = new Event();

        trySetField("description", o, e, "AREA CREATED");
        trySetField("location", o, e, "SOMEWHERE");
        trySetField("summary", o, e, "EVENT AUTOMATICALLY CREATED");

        e.setStart(setDateEvent("start", o));
        e.setEnd(setDateEvent("end", o));

        setAttendees(o, e);
        setRemainders(o, e);
        setRecurrences(o, e);

        return e;
    }

    @Override
    public void execute(Map<ApiUtils.Name, String> tokens, JSONObject actionOutput) {
        Calendar calendar;
        try {
            calendar = ApiGCalendar.getCalendarService();
        } catch (IOException e) {
            Logger.error("Error occurred while getting Calendar service");
            Logger.debug(e);
            return;
        }

        Event event = translate(actionOutput);
        try {
            calendar.events().insert("primary", event).execute();
        } catch (IOException e) {
            Logger.error("Error occured during Event insertion");
            Logger.debug(e);
        }
    }
}
