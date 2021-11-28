package de.optischa.teamspeak.manager;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.helper.CommandsReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class EventRegisterManager {

    private final TS3Api ts3Api;

    public EventRegisterManager(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public void registerEvents() {
        try {
            List<TS3EventAdapter> ts3EventAdapters = CommandsReflectionHelper.load("de.optischa.teamspeak.event", TS3EventAdapter.class,false);
            for (TS3EventAdapter ts3EventAdapter : ts3EventAdapters) {
                ts3Api.addTS3Listeners(ts3EventAdapter);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
