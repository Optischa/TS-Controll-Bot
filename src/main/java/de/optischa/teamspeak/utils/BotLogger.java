package de.optischa.teamspeak.utils;

import de.optischa.teamspeak.Bot;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BotLogger {

	private final Logger logger = Logger.getLogger(Bot.class.getName());

	public BotLogger() {
		logger.setLevel(Level.ALL);
		InputStream stream = BotLogger.class.getClassLoader().getResourceAsStream("logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(Level level, String msg) {
		logger.log(level, msg);
	}

	public void createFile() {
		try {
			File file = new File("logs/lastet.log");
			if(file.exists()) {
				Path dirPath = Files.createTempDirectory("logs");
				Path filePath = Files.createTempFile(dirPath, "lastet", ".log");
				BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
				file.renameTo(new File("logs/" + attributes.creationTime().toString().replaceAll(":", " ") + ".log"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
