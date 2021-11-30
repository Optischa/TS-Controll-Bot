package de.optischa.teamspeak.commands.console;

import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.template.CreateTemplate;
import de.optischa.teamspeak.template.LoadTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TemplateCommand implements ConsoleCommand {

    @Override
    public void action(String[] args) {
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("create")) {
                new CreateTemplate().createTeamplate(Bot.getBot().getTs3Api(), args[2]);
            } else if (args[1].equalsIgnoreCase("load")) {
                new LoadTemplate().loadTemplate(Bot.getBot().getTs3Api(), args[2]);
            } else if (args[1].equalsIgnoreCase("download")) {
                InputStream in = null;
                try {
                    in = new URL(args[2]).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String[] fileName = new URL(args[2]).getPath().split("/");
                    Files.copy(in, Paths.get("templates/" + fileName[fileName.length-1]), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String key() {
        return "template";
    }

    @Override
    public String description() {
        return "Create or load a template. You can use load, create and download. You can you teamspeak template with create.";
    }

}
