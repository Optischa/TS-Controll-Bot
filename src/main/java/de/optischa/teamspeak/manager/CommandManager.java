package de.optischa.teamspeak.manager;

import de.optischa.teamspeak.commands.chat.ChatCommand;
import de.optischa.teamspeak.helper.CommandHelper;
import de.optischa.teamspeak.helper.CommandsReflectionHelper;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.CommandParser;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;

public class CommandManager {

    @Getter
    private final CommandHelper commandHelper;
    @Getter
    private final CommandParser commandParser;
    @Getter
    private final BotLogger logger;

    public CommandManager() {
        this.commandHelper = new CommandHelper();
        this.commandParser = new CommandParser();
        this.logger = new BotLogger();
    }

    public void loadCommands() {
        try {

            List<ChatCommand> chatCommands = CommandsReflectionHelper.load("de.optischa.teamspeak.commands.chat", ChatCommand.class,false);
            for (ChatCommand chatCommand : chatCommands) {
                getCommandHelper().commands.put(chatCommand.name().toLowerCase(), chatCommand);
            }
            getLogger().log(Level.INFO, "Register " + getCommandHelper().commands.size() + " Commands");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
