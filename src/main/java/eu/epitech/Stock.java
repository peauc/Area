package eu.epitech;

import eu.epitech.action.AAction;
import eu.epitech.reaction.AReaction;

public class Stock {
	private User user = null;
	private AAction action = null;
	private AReaction reaction = null;
	private String prompt = null;

	public Stock(User user, AAction action, AReaction reaction, String prompt) {
		this.user = user;
		this.action = action;
		this.reaction = reaction;
		this.prompt = prompt;
	}

	public User getUser() {
		return user;
	}

	public AAction getAction() {
		return action;
	}

	public AReaction getReaction() {
		return reaction;
	}

	public String getPrompt() {
		return prompt;
	}
}
