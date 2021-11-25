package de.optischa.teamspeak.function;

import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

public class WelcomeFunction {

    public WelcomeFunction(Bot bot, int clientId) {
        Config config = new Config();
        JSONObject json = (JSONObject) config.getConfig().get("configs");
        Message message = new Message();
        if((Boolean) json.getOrDefault("welcomemessage", false)) {
            bot.getTs3Api().sendPrivateMessage(clientId, String.valueOf(message.getMessages().get("welcomemessage")));
        }
    }

}
