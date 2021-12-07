package de.optischa.teamspeak.event;

import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

import java.sql.SQLException;

public class TextMessage extends TS3EventAdapter {

    private final Bot bot;
    private final JSONObject config;

    public TextMessage() {
        this.bot = Bot.getBot();
        this.config = new Config().getConfig();
    }

    @Override
    public void onTextMessage(TextMessageEvent textMessageEvent) {
        try {
            if (textMessageEvent.getMessage().startsWith((String) config.get("prefix"))) {
                try {
                    bot.getCommandManager().getCommandHelper().handleCommand(bot.getCommandManager().getCommandParser().parser(textMessageEvent.getMessage()), bot.getTs3Api().getClientInfo(textMessageEvent.getInvokerId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
