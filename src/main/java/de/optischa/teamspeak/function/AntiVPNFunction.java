package de.optischa.teamspeak.function;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.utils.BotLogger;
import de.optischa.teamspeak.utils.Config;
import de.optischa.teamspeak.utils.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import vpn.detection.Response;
import vpn.detection.VPNDetection;

import java.io.IOException;
import java.util.logging.Level;

public class AntiVPNFunction {

    private final BotLogger logger;
    private final Message message = new Message();
    private final Bot bot;
    private final Config config;

    public AntiVPNFunction(Bot bot, Config config) {
        this.logger = new BotLogger();
        this.bot = bot;
        this.config = config;
    }

    public void check(Client client) {
        JSONObject jsonObject = (JSONObject) config.getConfig().get("configs");
        JSONArray jsonArray = (JSONArray) config.getConfig().get("whitelistip");
        Object object = jsonObject.get("antivpn");
        if (object instanceof Boolean) {
            if (Boolean.parseBoolean(String.valueOf(object))) {
                if (jsonArray.contains(client.getId())) {
                    return;
                }
                if (client.isServerQueryClient()) {
                    return;
                }
                VPNDetection vpn_detection = new VPNDetection();
                new Thread(() -> {
                    try {
                        String ipToLookup = client.getIp();
                        Response api_response = vpn_detection.getResponse(ipToLookup);

                        if (api_response.status.equals("success")) {
                            if (api_response.hostip) {
                                bot.getTs3Api().kickClientFromServer(String.valueOf(message.getMessages().get("kickvpn")), client.getId());
                                logger.log(Level.INFO, "Client with Name " + client.getNickname() + " and the ID " + client.getId() + " join with a VPN. The VPN IP Address is " + client.getIp());
                            }
                        } else {
                            logger.log(Level.SEVERE, "Error: " + api_response.msg);
                        }

                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "Error: " + ex.getMessage());
                    }
                }).start();
            }
        }
    }

}
