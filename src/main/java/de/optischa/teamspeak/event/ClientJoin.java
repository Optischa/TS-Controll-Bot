package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.function.AntiRecordingFunction;
import de.optischa.teamspeak.function.AntiVPNFunction;
import de.optischa.teamspeak.function.EditChannelFunction;
import de.optischa.teamspeak.function.WelcomeFunction;
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
        new EditChannelFunction().userCounter(message, config, bot);
        new WelcomeFunction(bot, clientJoinEvent.getClientId());
        new AntiRecordingFunction().antiRecording(clientJoinEvent.isClientRecording(), config, bot.getTs3Api(), clientJoinEvent.getClientId(), message);
        new AntiVPNFunction(bot, config).check(bot.getTs3Api().getClientInfo(clientJoinEvent.getClientId()));
    }

}
