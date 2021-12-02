package de.optischa.teamspeak.web.api;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.manager.web.ClientManager;
import de.optischa.teamspeak.web.WebApiModule;
import de.optischa.teamspeak.web.gson.Body;

public class ClientModule extends WebApiModule {
    @Override
    public boolean canHandle(String url) {
        return url.startsWith("client");
    }

    @Override
    public void handleApiRequest(HttpExchange httpExchange, String url) {
        String method = httpExchange.getRequestMethod();
        TS3Api ts3Api = Bot.getBot().getTs3Api();
        ClientManager clientManager = new ClientManager(ts3Api);
        if (method.equalsIgnoreCase("GET")) {
            sendResponse(httpExchange, clientManager.getClients(), clientManager.getrCode());
        } else if(method.equalsIgnoreCase("PUT")) {
            Body body = new Gson().fromJson(readBody(httpExchange.getRequestBody()), Body.class);
            sendResponse(httpExchange, clientManager.manageClient(body.getFunction(), body.getClientid()), clientManager.getrCode());
        }
    }
}
