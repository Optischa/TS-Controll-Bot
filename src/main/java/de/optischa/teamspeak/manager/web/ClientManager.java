package de.optischa.teamspeak.manager.web;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.web.gson.Body;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ClientManager {

    private final TS3Api ts3Api;
    private int rCode;

    public ClientManager(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public String getClients() {
        JSONArray json = new JSONArray();
        for (Client client : ts3Api.getClients()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", client.getNickname());
            jsonObject.put("ip", client.getIp());
            jsonObject.put("platform", client.getPlatform());
            jsonObject.put("version", client.getVersion());
            jsonObject.put("channelid", client.getChannelId());
            jsonObject.put("country", client.getCountry());
            jsonObject.put("lastconnected", client.getLastConnectedDate());
            jsonObject.put("firstconnected", client.getCreatedDate());
            jsonObject.put("idletime", client.getIdleTime());
            jsonObject.put("serverquery", client.isServerQueryClient());
            jsonObject.put("away", client.isAway());
            jsonObject.put("inputmuted", client.isInputMuted());
            jsonObject.put("outputmuted", client.isOutputMuted());
            JSONArray jsonArray = new JSONArray();
            for(int serverGroup : client.getServerGroups()) {
                jsonArray.add(serverGroup);
            }
            jsonObject.put("servergroups", jsonArray);
            json.add(jsonObject);
        }
        rCode = 200;
        return json.toJSONString();
    }

    public int getrCode() {
        return rCode;
    }

    public String manageClient(String function, int clientId, Body body) {
        JSONObject jsonObject = new JSONObject();

        if(function.equalsIgnoreCase("kickserver")) {
            try {
                ts3Api.kickClientFromServer(clientId);
                jsonObject.put("status", "successful");
            } catch (TS3CommandFailedException e) {
                jsonObject.put("status", "error");
                jsonObject.put("message", e.getMessage());
            }
        } else if (function.equalsIgnoreCase("kickchannel")) {
            try {
                ts3Api.kickClientFromChannel(clientId);
                jsonObject.put("status", "successful");
            } catch (TS3CommandFailedException e) {
                jsonObject.put("status", "error");
                jsonObject.put("message", e.getMessage());
            }
        } else if (function.equalsIgnoreCase("ban")) {
            try {
                ts3Api.banClient(clientId, body.getBanreason());
                jsonObject.put("status", "successful");
            } catch (TS3CommandFailedException e) {
                jsonObject.put("status", "error");
                jsonObject.put("message", e.getMessage());
            }
        }

        return jsonObject.toJSONString();
    }
}
