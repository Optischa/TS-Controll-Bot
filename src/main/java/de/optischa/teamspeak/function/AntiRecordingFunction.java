package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONObject;

public class AntiRecordingFunction {

    public void antiRecording(boolean isRecording, Config config, TS3Api ts3Api, int clientId, Message message) {
        JSONObject jsonObject = (JSONObject) config.getConfig().get("configs");
        if((Boolean) jsonObject.getOrDefault("antirecord", false)) {
            if(isRecording) {
                ts3Api.kickClientFromServer((String) message.getMessages().getOrDefault("kickclientbyantirecording", "You kicken while you recording the voice!"), clientId);
            }
        }
    }

}
