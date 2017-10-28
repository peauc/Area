
package eu.epitech.action;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import eu.epitech.API.ApiGCalendar;
import eu.epitech.API.ApiUtils;
import eu.epitech.FieldType;
import org.json.JSONObject;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ActionGCalendar extends AAction {
    private String lastSyncToken = null;
    private DateTime lastSyncDate = null;
    private ArrayList<JSONObject> eventsStore = new ArrayList<>();

    public ActionGCalendar() {
        this.api = ApiUtils.Name.GOOGLE_CALENDAR;
        this.name = "GOOGLE CALENDAR : on event creation";
        this.description = "Activates when someone creates a new event on Google Calendar";
        this.fields = new ArrayList<>();
        this.fields.add("start");
        this.fields.add("timezone");
        this.fields.add("end");
        this.fields.add("creator");
        this.fields.add("description");
        this.fields.add("location");
        this.fields.add("summary");
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
            Logger.error(e.getMessage());
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
        json.put("creator", e.getCreator().getDisplayName());
        json.put("description", e.getDescription());
        json.put("location", e.getLocation());
        json.put("summary", e.getSummary());
        return json;
    }

    private boolean process() {
        Calendar calendar;
        try {
            calendar = ApiGCalendar.getCalendarService();
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return false;
        }

        String pageToken;
        Events events;
        boolean actionFound = false;

        do {
            try {
                events = syncCalendar(calendar);
            } catch (Exception e) {
                Logger.error(e.getMessage());
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



        return actionFound;
    }

    private boolean isNewEvent(Event event) {
        return event.getCreated().getValue() > lastSyncDate.getValue();
    }

    @Override
    public boolean hasHappened() {
        lastSyncDate = new DateTime(System.currentTimeMillis());
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
