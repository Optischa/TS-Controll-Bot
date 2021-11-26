package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Schleuder {

    public void startSchleuder(TS3Api ts3Api) {
        Timer timer = new Timer();
        AFKFunction afkFunction = new AFKFunction(Bot.getBot());
        CheckNameFunction checkNameFunction = new CheckNameFunction();
        AntiRecordingFunction antiRecordingFunction = new AntiRecordingFunction();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Config config = new Config();
                JSONObject json = (JSONObject) config.getConfig().get("configs");
                Message message = new Message();
                for (Client client : ts3Api.getClients()) {
                    if (client.isServerQueryClient()) {
                        continue;
                    }
                    antiRecordingFunction.antiRecording(client.isRecording(), config, ts3Api, client.getId(), message);
                    if ((Boolean) json.get("afkmoved")) {
                        afkFunction.start(client, message, json);
                    }
                    if ((Boolean) json.get("kickclientname")) {
                        checkNameFunction.check(client, json, ts3Api, message);
                    }
                }
                if ((Boolean) json.get("renamechannelname")) {
                    for (Channel channel : ts3Api.getChannels()) {
                        checkNameFunction.check(channel, json, ts3Api);
                    }
                }
            }
        }, 1000, 5000);
    }

}
