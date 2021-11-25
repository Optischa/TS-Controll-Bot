package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.function.SupportFunction;

public class ClientMoved extends TS3EventAdapter {

    private final Bot bot;

    public ClientMoved() {
        this.bot = Bot.getBot();
    }

    @Override
    public void onClientMoved(ClientMovedEvent clientMovedEvent) {
        new SupportFunction(bot).clientJoinSupportChannel(clientMovedEvent.getClientId(), clientMovedEvent.getTargetChannelId());
    }

}
