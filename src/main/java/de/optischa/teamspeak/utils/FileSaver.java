package de.optischa.teamspeak.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    public int getVolume(Guild guild) {
        Config music = new Config(new File(guild.getId() + ".json"));
        return music.getInt("volume");
    }

    public void setVolume(Guild guild, int volume) {
        Config music = new Config(new File(guild.getId() + ".json"));
        JSONObject jsonObject = new JSONObject(music.toString());
        jsonObject.put("volume", volume);
        save(jsonObject, guild.getId() + ".json");
    }

    private void save(JSONObject jsonObject, String pathname) {
        try (FileWriter file = new FileWriter(new File(pathname).getName())) {
            file.append(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
