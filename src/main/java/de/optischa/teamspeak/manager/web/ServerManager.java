package de.optischa.teamspeak.manager.web;

import com.github.theholywaffle.teamspeak3.TS3Api;
import org.json.simple.JSONObject;

public class ServerManager {

    private final TS3Api ts3Api;

    public ServerManager(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public String getServerInfo() {
        JSONObject json = new JSONObject();
        json.put("version", ts3Api.getServerInfo().getVersion());
        json.put("hostmessage", ts3Api.getServerInfo().getHostMessage());
        json.put("ip", ts3Api.getServerInfo().getIp());
        json.put("port", ts3Api.getServerInfo().getPort());
        json.put("uptime", ts3Api.getServerInfo().getUptime());
        json.put("bannerurl", ts3Api.getServerInfo().getHostbannerUrl());
        json.put("ping", ts3Api.getServerInfo().getPing());
        json.put("platform", ts3Api.getServerInfo().getPlatform());
        json.put("channels", ts3Api.getServerInfo().getChannelsOnline());
        json.put("clientsonline", ts3Api.getServerInfo().getClientsOnline());
        json.put("clientsmax", ts3Api.getServerInfo().getMaxClients());
        json.put("totalpacketloss", ts3Api.getServerInfo().getTotalPacketloss());
        return json.toJSONString();
    }

}
