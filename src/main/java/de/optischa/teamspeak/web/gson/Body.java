package de.optischa.teamspeak.web.gson;

public class Body {

    private int channelid;

    public int getChannelid() {
        return channelid;
    }

    // create Channels
    private String channelname;
    private ChannelProperties[] channelproperties;

    public String getChannelname() {
        return channelname;
    }

    public ChannelProperties[] getChannelproperties() {
        return channelproperties;
    }

    // client info
    private String function;
    private int clientid;

    public int getClientid() {
        return clientid;
    }

    public String getFunction() {
        return function;
    }
}
