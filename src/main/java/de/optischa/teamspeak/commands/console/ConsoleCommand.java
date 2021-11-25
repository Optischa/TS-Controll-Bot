package de.optischa.teamspeak.commands.console;

public interface ConsoleCommand {

    void action(String[] args);
    String key();
    String description();

}
