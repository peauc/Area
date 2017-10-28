package eu.epitech;

import eu.epitech.API.ApiUtils;
import eu.epitech.action.AAction;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionExample extends AAction {

	public ActionExample() {
		this.api = ApiUtils.Name.FACEBOOK;
		this.name = "FACEBOOK : On friend request";
		this.description = "Activates when someone sends you a friend request";
		this.fields = new ArrayList<>();
		this.fields.add("name");
		this.fields.add("date");
		this.requiredConfigFields = new HashMap<>();
		this.requiredConfigFields.put("something", FieldType.STRING);
		this.config = null;
		this.previousDatas = null;
	}

	@Override
	public boolean hasHappened() {
		return false;
	}

	@Override
	public List<JSONObject> whatHappened() {
		return null;
	}

	@Override
	public Map<String, FieldType> configFields() {
		return this.requiredConfigFields;
	}

	@Override
	public List<String> returnedFields() {
		return this.fields;
	}
}
