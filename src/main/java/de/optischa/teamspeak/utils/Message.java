package de.optischa.teamspeak.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private final File file = new File("message.json");

    public Message() {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setDefault();
        }
    }

    public JSONObject getMessages() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(file.getName()));
            return (JSONObject) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setDefault() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("usercounter", "[cspacer0]x User Online %onlineuser%/%maxuser%");
        JsonObject jo = builder.build();
        try {
            FileWriter fw = new FileWriter(file.getName());
            JsonWriter jsonWriter = Json.createWriter(fw);
            jsonWriter.writeObject(jo);
            jsonWriter.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
