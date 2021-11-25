package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.function.EditChannelFunction;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;

public class ClientLeave extends TS3EventAdapter {

    private final Bot bot;
    private final Message message;
    private final Config config;

    public ClientLeave() {
        this.bot = Bot.getBot();
        this.message = new Message();
        this.config = new Config();
    }

    @Override
    public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {
        new EditChannelFunction().userCounter(message, config, bot);
    }

}
