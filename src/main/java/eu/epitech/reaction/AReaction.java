package eu.epitech.reaction;

import elemental.json.Json;
import eu.epitech.Tokene;

import java.util.List;

public abstract class AReaction implements IReaction {
    protected String name;

    protected String description;

    protected Json config = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Json getConfig() {
        return config;
    }

    public void setConfig(Json config) {
        this.config = config;
    }

    @Override
    public abstract void execute(Tokene token, Json actionOutput);

    @Override
    public abstract boolean isExecutable(List<String> fields);
}
