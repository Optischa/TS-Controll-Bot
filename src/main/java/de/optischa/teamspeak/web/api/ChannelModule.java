package de.optischa.teamspeak.web.api;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.manager.web.ChannelManager;
import de.optischa.teamspeak.web.WebApiModule;
import de.optischa.teamspeak.web.gson.Body;

public class ChannelModule extends WebApiModule {

    @Override
    public boolean canHandle(String url) {
        return url.startsWith("channel");
    }

    @Override
    public void handleApiRequest(HttpExchange httpExchange, String url) {
        String method = httpExchange.getRequestMethod();
        TS3Api ts3Api = Bot.getBot().getTs3Api();
        ChannelManager channelManager = new ChannelManager(ts3Api);
        if (method.equalsIgnoreCase("get")) {
            sendResponse(httpExchange, channelManager.getChannels(), channelManager.getrCode());
        } else if (method.equalsIgnoreCase("put")) {
            System.out.println("PUT Request");
        } else if (method.equalsIgnoreCase("post")) {
            Body body = new Gson().fromJson(readBody(httpExchange.getRequestBody()), Body.class);
            sendResponse(httpExchange, channelManager.createChannel(body.getChannelname(), body.getChannelproperties()), channelManager.getrCode());
        } else if (method.equalsIgnoreCase("delete")) {
            Body body = new Gson().fromJson(readBody(httpExchange.getRequestBody()), Body.class);
            sendResponse(httpExchange, channelManager.deleteChannel(body.getChannelid()), channelManager.getrCode());
        }
    }
}
