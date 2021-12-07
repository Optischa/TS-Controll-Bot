package de.optischa.teamspeak.manager;

import com.google.gson.Gson;
import de.optischa.teamspeak.gson.IPLocation;
import de.optischa.teamspeak.gson.Weather;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherManager {

    public IPLocation getLocation(String ip) throws IOException, ParseException {

        URL url = new URL("http://ip-api.com/json/" + ip);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(responseStream, StandardCharsets.UTF_8));

        return new Gson().fromJson(jsonObject.toJSONString(), IPLocation.class);
    }

    public Weather getWeather(IPLocation ipLocation, String lang, String apiKey) throws IOException, ParseException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + ipLocation.getCity() + "&appid=" + apiKey + "&units=metric&lang=" + lang);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(responseStream, StandardCharsets.UTF_8));
        return new Gson().fromJson(jsonObject.toJSONString(), Weather.class);
    }

}
