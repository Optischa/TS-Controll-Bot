package de.optischa.teamspeak.addons.core;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.optischa.teamspeak.Bot;
import de.optischa.teamspeak.commands.chat.ChatCommand;
import de.optischa.teamspeak.commands.console.ConsoleCommand;
import de.optischa.teamspeak.web.WebApiModule;
import lombok.*;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class Addon {
	
	private final String name, description, author, version;
	private File dataFolder;
	private ClassLoader loader;
	private boolean loaded = false;
	
	public Addon(AddonInfo info) {
		this.name = info.name();
		this.description = info.description();
		this.author = info.author();
		this.version = info.version();
	}
	
	final void init(AddonClassLoader classLoader, File dataFolder) {
		this.dataFolder = dataFolder;
		this.loader = classLoader;
		this.onEnable();
		this.loaded = true;
	}

	public void registerChatCommand(ChatCommand chatCommand) {
		Bot.getBot().getCommandManager().getCommandHelper().commands.put(chatCommand.name(), chatCommand);
	};

	public void registerWebAPIModule(WebApiModule webApiModule) {
		Bot.getBot().getWebApiModules().add(webApiModule);
	}

	public void registerEventListener(TS3EventAdapter ts3EventAdapter) {
		Bot.getBot().getTs3Api().addTS3Listeners(ts3EventAdapter);
	};

	public TS3Api getTS3Api() {
		return Bot.getBot().getTs3Api();
	}

	public void registerConsoleCommand(ConsoleCommand consoleCommand) {
		Bot.getBot().getConsoleManager().registerCommands(consoleCommand);
	}
	
	public abstract void onEnable();
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface AddonInfo {
		String name();
		
		String description() default "";
		
		String author();
		
		String version() default "1.0";
	}
}
