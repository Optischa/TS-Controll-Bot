package de.optischa.teamspeak.manager.web;

import com.sun.net.httpserver.HttpExchange;
import de.optischa.teamspeak.web.WebApiModule;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;

public class WebApiManager extends WebApiModule {
    private static final WebApiManager instance = new WebApiManager();

    private final List<WebApiModule> modules = new ArrayList<>();
    public void addModule(WebApiModule module) throws AlreadyBoundException {
        if(this.modules.contains(module))
            throw new AlreadyBoundException("This module is already bound to the WebApiHelper!");

        this.modules.add(module);
    }


    @Override
    public boolean canHandle(String url) {
        return url.contains(baseUrlIdentifier);
    }

    private static final String baseUrlIdentifier = "/api/";
    @Override
    public void handleApiRequest(HttpExchange httpExchange, String url) {
        String subUrl = url.substring(url.indexOf(baseUrlIdentifier) + baseUrlIdentifier.length());
        if(subUrl.startsWith("/")) {
            subUrl = subUrl.substring(1);
        }

        for (WebApiModule handler : this.modules){
            if(handler.canHandle(subUrl)) {
                handler.handleApiRequest(httpExchange, subUrl);
                break;
            }
        }
    }

    public static WebApiManager getInstance() {
        return instance;
    }
}
