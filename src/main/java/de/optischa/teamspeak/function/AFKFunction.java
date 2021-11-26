package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class AFKFunction {

    private final Map<String, Long> afkUser = new HashMap<>();
    private final Map<String, Boolean> movedUser = new HashMap<>();
    private final Map<String, Integer> channelUser = new HashMap<>();

    private final Bot bot;
    @Getter
    private final BotLogger logger;

    public AFKFunction(Bot bot) {
        this.bot = bot;
        this.logger = new BotLogger();
    }

    public void start(Client client, Message message, JSONObject jsonObject) {
        if (movedUser.containsKey(client.getUniqueIdentifier())) {
            if (!(client.isAway() || client.isInputMuted() || client.isOutputMuted())) {
                if (afkUser.containsKey(client.getUniqueIdentifier())) {
                    afkUser.remove(client.getUniqueIdentifier());
                }
                if (movedUser.containsKey(client.getUniqueIdentifier())) {
                    if (movedUser.get(client.getUniqueIdentifier())) {
                        bot.getTs3Api().moveClient(client.getId(), channelUser.get(client.getUniqueIdentifier()));
                        movedUser.remove(client.getUniqueIdentifier());
                        channelUser.remove(client.getUniqueIdentifier());
                    }
                }
            }
        } else {
            if (client.isAway() || client.isInputMuted() || client.isOutputMuted()) {
                if (!afkUser.containsKey(client.getUniqueIdentifier())) {
                    afkUser.put(client.getUniqueIdentifier(), System.currentTimeMillis());
                } else {
                    long current = afkUser.get(client.getUniqueIdentifier());
                    if ((System.currentTimeMillis() - current) >= Integer.parseInt(String.valueOf(jsonObject.get("afktime"))) * 1000) {
                        movedUser.put(client.getUniqueIdentifier(), true);
                        channelUser.put(client.getUniqueIdentifier(), client.getChannelId());
                        afkUser.remove(client.getUniqueIdentifier());

                        bot.getTs3Api().sendPrivateMessage(client.getId(), (String) message.getMessages().get("afkmovemessage"));
                        try {
                            bot.getTs3Api().moveClient(client.getId(), Integer.parseInt(String.valueOf(jsonObject.get("afkchannelid"))));
                        } catch (TS3CommandFailedException e) {
                            getLogger().log(Level.WARNING, e.getMessage());
                        }
                    }
                }
            } else {
                if (afkUser.containsKey(client.getUniqueIdentifier())) {
                    afkUser.remove(client.getUniqueIdentifier());
                }
            }
        }
    }
}
