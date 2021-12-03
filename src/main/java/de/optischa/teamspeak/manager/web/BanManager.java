package de.optischa.teamspeak.manager.web;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Ban;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BanManager {

    private final TS3Api ts3Api;
    private int rCode;

    public BanManager(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public String getAllBans() {
        JSONArray json = new JSONArray();
        for (Ban ban : ts3Api.getBans()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ipbanned", ban.getBannedIp());
            jsonObject.put("id", ban.getId());
            jsonObject.put("bannedname", ban.getBannedName());
            jsonObject.put("createdate", ban.getCreatedDate());
            jsonObject.put("duration", ban.getDuration());
            jsonObject.put("reason", ban.getReason());
            jsonObject.put("invokername", ban.getInvokerName());
            json.add(jsonObject);
        }
        return json.toJSONString();
    }

    public String unBan(int banId) {
        JSONObject json = new JSONObject();

        try {
            ts3Api.deleteBan(banId);
            json.put("status", "successful");
        } catch (TS3CommandFailedException e) {
            json.put("status", "error");
            json.put("error", e.getMessage());
        }

        return json.toJSONString();
    }

    public int getrCode() {
        return rCode;
    }
}
