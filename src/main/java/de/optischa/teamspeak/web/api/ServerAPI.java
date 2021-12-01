package de.optischa.teamspeak.web.api;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.sun.net.httpserver.HttpExchange;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.manager.web.ServerManager;
import de.optischa.teamspeak.web.WebApiModule;

public class ServerAPI extends WebApiModule {

    @Override
    public boolean canHandle(String url) {
        return url.startsWith("server");
    }

    @Override
    public void handleApiRequest(HttpExchange httpExchange, String url) {
        String method = httpExchange.getRequestMethod();
        TS3Api ts3Api = Bot.getBot().getTs3Api();
        ServerManager serverManager = new ServerManager(ts3Api);
        if(method.equalsIgnoreCase("get")) {
            sendResponse(httpExchange, serverManager.getServerInfo());
        }
    }

}
