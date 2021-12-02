package de.optischa.teamspeak.web;

import com.sun.net.httpserver.HttpServer;
import de.optischa.teamspeak.manager.web.WebApiManager;
import de.optischa.teamspeak.utils.BotLogger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.logging.Level;

public class WebAPI {
    private boolean isRunning = false;
    private HttpServer server;

    public void start() {
        if(isRunning){
            try {
                throw new AlreadyBoundException();
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
        }

        BotLogger logger = new BotLogger();


        try {
            server = HttpServer.create();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.bind(new InetSocketAddress(8081), 5);
        } catch (IOException e) {
            e.printStackTrace();
        }


        server.createContext("/api/", WebApiManager.getInstance());
        server.start();

        logger.log(Level.INFO, "WebAPI start");

        List<WebApiModule> apiModules = null;
        try {
            apiModules = ModuleReflectionHelper.load("de.optischa.teamspeak.web.api", WebApiModule.class,false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        WebApiManager apiManager = WebApiManager.getInstance();
        for (WebApiModule apiModule : apiModules) {
            try {
                apiManager.addModule(apiModule);
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
        }


        isRunning = true;
    }


    public void stop(){
        if(server == null)
            return;


        server.stop(5000);

        isRunning = false;
    }
}
