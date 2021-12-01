package de.optischa.teamspeak.web;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleReflectionHelper {
    @SuppressWarnings("unchecked")
    private static <T> T construct(Constructor<? extends T>[] constructors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for(Constructor<?> ctor : constructors) {
            if(ctor.getParameterCount() == 0) {
                return (T) ctor.newInstance();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> load(String packageName, Class<T> type, Boolean ignoreSubPackets) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends T>> cmds = reflections.getSubTypesOf(type);

        List<T> commands = new ArrayList<>();

        for(Class<? extends T> commandClass : cmds){
            if(!commandClass.getPackage().getName().equals(packageName) && ignoreSubPackets)
                continue;

            T cmd = (T) construct(commandClass.getConstructors());
            if(cmd != null) {
                commands.add(cmd);
            }
        }
        return commands;
    }
}
