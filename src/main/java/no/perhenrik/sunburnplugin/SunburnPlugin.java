package no.perhenrik.sunburnplugin;

import org.bukkit.plugin.java.JavaPlugin;
import no.perhenrik.sunburnplugin.SunburnCommandExecutor;
import no.perhenrik.sunburnplugin.runnable.DamageEntityRunnable;
import no.perhenrik.sunburnplugin.runnable.KillPlantsRunnable;

public class SunburnPlugin extends JavaPlugin {
	
    @Override
    public void onLoad() {
    	saveDefaultConfig();
    	reloadConfig();
    	info("world: " + getConfig().getString("world","world"));
    	info("damageticks: " + getConfig().getInt("damageticks", 200));
    	info("natureticks: " + getConfig().getInt("natureticks", 15));   
    	info("debug: " + getConfig().getBoolean("debug", false)); 
    }

    public void reload() {
    	reloadConfig();
    }
    
    public String getWorld() {
    	return getConfig().getString("world","world");
    }
    
    public void World(String w) {
    	getConfig().set("world", w);
    }
    

    public int getDamageTicks() {
    	return getConfig().getInt("damageticks", 200);
    }
    
    public void setDamageTicks(int t) {
    	getConfig().set("damageticks", t);
		saveConfig();
	}
    
    public int getNatureTicks() {
    	return getConfig().getInt("natureticks", 15);
    }
    
    public void setNatureTicks(int t) {
    	getConfig().set("natureticks", t);
		saveConfig();
	}

	public boolean getDebug() {
		return getConfig().getBoolean("debug", false);
	}

	public void setDebug(boolean d) {
		getConfig().set("debug", d);
		saveConfig();
	}
	
    public void info(String msg) {
        getLogger().info(msg);
    }
    
    public void debug(String msg) {
    	if(getDebug()) {
    		getLogger().info("DEBUG: " + msg);
    	}
    }
    
    public void severe(String msg) {
        getLogger().severe(msg);
    }

    @Override
    public void onEnable() {
    	this.getCommand("sb").setExecutor(new SunburnCommandExecutor(this));
        new DamageEntityRunnable(this).runTaskTimer(this, 10, getDamageTicks());
        new KillPlantsRunnable(this).runTaskTimer(this, 10, getNatureTicks());
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }

}
