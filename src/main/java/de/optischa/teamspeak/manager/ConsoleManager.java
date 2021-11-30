package de.optischa.teamspeak.manager;

import de.optischa.teamspeak.commands.chat.ChatCommand;
import de.optischa.teamspeak.commands.console.ConsoleCommand;
import de.optischa.teamspeak.helper.CommandsReflectionHelper;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.Config;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ConsoleManager {

    @Getter
    private final BotLogger logger;
    private final Map<String, ConsoleCommand> commandList;

    public ConsoleManager() {
        this.logger = new BotLogger();
        this.commandList = new HashMap<>();
    }

    public void startConsole() {
        getLogger().log(Level.INFO, "");
        getLogger().log(Level.INFO, "");
        getLogger().log(Level.INFO, "$$$$$$$$\\                                 $$$$$$\\                                $$\\               $$$$$$$\\             $$\\     ");
        getLogger().log(Level.INFO, "\\__$$  __|                               $$  __$$\\                               $$ |              $$  __$$\\            $$ |    ");
        getLogger().log(Level.INFO, "   $$ | $$$$$$\\   $$$$$$\\  $$$$$$\\$$$$\\  $$ /  \\__| $$$$$$\\   $$$$$$\\   $$$$$$\\  $$ |  $$\\         $$ |  $$ | $$$$$$\\ $$$$$$\\   ");
        getLogger().log(Level.INFO, "   $$ |$$  __$$\\  \\____$$\\ $$  _$$  _$$\\ \\$$$$$$\\  $$  __$$\\ $$  __$$\\  \\____$$\\ $$ | $$  |$$$$$$\\ $$$$$$$\\ |$$  __$$\\\\_$$  _|  ");
        getLogger().log(Level.INFO, "   $$ |$$$$$$$$ | $$$$$$$ |$$ / $$ / $$ | \\____$$\\ $$ /  $$ |$$$$$$$$ | $$$$$$$ |$$$$$$  / \\______|$$  __$$\\ $$ /  $$ | $$ |    ");
        getLogger().log(Level.INFO, "   $$ |$$   ____|$$  __$$ |$$ | $$ | $$ |$$\\   $$ |$$ |  $$ |$$   ____|$$  __$$ |$$  _$$<          $$ |  $$ |$$ |  $$ | $$ |$$\\ ");
        getLogger().log(Level.INFO, "   $$ |\\$$$$$$$\\ \\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$  |$$$$$$$  |\\$$$$$$$\\ \\$$$$$$$ |$$ | \\$$\\         $$$$$$$  |\\$$$$$$  | \\$$$$  |");
        getLogger().log(Level.INFO, "   \\__| \\_______| \\_______|\\__| \\__| \\__| \\______/ $$  ____/  \\_______| \\_______|\\__|  \\__|        \\_______/  \\______/   \\____/ ");
        getLogger().log(Level.INFO, "                                                   $$ |                                                                         ");
        getLogger().log(Level.INFO, "                                                   $$ |                                                                         ");
        getLogger().log(Level.INFO, "                                                   \\__|                                                                         ");
        getLogger().log(Level.INFO, "");
        getLogger().log(Level.INFO, "Author: \tOptischa");
        getLogger().log(Level.INFO, "Version: \t0.1");
    }

    public void startCommands() {
        new Thread(() -> {
            getLogger().log(Level.INFO, "If you need help write help");
            while (true) {
                System.out.print("> ");
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                String input = "";
                try {
                    input = inputReader.readLine();
                } catch (IOException ignored) {
                }

                if (input.equalsIgnoreCase("help")) {
                    for (ConsoleCommand consoleCommand : commandList.values()) {
                        getLogger().log(Level.INFO, consoleCommand.key() + " | " + consoleCommand.description());
                    }
                } else {
                    String[] splitInput = input.split(" ");
                    for (ConsoleCommand consoleCommand : commandList.values()) {
                        if(splitInput[0].toLowerCase().equalsIgnoreCase(consoleCommand.key())) {
                            consoleCommand.action(splitInput);
                        }
                    }
                }
            }
        }).start();
    }

    public void loadCommands() {
        try {
            List<ConsoleCommand> consoleCommands = CommandsReflectionHelper.load("de.optischa.teamspeak.commands.console", ConsoleCommand.class, false);
            for (ConsoleCommand consoleCommand : consoleCommands) {
                commandList.put(consoleCommand.key().toLowerCase(), consoleCommand);
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void startFurnishing(Config config) {
        JSONObject json = config.getConfig();
        String host = createQuestion("Hostadress (127.0.0.1): ");
        int hostport = 0;
        try {
            hostport = Integer.parseInt(createQuestion("Hostname port (9987): "));
        } catch (NumberFormatException ignored) {

        }
        String username = createQuestion("Query Name (serveradmin): ");
        String password = createQuestion("Query password (none): ");
        String botname = createQuestion("Bot Name (Bot): ");
        json.put("host", host.equalsIgnoreCase("") ? "127.0.0.1" : host);
        json.put("hostport", hostport == 0 ? "9987" : hostport);
        json.put("username", username.equalsIgnoreCase("") ? "serveradmin" : username);
        json.put("password", password.equalsIgnoreCase("") ? "" : password);
        json.put("name", botname.equalsIgnoreCase("") ? "Bot" : botname);
        save(json, "config.json");
    }

    private String createQuestion(String question) {
        System.out.print("> " + question + ": ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = inputReader.readLine();
        } catch (IOException ignored) {
        }
        return input;
    }

    private void save(JSONObject jsonObject, String pathname) {
        try (FileWriter file = new FileWriter(new File(pathname).getName())) {
            file.append(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
