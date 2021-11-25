package de.optischa.teamspeak.addons;

import de.optischa.teamspeak.addons.core.AddonManager;
import de.optischa.teamspeak.utils.BotLogger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.logging.Level;

@RequiredArgsConstructor
public class AddonCore {
	
	private static final File ADDON_FOLDER = new File("addons");
	@Getter
	private static BotLogger logger;
	@Getter
	private static AddonManager addonManager;
	
	public void enable() {
		logger = new BotLogger();
		
		if(!getAddonFolder().exists())
			getAddonFolder().mkdirs();
		
		getLogger().log(Level.INFO, "Loading addons...");
		AddonCore.addonManager = new AddonManager(getAddonFolder());
		
		AddonCore.getAddonManager().loadAddons();
		
		getLogger().log(Level.INFO, "Successfully loaded " + AddonCore.getAddonManager().getAddons().length + " Addons");
	}
	
	public static File getAddonFolder() {
		return ADDON_FOLDER;
	}
}
