#Teamspeak-Bot

1. [Creating a Addon](#addon) 
   1. [Command](#command)
2. [Downloading Bot](#download)


##Addon

Creating Addon for the Teamspeak Bot.

```java
public class ExampleAddon extends Addon {
    
    //Addon info for the Addon
    public Addon(AddonInfo info) {
        super(new AddonInfo() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String name() {
                return "Example Addon";
            }

            @Override
            public String description() {
                return "Description for the addon";
            }

            @Override
            public String author() {
                return "Optischa";
            }

            @Override
            public String version() {
                return "1.0";
            }
        });
    }

    @Override
    public void onEnable() {
        //Code for the addon
    }
}
```

###Command

```java
public class ExampleCommand implements ChatCommand {
    @Override
    public boolean isCalled(String[] strings) {
        //Return false call action 
        return false;
    }

    @Override
    public void action(String[] strings) {
        //isCalled false call action
    }

    @Override
    public void executed(boolean b) {
        //Always executed
    }

    @Override
    public String name() {
        //Key name
        return "example";
    }
}
```

###Register Command

```java
public class ExampleAddon extends Addon {

    // ...

    @Override
        public void onEnable() {
            // ...
            registerCommand(new ExampleCommand());
        }

}
```

##Rest API

| URL | Method | Description |
| ------------- | ------- |--------------------- |
| `/api/channel` | `GET` | Display all channel form teamspeak |
| `/api/channel` | `POST` | Create new channel |
| `/api/channel` | `DELETE` | Delete channel with `channelid`|
| `/api/server` | `GET` | Get all information of the Server |
| `/api/client` | `GET` | Get all online Clients with information's |


##Download