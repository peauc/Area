package eu.epitech.reaction;

import elemental.json.Json;
import eu.epitech.Tokene;

import java.util.List;

public interface IReaction {
    void execute(Tokene token, Json actionOutput);
    boolean isExecutable(List<String> fields);
}
