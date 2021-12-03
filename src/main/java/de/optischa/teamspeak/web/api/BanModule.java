package de.optischa.teamspeak.web.api;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.manager.web.BanManager;
import de.optischa.teamspeak.web.WebApiModule;
import de.optischa.teamspeak.web.gson.Body;

public class BanModule extends WebApiModule {

    @Override
    public boolean canHandle(String url) {
        return url.startsWith("ban");
    }

    @Override
    public void handleApiRequest(HttpExchange httpExchange, String url) {
        String method = httpExchange.getRequestMethod();
        TS3Api ts3Api = Bot.getBot().getTs3Api();
        BanManager banManager = new BanManager(ts3Api);
        if (method.equalsIgnoreCase("GET")) {
            sendResponse(httpExchange, banManager.getAllBans(), banManager.getrCode());
        } else if (method.equalsIgnoreCase("PUT")) {
            Body body = new Gson().fromJson(readBody(httpExchange.getRequestBody()), Body.class);
            sendResponse(httpExchange, banManager.unBan(body.getBanid()), banManager.getrCode());
        }
    }
}
