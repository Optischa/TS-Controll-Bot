package de.optischa.teamspeak.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public abstract class WebApiModule implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            String method = httpExchange.getRequestMethod();
            String url = httpExchange.getRequestURI().toString();
            System.out.println(method + "\t" + url);

            handleApiRequest(httpExchange,url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendResponse(HttpExchange exchange, String response) {
        try {
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bs.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bs);
            os.close();
            exchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readBody (InputStream body){
        return new BufferedReader(new InputStreamReader(body)).lines().collect(Collectors.joining("\n"));
    }

    public abstract boolean canHandle(String url);
    public abstract void handleApiRequest(HttpExchange httpExchange, String url);

  

}
