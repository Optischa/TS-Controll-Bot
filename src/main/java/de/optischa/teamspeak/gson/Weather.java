package de.optischa.teamspeak.gson;

public class Weather {

    private WeatherInfo[] weather;
    private MainWeather main;

    public WeatherInfo[] getWeather() {
        return weather;
    }

    public MainWeather getMain() {
        return main;
    }
}
