package de.optischa.teamspeak.commands.chat;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TestCommand implements ChatCommand {

    @Override
    public boolean isCalled(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args, Client client) {

    }

    @Override
    public void executed(boolean sucess) {

    }

    @Override
    public String name() {
        return "test";
    }
}
