package de.optischa.teamspeak.commands.chat;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.gson.IPLocation;
import de.optischa.teamspeak.gson.Weather;
import de.optischa.teamspeak.manager.WeatherManager;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WeatherCommand implements ChatCommand {
    @Override
    public boolean isCalled(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args, Client client) {
        try {
            WeatherManager weatherManager = new WeatherManager();
            Config config = Bot.getBot().getConfig();
            Message message = new Message();
            IPLocation ipLocation = weatherManager.getLocation(client.getIp());
            Weather weather = weatherManager.getWeather(ipLocation, client.getCountry(), (String) config.getConfig().get("weatherapikey"));

            Bot.getBot().getTs3Api().sendPrivateMessage(client.getId(), String.valueOf(message.getMessages().get("weathermessage"))
                    .replaceAll("%temp%", String.valueOf(weather.getMain().getTemp()))
                    .replaceAll("%city%", ipLocation.getCity())
                    .replaceAll("%feelingtemp%", String.valueOf(weather.getMain().getFeels_like()))
                    .replaceAll("%description%", weather.getWeather()[0].getDescription())
                    .replaceAll("%maxtemp%", String.valueOf(weather.getMain().getTemp_max()))
                    .replaceAll("%mintemp%", String.valueOf(weather.getMain().getTemp_min())));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executed(boolean success) {

    }

    @Override
    public String name() {
        return "weather";
    }
}
