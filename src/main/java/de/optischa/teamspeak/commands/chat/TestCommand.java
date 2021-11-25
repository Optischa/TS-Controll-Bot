package de.optischa.teamspeak.commands.chat;

public class TestCommand implements ChatCommand {
    @Override
    public boolean isCalled(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args) {

    }

    @Override
    public void executed(boolean sucess) {

    }

    @Override
    public String name() {
        return "test";
    }
}
