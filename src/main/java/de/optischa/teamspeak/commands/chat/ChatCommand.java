package de.optischa.teamspeak.commands.chat;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public interface ChatCommand {

    boolean isCalled(String[] args);
    void action(String[] args, Client client);
    void executed(boolean success);
    String name();

}
