package de.optischa.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import de.optischa.teamspeak.addons.AddonCore;
import de.optischa.teamspeak.function.AFKFunction;
import de.optischa.teamspeak.function.Schleuder;
import de.optischa.teamspeak.manager.CommandManager;
import de.optischa.teamspeak.manager.ConsoleManager;
import de.optischa.teamspeak.manager.EventRegisterManager;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.Config;
import lombok.Getter;

import java.util.logging.Level;

public class Bot {

    @Getter
    private static Bot bot;
    @Getter
    private final TS3Config ts3Config;
    @Getter
    private final TS3Query ts3Query;
    @Getter
    private final TS3Api ts3Api;
    @Getter
    private final Config config;
    @Getter
    private final CommandManager commandManager;
    @Getter
    private final ConsoleManager consoleManager;

    public static void main(String[] args) {
        new Bot();
    }

    public Bot() {
        bot = this;
        ts3Config = new TS3Config();
        ts3Query = new TS3Query(ts3Config);
        ts3Api = new TS3Api(ts3Query.getAsyncApi());
        commandManager = new CommandManager();
        consoleManager = new ConsoleManager();
        config = new Config();


        getConsoleManager().startConsole();
        if(!config.isExist()) {
            config.init();
            getConsoleManager().startFurnishing(config);
        }

        ts3Config.setHost((String) config.getConfig().get("host")).setEnableCommunicationsLogging(true).setFloodRate(TS3Query.FloodRate.UNLIMITED);
        if(!ts3Query.isConnected()) {
            ts3Query.connect();
        }

        ts3Api.login((String) config.getConfig().get("username"), (String) config.getConfig().get("password"));
        ts3Api.selectVirtualServerByPort(Integer.parseInt(String.valueOf(config.getConfig().get("hostport"))));
        ts3Api.setNickname((String) config.getConfig().get("name"));

        new AddonCore().enable();
        try {
            ts3Api.registerAllEvents();
            new EventRegisterManager(ts3Api).registerEvents();
        } catch (TS3CommandFailedException e) {
            BotLogger logger = new BotLogger();
            logger.log(Level.WARNING, e.getMessage());
        }
        getCommandManager().loadCommands();
        getConsoleManager().loadCommands();
        new Schleuder().startSchleuder(ts3Api);
        getConsoleManager().startCommands();
    }
}
