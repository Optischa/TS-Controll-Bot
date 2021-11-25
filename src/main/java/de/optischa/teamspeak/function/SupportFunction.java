package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SupportFunction {

    private final Bot bot;

    public SupportFunction(Bot bot) {
        this.bot = bot;
    }

    public void clientJoinSupportChannel(int clientId, int channelId) {
        Config config = new Config();
        JSONObject json = (JSONObject) config.getConfig().get("configs");
        Message message = new Message();
        if ((Boolean) json.getOrDefault("supportsystem", false)) {
            if (bot.getTs3Api().getChannelInfo(Integer.parseInt(String.valueOf(json.getOrDefault("supportchannelid", 1)))) != null) {
                if (channelId == Integer.parseInt(String.valueOf(json.getOrDefault("supportchannelid", 1)))) {
                    JSONArray jsonArray = (JSONArray) json.get("supportgroupsid");
                    int count = 0;
                    List<Integer> supporter = new ArrayList<>();
                    for (Client clients : bot.getTs3Api().getClients()) {
                        if(clients.isServerQueryClient()) {
                            continue;
                        }
                        for (Object groupsid : jsonArray) {
                            for (int i = 0; i < bot.getTs3Api().getClientInfo(clients.getId()).getServerGroups().length; i++) {
                                if (bot.getTs3Api().getClientInfo(clients.getId()).getServerGroups()[i] == Integer.parseInt(String.valueOf(groupsid))) {
                                    if(clients.getId() != clientId) {
                                        if (!supporter.contains(clients.getId())) {
                                            supporter.add(clients.getId());
                                            count++;
                                        }
                                    } else {
                                        bot.getTs3Api().sendPrivateMessage(clientId, String.valueOf(message.getMessages().get("supporterjoinsupport")));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    for (Integer clientIds : supporter) {
                        bot.getTs3Api().sendPrivateMessage(clientIds, String.valueOf(message.getMessages().get("supporterinfo")).replaceAll("%user%", bot.getTs3Api().getClientInfo(clientId).getNickname()));
                    }
                    bot.getTs3Api().sendPrivateMessage(clientId, String.valueOf(message.getMessages().get("supportnotification")).replaceAll("%counter%", String.valueOf(count)));
                }
            }
        }
    }

}
