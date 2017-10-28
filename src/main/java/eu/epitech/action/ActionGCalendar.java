
package eu.epitech.action;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import eu.epitech.API.ApiGCalendar;
import eu.epitech.API.ApiUtils;
import eu.epitech.FieldType;
import org.json.JSONObject;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionGCalendar extends AAction {
    private String lastSyncToken = null;
    private long lastSyncDate;
    private ArrayList<JSONObject> eventsStore = new ArrayList<>();

    public ActionGCalendar() {
        this.api = ApiUtils.Name.GOOGLE_CALENDAR;
        this.name = "GOOGLE CALENDAR : on event creation";
        this.description = "Activates when someone creates a new event on Google Calendar";
        this.fields = new ArrayList<>();
        this.fields.add("start");
        this.fields.add("timezone");
        this.fields.add("end");
        this.fields.add("description");
        this.fields.add("location");
        this.fields.add("summary");
        this.fields.add("attendees");
        this.fields.add("attachments");
        this.fields.add("remainders");
        this.requiredConfigFields = new HashMap<>();
        this.requiredConfigFields.put("email", FieldType.EMAIL);
        this.config = null;
        this.previousDatas = null;
    }

    private Events fullSync(Calendar calendar) throws Exception {
        DateTime now = new DateTime(System.currentTimeMillis());
        return calendar.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
    }

    private Events partialSync(Calendar calendar) throws Exception {
        return calendar.events().list("primary")
                .setSyncToken(lastSyncToken)
                .setSingleEvents(true)
                .execute();
    }

    private Events syncCalendar(Calendar calendar) throws Exception {
        try {
            return lastSyncToken == null ? fullSync(calendar) : partialSync(calendar);
        } catch (GoogleJsonResponseException e) {
            lastSyncToken = null;
            fullSync(calendar);
            return null;
        } catch (Exception e) {
            Logger.error("Error occurred Calendar first syncing attempt");
            Logger.debug(e.getMessage());
            Logger.debug(e.getCause());
            return null;
        }
    }

    private JSONObject translate(Event e) {
        JSONObject json = new JSONObject();

        DateTime start = e.getStart().getDateTime();
        DateTime end = e.getEnd().getDateTime();

        json.put("start", start == null ? e.getStart().getDate() : start);
        json.put("timezone", e.getStart().getTimeZone());
        json.put("end", end == null ? e.getEnd().getDate() : end);
        json.put("description", e.getDescription());
        json.put("location", e.getLocation());
        json.put("summary", e.getSummary());

        List<String> attendees = new ArrayList<>();
        for (EventAttendee attendee : e.getAttendees()) {
            attendees.add(attendee.getEmail());
        }
        json.put("attendees", attendees);

        List<String> remindersMethods = new ArrayList<>();
        List<Integer> remindersMinutes = new ArrayList<>();
        for (EventReminder reminder : e.getReminders().getOverrides()) {
            remindersMethods.add(reminder.getMethod());
            remindersMinutes.add(reminder.getMinutes());
        }
        json.put("remindersMethods", remindersMethods);
        json.put("remindersMinutes", remindersMinutes);
        json.put("recurrences", e.getRecurrence());
        return json;
    }

    private boolean process() {
        Calendar calendar;
        try {
            calendar = ApiGCalendar.getCalendarService();
        } catch (Exception e) {
            Logger.error("Error occurred while acquiring Calendar service");
            Logger.debug(e.getMessage());
            Logger.debug(e.getCause());
            return false;
        }

        String pageToken;
        Events events;
        boolean actionFound = false;
        if (previousDatas != null) {
            lastSyncToken = previousDatas.getString("lastSyncToken");
        }

        do {
            try {
                events = syncCalendar(calendar);
            } catch (Exception e) {
                Logger.error("Error occurred during second calendar syncing attempt");
                Logger.debug(e.getMessage());
                Logger.debug(e.getCause());
                return false;
            }

            if (events == null) {
                Logger.info("An Error has occurred during the Google Calendar syncing");
                return false;
            }

            List<Event> items = events.getItems();
            if (items.size() == 0)
                return false;

            for (Event event : items) {
                if (isNewEvent(event))
                {
                    eventsStore.add(translate(event));
                    actionFound = true;
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        lastSyncToken = events.getNextSyncToken();

        if (previousDatas == null)
            previousDatas = new JSONObject();
        previousDatas.put("lastSyncToken", lastSyncToken);
        previousDatas.put("lastSyncDate", System.currentTimeMillis());
        return actionFound;
    }

    private boolean isNewEvent(Event event) {
        return event.getCreated().getValue() > lastSyncDate;
    }

    @Override
    public boolean hasHappened() {
        if (previousDatas != null)
            lastSyncDate = previousDatas.getLong("lastSyncDate");
        return process();
    }

    @Override
    public List<JSONObject> whatHappened() {
        return eventsStore;
    }

    @Override
    public Map<String, FieldType> configFields() {
        return requiredConfigFields;
    }

    @Override
    public List<String> returnedFields() {
        return fields;
    }
}
