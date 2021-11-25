package de.optischa.teamspeak.utils;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class CommandParser {

    public commandContainer parser(String raw) {
        Config config = new Config();
        String beheaded = raw.replaceFirst((String) config.getConfig().get("prefix"), "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0];
        ArrayList<String> split = new ArrayList<>();
        Collections.addAll(split, splitBeheaded);
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new commandContainer(raw, beheaded, splitBeheaded, invoke, args);
    }

    public static class commandContainer {

        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;

        public commandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
        }
    }

}
