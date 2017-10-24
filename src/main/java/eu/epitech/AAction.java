package eu.epitech;

import com.sun.istack.internal.NotNull;
import jdk.internal.jline.internal.Nullable;
import elemental.json.Json;

import java.util.List;
import java.util.Map;

public abstract class AAction implements IAction {
    @NotNull
    protected String name;

    @NotNull
    protected String description;

    @Nullable
    protected Json config = null;

    public Json getConfig() {
        return config;
    }
    public void setConfig(Json config) {
        this.config = config;
    }
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

    @Override
    public abstract boolean hasHappened();

    @Override
    public abstract Json whatHappened();

    @Override
    public abstract Map<String, FieldType> configFields();

    @Override
    public abstract List<String> returnedFields();
}
