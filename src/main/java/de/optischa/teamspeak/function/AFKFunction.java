package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AFKFunction {

    private final Map<String, Long> afkUser = new HashMap<>();
    private final Map<String, Boolean> movedUser = new HashMap<>();
    private final Map<String, Integer> channelUser = new HashMap<>();

    private final Bot bot;

    public AFKFunction(Bot bot) {
        this.bot = bot;
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Config config = new Config();
                JSONObject json = (JSONObject) config.getConfig().get("configs");
                Message message = new Message();
                if ((Boolean) json.get("afkmoved")) {
                    for (Client client : bot.getTs3Api().getClients()) {
                        if (client.isServerQueryClient()) {
                            continue;
                        }
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
                                    if ((System.currentTimeMillis() - current) >= Integer.parseInt(String.valueOf(json.get("afktime"))) * 1000) {
                                        movedUser.put(client.getUniqueIdentifier(), true);
                                        channelUser.put(client.getUniqueIdentifier(), client.getChannelId());
                                        afkUser.remove(client.getUniqueIdentifier());

                                        bot.getTs3Api().sendPrivateMessage(client.getId(), (String) message.getMessages().get("afkmovemessage"));
                                        bot.getTs3Api().moveClient(client.getId(), Integer.parseInt(String.valueOf(json.get("afkchannelid"))));
                                    }
                                }
                            } else {
                                if(afkUser.containsKey(client.getUniqueIdentifier())) {
                                    afkUser.remove(client.getUniqueIdentifier());
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 5000);
    }
}
