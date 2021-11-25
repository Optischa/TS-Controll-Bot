package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.helper.EditChannelHelper;
import de.optischa.teamspeak.helper.WelcomeHelper;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;

public class ClientJoin extends TS3EventAdapter {

    private final Bot bot;
    private final Message message;
    private final Config config;

    public ClientJoin() {
        this.bot = Bot.getBot();
        this.message = new Message();
        this.config = new Config();
    }

    @Override
    public void onClientJoin(ClientJoinEvent clientJoinEvent) {
        new EditChannelHelper().userCounter(message, config, bot);
        new WelcomeHelper(bot, clientJoinEvent.getClientId());
    }

}
