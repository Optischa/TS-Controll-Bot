package de.optischa.teamspeak.helper;

import de.optischa.teamspeak.commands.chat.ChatCommand;
import de.optischa.teamspeak.utils.CommandParser;

import java.sql.SQLException;
import java.util.HashMap;

public class CommandHelper {

    public final CommandParser parse = new CommandParser();
    public HashMap<String, ChatCommand> commands = new HashMap<>();

    public void handleCommand(CommandParser.commandContainer cmd) throws SQLException {
        ChatCommand command = getChatCommand(cmd);
        if (command != null) {
            boolean called = command.isCalled(cmd.args);
            if (!called) {
                command.action(cmd.args);
            }
            command.executed(called);
        }
    }

    private ChatCommand getChatCommand(CommandParser.commandContainer cmd) {
        return commands.get(getKey(cmd.invoke));
    }

    private String getKey(String invoke) {
        return invoke.toLowerCase();
    }

}
