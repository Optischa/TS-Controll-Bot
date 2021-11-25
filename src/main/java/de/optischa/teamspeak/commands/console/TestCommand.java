package de.optischa.teamspeak.commands.console;

public class TestCommand implements ConsoleCommand {

    @Override
    public void action(String[] args) {
        System.out.println("eeee");
    }

    @Override
    public String key() {
        return "test";
    }

    @Override
    public String description() {
        return "Das ist ein test Command";
    }
}
