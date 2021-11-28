package de.optischa.teamspeak.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    private final File file = new File("config.json");

    public boolean isExist() {
        return file.exists();
    }

    public void init() {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setDefault();
        }
    }

    public JSONObject getConfig() {
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
        builder.add("host", "127.0.0.1");
        builder.add("queryport", 10011);
        builder.add("name", "Bot");
        builder.add("hostport", 9987);
        builder.add("username", "serveradmin");
        builder.add("password", "");

        JsonObjectBuilder configArrayBuilder = Json.createObjectBuilder();

        configArrayBuilder.add("usercount", false);
        configArrayBuilder.add("usercountchannelid", 1);
        configArrayBuilder.add("afkmoved", false);
        configArrayBuilder.add("afktime", 60);
        configArrayBuilder.add("afkchannelid", 1);
        JsonArrayBuilder supportgroupid = Json.createArrayBuilder();
        supportgroupid.add(1);
        configArrayBuilder.add("supportgroupsid", supportgroupid);
        configArrayBuilder.add("supportsystem", false);
        configArrayBuilder.add("supportchannelid", 1);
        configArrayBuilder.add("welcomemessage", false);
        configArrayBuilder.add("antirecord", false);
        configArrayBuilder.add("kickclientname", false);
        JsonArrayBuilder backlistname = Json.createArrayBuilder();
        supportgroupid.add("");
        configArrayBuilder.add("backlistname", backlistname);
        configArrayBuilder.add("renamechannelname", false);
        JsonArrayBuilder backlistchannel = Json.createArrayBuilder();
        supportgroupid.add("");
        configArrayBuilder.add("backlistchannel", backlistchannel);

        builder.add("configs", configArrayBuilder);
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
