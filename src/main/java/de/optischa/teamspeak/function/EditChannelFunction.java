package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

public class EditChannelFunction {

    public void userCounter(Message message, Config config, Bot bot) {
        JSONObject jsonObject = (JSONObject) config.getConfig().get("configs");
        if((Boolean) jsonObject.getOrDefault("usercount", false)) {
            bot.getTs3Api().editChannel(Integer.parseInt(String.valueOf(jsonObject.get("usercountchannelid"))), ChannelProperty.CHANNEL_NAME, String.valueOf(message.getMessages().get("usercounter"))
                    .replaceAll("%onlineuser%", String.valueOf(bot.getTs3Api().getClients().size()-bot.getTs3Api().getServerInfo().getQueryClientsOnline()))
                    .replaceAll("%maxuser%", String.valueOf(bot.getTs3Api().getServerInfo().getMaxClients())));
        }
    }

}
