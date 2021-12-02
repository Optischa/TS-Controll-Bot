package de.optischa.teamspeak.manager.web;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import de.optischa.teamspeak.web.gson.ChannelProperties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private final TS3Api ts3Api;
    private int rCode;

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
        rCode = 200;
        return json.toJSONString();
    }

    public String deleteChannel(int channelid) {
        JSONObject json = new JSONObject();
        try {
            ts3Api.deleteChannel(channelid);
            json.put("status", "successful");
            rCode = 200;
        } catch (TS3CommandFailedException e) {
            json.put("status", "error");
            json.put("message", "There is no channel with the id " + channelid);
            rCode = 500;
        }
        return json.toJSONString();
    }

    public String createChannel(String name, ChannelProperties[] channelProperties) {
        Map<ChannelProperty, String> options = new HashMap<>();
        for(ChannelProperties channelPropertie : channelProperties) {
            ChannelProperty channelProperty = ChannelProperty.valueOf(channelPropertie.getName());
            if (channelPropertie.getName().equalsIgnoreCase("CHANNEL_FLAG_MAXCLIENTS_UNLIMITED") || channelPropertie.getName().equalsIgnoreCase("CHANNEL_FLAG_SEMI_PERMANENT") || channelPropertie.getName().equalsIgnoreCase("CHANNEL_MAXCLIENTS") || channelPropertie.getName().equalsIgnoreCase("CHANNEL_DESCRIPTION") || channelPropertie.getName().equalsIgnoreCase("channel_flag_permanent") || channelPropertie.getName().equalsIgnoreCase("channel_password") && !String.valueOf(channelPropertie.getValue()).equalsIgnoreCase("") || channelPropertie.getName().equalsIgnoreCase("channel_maxfamilyclients") || channelPropertie.getName().equalsIgnoreCase("CHANNEL_CODEC_QUALITY") || channelPropertie.getName().equalsIgnoreCase("CHANNEL_DELETE_DELAY") && !String.valueOf(channelPropertie.getValue()).equalsIgnoreCase("")) {
                options.put(channelProperty, channelPropertie.getValue());
            }
        }
        JSONObject json = new JSONObject();

        try {
            int channelId = ts3Api.createChannel(name, options);
            json.put("status", "successful");
            json.put("channelid", channelId);
            rCode = 200;
        } catch (TS3CommandFailedException e) {
            json.put("status", "error");
            json.put("message", e.getMessage());
            rCode = 500;
        }
        return json.toJSONString();
    }

    public int getrCode() {
        return rCode;
    }
}
