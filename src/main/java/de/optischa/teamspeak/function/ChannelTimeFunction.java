package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.web.gson.ChannelProperties;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChannelTimeFunction {

    private final Bot bot;

    public ChannelTimeFunction(Bot bot) {
        this.bot = bot;
    }

    public void start(Config config) {
        JSONObject jsonObject = (JSONObject) config.getConfig().get("configs");
        if (Boolean.parseBoolean(String.valueOf(jsonObject.get("channel-timer")))) {
            Map<ChannelProperty, String> editChannel = new HashMap<>();
            Date date = new Date();
            editChannel.put(ChannelProperty.CHANNEL_NAME, date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
            bot.getTs3Api().editChannel(Integer.parseInt(String.valueOf(jsonObject.get("channel-timer-id"))), editChannel);
        }
    }

}
