package guis;

import java.util.HashMap;
import java.util.Map;

public class CommandRouter {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String action, Command command) {
        commands.put(action, command);
    }

    public void executeCommand(String action) {
        Command command = commands.get(action);
        if (command != null) {
            command.execute();
        }
    }
}