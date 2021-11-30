package de.optischa.teamspeak.template;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import de.optischa.teamspeak.utils.BotLogger;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadTemplate {

    @Getter
    private final BotLogger logger;

    public LoadTemplate() {
        logger = new BotLogger();
    }

    public void loadTemplate(TS3Api ts3Api, String name) {
        File file = new File("templates/" + name + ".json");
        if (file.exists()) {
            for (Channel channel : ts3Api.getChannels()) {
                if(!channel.isDefault()) {
                    ts3Api.deleteChannel(channel.getId());
                }
            }
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader("templates/" + file.getName()));
                JSONObject jsonObject = (JSONObject) obj;
                loadChannels((JSONArray) jsonObject.get("channels"), ts3Api);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    private void loadChannels(JSONArray jsonArray, TS3Api ts3Api) {
        for (Object object : jsonArray) {
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                Map<ChannelProperty, String> option = new HashMap();
                JSONArray channelpropertys = (JSONArray) jsonObject.get("channelpropertys");
                Map<ChannelProperty, String> properties = new HashMap<>();
                properties.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "1");
                properties.put(ChannelProperty.CHANNEL_FLAG_SEMI_PERMANENT, "1");
                properties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "1");
                properties.put(ChannelProperty.CHANNEL_DESCRIPTION, "Test");
                for (Object channelProperty : channelpropertys) {
                    if (channelProperty instanceof JSONObject) {
                        JSONObject channelPropertyJSON = (JSONObject) channelProperty;
                        String name = String.valueOf(channelPropertyJSON.get("name"));
                        if (name.equalsIgnoreCase("CHANNEL_FLAG_MAXCLIENTS_UNLIMITED") || name.equalsIgnoreCase("CHANNEL_FLAG_SEMI_PERMANENT") || name.equalsIgnoreCase("CHANNEL_MAXCLIENTS") || name.equalsIgnoreCase("CHANNEL_DESCRIPTION") || name.equalsIgnoreCase("channel_flag_permanent") || name.equalsIgnoreCase("channel_password") && !String.valueOf(channelPropertyJSON.get("value")).equalsIgnoreCase("") || name.equalsIgnoreCase("channel_maxfamilyclients") || name.equalsIgnoreCase("CHANNEL_CODEC_QUALITY") || name.equalsIgnoreCase("CHANNEL_DELETE_DELAY") && !String.valueOf(channelPropertyJSON.get("value")).equalsIgnoreCase("")) {
                            option.put(ChannelProperty.valueOf(String.valueOf(channelPropertyJSON.get("name")).toUpperCase()), (String) channelPropertyJSON.get("value"));
                        }
                    }
                }
                String name = String.valueOf(jsonObject.get("channelname"));
                System.out.println(name);
                System.out.println(option);
                ts3Api.createChannel(name, option);
            }
        }
    }
}
