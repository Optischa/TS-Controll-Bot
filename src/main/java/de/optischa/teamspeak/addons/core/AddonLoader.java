package de.optischa.teamspeak.addons.core;

import de.optischa.teamspeak.addons.AddonCore;
import de.optischa.teamspeak.addons.core.exceptions.InvalidAddonException;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddonLoader {
	
	public static Addon loadAddon(File file) throws InvalidAddonException {
		Validate.notNull(file, "FileHelp cannot be null");
		
		if(!file.exists())
			throw new InvalidAddonException(new FileNotFoundException(String.valueOf(file.getPath()) + " does not exist"));
		
		File dataFolder = new File(AddonCore.getAddonFolder() + File.separator + file.getName());
		
		if(!dataFolder.exists())
			dataFolder.mkdir();
		
		AddonClassLoader loader;
		
		try {
			loader = new AddonClassLoader(AddonLoader.class.getClassLoader(), file, dataFolder);
			
			loader.close();
			
			return loader.addon;
		} catch(IOException e) {
			throw new InvalidAddonException(e);
		}
	}
}
