package de.optischa.teamspeak.commands.chat;

public interface ChatCommand {

    boolean isCalled(String[] args);
    void action(String[] args);
    void executed(boolean sucess);
    String name();

}
