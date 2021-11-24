package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.*;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.helper.EditChannelHelper;
import de.optischa.teamspeak.helper.SupportHelper;
import de.optischa.teamspeak.helper.WelcomeHelper;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;

public class Events implements TS3Listener {

    private Bot bot;
    private Config config;
    private Message message;

    public Events(Bot bot) {
        this.bot = bot;
        this.config = new Config();
        this.message = new Message();
    }

    @Override
    public void onTextMessage(TextMessageEvent textMessageEvent) {

    }

    @Override
    public void onClientJoin(ClientJoinEvent clientJoinEvent) {
        new EditChannelHelper().userCounter(message, config, bot);
        new WelcomeHelper(bot, clientJoinEvent.getClientId());
    }

    @Override
    public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {
        new EditChannelHelper().userCounter(message, config, bot);
    }

    @Override
    public void onServerEdit(ServerEditedEvent serverEditedEvent) {

    }

    @Override
    public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

    }

    @Override
    public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

    }

    @Override
    public void onClientMoved(ClientMovedEvent clientMovedEvent) {
        new SupportHelper(bot).clientJoinSupportChannel(clientMovedEvent.getClientId(), clientMovedEvent.getTargetChannelId());
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

    }

    @Override
    public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

    }

    @Override
    public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

    }

    @Override
    public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

    }

    @Override
    public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

    }
}
