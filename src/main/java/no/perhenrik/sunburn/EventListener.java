package no.perhenrik.sunburn;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {

	private SunburnPlugin plugin ;
	 
	public EventListener(Plugin instance) {
		plugin = (SunburnPlugin) instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}	
}
