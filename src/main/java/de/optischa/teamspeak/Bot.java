package de.optischa.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import de.optischa.teamspeak.addons.AddonCore;
import de.optischa.teamspeak.function.Schleuder;
import de.optischa.teamspeak.manager.CommandManager;
import de.optischa.teamspeak.manager.ConsoleManager;
import de.optischa.teamspeak.manager.EventRegisterManager;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.web.WebAPI;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.logging.Level;

@Getter
@EqualsAndHashCode
public class Bot {

    @Getter
    private static Bot bot;
    private final TS3Config ts3Config;
    private final TS3Query ts3Query;
    private final TS3Api ts3Api;
    private final Config config;
    private final CommandManager commandManager;
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
        BotLogger logger = new BotLogger();
        logger.createFile();
        getConsoleManager().startConsole();
        new WebAPI().start();
        if(!config.isExist()) {
            config.init();
            getConsoleManager().startFurnishing(config);
        }

        ts3Config.setHost((String) config.getConfig().get("host")).setEnableCommunicationsLogging(true).setFloodRate(TS3Query.FloodRate.UNLIMITED);
        if(!ts3Query.isConnected()) {
            try {
                ts3Query.connect();
            } catch (TS3ConnectionFailedException e) {
                logger.log(Level.WARNING, "No connection to Server with Adresse " + config.getConfig().get("host"));
            }
        }

        try {
            ts3Api.login((String) config.getConfig().get("username"), (String) config.getConfig().get("password"));
        } catch (TS3CommandFailedException e) {
            logger.log(Level.SEVERE, "Login has a error. Please check the config");
        }
        ts3Api.selectVirtualServerByPort(Integer.parseInt(String.valueOf(config.getConfig().get("hostport"))));
        try {
            ts3Api.setNickname((String) config.getConfig().get("name"));
        } catch (TS3CommandFailedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        new AddonCore().enable();
        try {
            ts3Api.registerAllEvents();
            new EventRegisterManager(ts3Api).registerEvents();
        } catch (TS3CommandFailedException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        getCommandManager().loadCommands();
        getConsoleManager().loadCommands();
        new Schleuder().startSchleuder(ts3Api);
        getConsoleManager().startCommands();
    }
}
