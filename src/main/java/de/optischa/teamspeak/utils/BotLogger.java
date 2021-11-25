package de.optischa.teamspeak.utils;

import lombok.RequiredArgsConstructor;

import java.util.logging.Level;

@RequiredArgsConstructor
public class BotLogger {
	
	private static final String PREFIX = "[TeamSpeakBot] ";
	private static final String INFO = "[INFO]";
	private static final String ERROR = "[ERROR]";
	
	public void log(Level level, String msg) {
		StringBuilder builder = new StringBuilder();
		builder.append(BotLogger.PREFIX);
		
		if(level == Level.INFO)
			builder.append(BotLogger.INFO);
		else if(level == Level.SEVERE)
			builder.append(BotLogger.ERROR);
		
		builder.append(" ").append(msg);
		
		System.out.println(builder.toString());
	}
}
