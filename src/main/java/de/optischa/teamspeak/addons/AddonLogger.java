package de.optischa.teamspeak.addons;

import lombok.RequiredArgsConstructor;

import java.util.logging.Level;

@RequiredArgsConstructor
public class AddonLogger {
	
	private static final String PREFIX = "[TeamSpeakBot] ";
	private static final String INFO = "[INFO]";
	private static final String ERROR = "[ERROR]";
	
	public void log(Level level, String msg) {
		StringBuilder builder = new StringBuilder();
		builder.append(AddonLogger.PREFIX);
		
		if(level == Level.INFO)
			builder.append(AddonLogger.INFO);
		else if(level == Level.SEVERE)
			builder.append(AddonLogger.ERROR);
		
		builder.append(" ").append(msg);
		
		System.out.println(builder.toString());
	}
}
