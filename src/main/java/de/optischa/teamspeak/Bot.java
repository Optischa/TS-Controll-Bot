package de.optischa.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.optischa.teamspeak.addons.AddonCore;
import de.optischa.teamspeak.helper.AFKHelper;
import de.optischa.teamspeak.manager.CommandManager;
import de.optischa.teamspeak.manager.ConsoleManager;
import de.optischa.teamspeak.manager.EventRegisterManager;
import de.optischa.teamspeak.utils.Config;
import lombok.Getter;

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

        ts3Config.setHost((String) config.getConfig().get("host")).setEnableCommunicationsLogging(true).setFloodRate(TS3Query.FloodRate.UNLIMITED);
        if(!ts3Query.isConnected()) {
            ts3Query.connect();
        }

        ts3Api.login("serveradmin", "Zb3jo6b9");
        ts3Api.selectVirtualServerById(1);
        ts3Api.setNickname((String) config.getConfig().get("name"));

        getConsoleManager().startConsole();

        new AddonCore().enable();
        new EventRegisterManager(ts3Api).registerEvents();
        getCommandManager().loadCommands();
        getConsoleManager().loadCommands();
        new AFKHelper(getBot()).start();
        getConsoleManager().startCommands();
    }
}
