package de.optischa.teamspeak.manager.web;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChannelManager {

    private final TS3Api ts3Api;

    public ChannelManager(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public String getChannels() {
        JSONObject json = new JSONObject();
        JSONArray channelArray = new JSONArray();
        for (Channel channel : ts3Api.getChannels()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", channel.getId());
            jsonObject.put("name", channel.getName());
            jsonObject.put("maxclients", channel.getMaxClients());
            JSONArray channelPropertys = new JSONArray();
            for (ChannelProperty channelProperty : ChannelProperty.values()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("name", channelProperty.getName());
                jsonObject1.put("value", channel.get(channelProperty));
                channelPropertys.add(jsonObject1);
            }
            jsonObject.put("propertys", channelPropertys);
            channelArray.add(jsonObject);
        }
        json.put("channels", channelArray);
        return json.toJSONString();
    }

    public String deleteChannel(int channelid) {
        JSONObject json = new JSONObject();
        try {
            ts3Api.deleteChannel(channelid);
            json.put("status", "successful");
        } catch (TS3CommandFailedException e) {
            json.put("status", "error");
            json.put("message", "There is no channel with the id " + channelid);
        }
        return json.toJSONString();
    }

    public String createChannel(String name) {
        JSONObject json = new JSONObject();



        return json.toJSONString();
    }

}
