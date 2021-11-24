package de.optischa.teamspeak.helper;

import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

public class WelcomeHelper {

    public WelcomeHelper(Bot bot, int clientId) {
        Config config = new Config();
        JSONObject json = (JSONObject) config.getConfig().get("configs");
        Message message = new Message();
        if((Boolean) json.getOrDefault("welcomemessage", false)) {
            bot.getTs3Api().sendPrivateMessage(clientId, String.valueOf(message.getMessages().get("welcomemessage")));
        }
    }

}
