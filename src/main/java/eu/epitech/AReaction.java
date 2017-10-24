package eu.epitech;

import com.sun.istack.internal.NotNull;
import elemental.json.Json;
import jdk.internal.jline.internal.Nullable;

import java.util.List;

public abstract class AReaction implements IReaction {
    @NotNull
    protected String name;

    @NotNull
    protected String description;

    @Nullable
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
    public abstract void execute(Token token, Json actionOutput);

    @Override
    public abstract boolean isExecutable(List<String> fields);
}
