package de.optischa.teamspeak.template;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType;
import com.github.theholywaffle.teamspeak3.api.PermissionGroupType;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateTemplate {

    private final File folder = new File("templates/");

    public CreateTemplate() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public void createTeamplate(TS3Api ts3Api, String name) {
        boolean exists = false;
        if (folder.listFiles() != null) {
            for (File file : folder.listFiles()) {
                if (file.getName().equalsIgnoreCase(name + ".json")) {
                    exists = true;
                }
            }
        }
        if (!exists) {
            File file = new File("templates/" + name + ".json");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    JSONObject jsonObject = new JSONObject();
                    JSONArray channels = loadAllChannel(ts3Api);
                    JSONArray servergroups = loadAllGroups(ts3Api);
                    jsonObject.put("channels", channels);
                    jsonObject.put("groups", servergroups);
                    try {
                        FileWriter fw = new FileWriter("templates/" + file.getName());
                        fw.write(jsonObject.toJSONString());
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Template created");
                } catch (IOException e) {
                    System.out.println("Error by create template");
                    e.printStackTrace();
                }
            }
        }
    }

    private JSONArray loadAllGroups(TS3Api ts3Api) {
        JSONArray serverGroups = new JSONArray();
        for (ServerGroup serverGroup : ts3Api.getServerGroups()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("groupname", serverGroup.getName());
            jsonObject.put("type", String.valueOf(serverGroup.getType()));
            jsonObject.put("iconid", serverGroup.getIconId());
            jsonObject.put("savedb", serverGroup.getSaveDb());
            jsonObject.put("type", serverGroup.getType());
            serverGroups.add(jsonObject);
        }
        return serverGroups;
    }

    private JSONArray loadAllChannel(TS3Api ts3Api) {
        JSONArray serverChannels = new JSONArray();
        for (Channel channel : ts3Api.getChannels()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("channelname", channel.getName());
            jsonObject.put("permissionpower", channel.getNeededSubscribePower());
            JSONArray channelPropertys = new JSONArray();
            for (ChannelProperty channelProperty : ChannelProperty.values()) {
                String name = channelProperty.getName();
                if (name.equalsIgnoreCase("CHANNEL_FLAG_MAXCLIENTS_UNLIMITED") || name.equalsIgnoreCase("CHANNEL_FLAG_SEMI_PERMANENT") || name.equalsIgnoreCase("CHANNEL_MAXCLIENTS") || name.equalsIgnoreCase("CHANNEL_DESCRIPTION") || name.equalsIgnoreCase("channel_flag_permanent") || name.equalsIgnoreCase("channel_password") && !String.valueOf(channel.get(channelProperty)).equalsIgnoreCase("") || name.equalsIgnoreCase("channel_maxfamilyclients") || name.equalsIgnoreCase("CHANNEL_CODEC_QUALITY") || name.equalsIgnoreCase("CHANNEL_DELETE_DELAY") && !String.valueOf(channel.get(channelProperty)).equalsIgnoreCase("")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("name", channelProperty.getName());
                    jsonObject1.put("value", channel.get(channelProperty));
                    channelPropertys.add(jsonObject1);
                }
            }
            jsonObject.put("channelpropertys", channelPropertys);
            jsonObject.put("maxclients", channel.getMaxClients());
            serverChannels.add(jsonObject);
        }
        return serverChannels;
    }

}
