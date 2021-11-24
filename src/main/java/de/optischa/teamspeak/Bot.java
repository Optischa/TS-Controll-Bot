package de.optischa.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.optischa.teamspeak.addons.AddonCore;
import de.optischa.teamspeak.event.Events;
import de.optischa.teamspeak.helper.AFKHelper;
import de.optischa.teamspeak.utils.Config;

public class Bot {

    private static Bot bot;
    private final TS3Config ts3Config;
    private final TS3Query ts3Query;
    private final TS3Api ts3Api;
    private final Config config;

    public static void main(String[] args) {
        new Bot();
    }

    public Bot() {
        bot = this;
        ts3Config = new TS3Config();
        ts3Query = new TS3Query(ts3Config);
        ts3Api = new TS3Api(ts3Query.getAsyncApi());
        config = new Config();

        ts3Config.setHost((String) config.getConfig().get("host")).setEnableCommunicationsLogging(true).setFloodRate(TS3Query.FloodRate.UNLIMITED);
        if(!ts3Query.isConnected()) {
            ts3Query.connect();
        }

        ts3Api.login("serveradmin", "Zb3jo6b9");
        ts3Api.selectVirtualServerById(1);
        ts3Api.setNickname((String) config.getConfig().get("name"));

        new AddonCore().enable();

        ts3Api.registerAllEvents();
        ts3Api.addTS3Listeners(new Events(getBot()));
        new AFKHelper(getBot()).start();
    }

    public static Bot getBot() {
        return bot;
    }

    public TS3Api getTs3Api() {
        return ts3Api;
    }

    public TS3Config getTs3Config() {
        return ts3Config;
    }

    public TS3Query getTs3Query() {
        return ts3Query;
    }

    public Config getConfig() {
        return config;
    }
}
