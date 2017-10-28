package eu.epitech.reaction;

import eu.epitech.API.ApiUtils;
import eu.epitech.FieldType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactionExample extends AReaction {

	public ReactionExample() {
		this.api = ApiUtils.Name.TWITTER;
		this.name = "TWITTER : Send private message";
		this.description = "Sends a private message.";
		this.requiredActionFields = new ArrayList<>();
		this.requiredActionFields.add("name");
		this.requiredActionFields.add("date");
		this.requiredConfigFields = new HashMap<>();
		this.requiredConfigFields.put("something", FieldType.STRING);
		this.config = null;
	}

	@Override
	public void execute(String token, JSONObject actionOutput) {
	}

	@Override
	public boolean isExecutable(List<String> fields) {
		return false;
	}

	@Override
	public Map<String, FieldType> configFields() {
		return this.requiredConfigFields;
	}

	@Override
	public List<String> requiredFields() {
		return this.requiredActionFields;
	}
}
