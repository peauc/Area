package eu.epitech;

import elemental.json.Json;

import java.util.List;

public interface IReaction {
    void execute(Token token, Json actionOutput);
    boolean isExecutable(List<String> fields);
}
