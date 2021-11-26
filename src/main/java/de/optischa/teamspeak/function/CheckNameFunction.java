package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CheckNameFunction {

    public void check(Channel channel, JSONObject config, TS3Api ts3Api) {
        JSONArray blacklistChannel = (JSONArray) config.get("backlistchannel");
        for(Object string : blacklistChannel) {
            if(string instanceof String) {
                String balcklist = (String)string;
                if(channel.getName().equalsIgnoreCase(balcklist)) {
                    ts3Api.editChannel(channel.getId(), ChannelProperty.CHANNEL_NAME, channel.getName().replaceAll(balcklist, ""));
                }
            }
        }
    }

    public void check(Client client, JSONObject config, TS3Api ts3Api, Message message) {
        JSONArray blacklistName = (JSONArray) config.get("backlistname");
        for(Object string : blacklistName) {
            if(string instanceof String) {
                String blacklist = (String)string;
                if(client.getNickname().equalsIgnoreCase(blacklist)) {
                    ts3Api.kickClientFromServer(String.valueOf(message.getMessages().get("kickname")).replaceAll("%blackname%", blacklist) ,client.getId());
                }
            }
        }
    }
}
