package no.perhenrik.sunburn;

import org.bukkit.plugin.java.JavaPlugin;
import no.perhenrik.sunburn.runnable.DamageEntityRunnable;
import no.perhenrik.sunburn.runnable.KillPlantsRunnable;

public class SunburnPlugin extends JavaPlugin {

    static final String DAMAGE_TICKS = "Damage ticks";
    static final String ENABLED_WORLD = "World";
    static final String NATURE_TICKS = "Nature ticks";
    static final String DEBUG = "Debug";
    static final String DAMAGE_PLANTS = "Damage plants";
    static final String DAMAGE_ENTITIES = "Damage entities";

    @Override
    public void onLoad() {
    	saveDefaultConfig();
    	reloadConfig();
    	info(String.format("%s: %s", ENABLED_WORLD, getConfig().getString(ENABLED_WORLD, "world")));
    	info(String.format("%s: %d", DAMAGE_TICKS ,getConfig().getInt(DAMAGE_TICKS, 200)));
    	info(String.format("%s: %d", NATURE_TICKS, getConfig().getInt(NATURE_TICKS, 15)));
    	info(String.format("%s: %s", DEBUG, getConfig().getBoolean(DEBUG, false)));
        info(String.format("%s: %s", DAMAGE_PLANTS, getConfig().getBoolean(DAMAGE_PLANTS, true)));
        info(String.format("%s: %s", DAMAGE_ENTITIES, getConfig().getBoolean(DAMAGE_ENTITIES, true)));
    }

    void reload() {
    	reloadConfig();
    }
    
    public String getWorld() {
    	return getConfig().getString(ENABLED_WORLD,"world");
    }
    
    public void SetWorld(String w) {
    	getConfig().set(ENABLED_WORLD, w);
    }
    

    int getDamageTicks() {
    	return getConfig().getInt(DAMAGE_TICKS, 200);
    }
    
    void setDamageTicks(int t) {
    	getConfig().set(DAMAGE_TICKS, t);
		saveConfig();
	}
    
    int getNatureTicks() {
    	return getConfig().getInt(NATURE_TICKS, 15);
    }
    
    void setNatureTicks(int t) {
    	getConfig().set(NATURE_TICKS, t);
		saveConfig();
	}

	public boolean getDebug() {
		return getConfig().getBoolean(DEBUG, false);
	}

	void setDebug(boolean d) {
		getConfig().set(DEBUG, d);
		saveConfig();
	}
	
    void info(String msg) {
        getLogger().info(msg);
    }
    
    public void debug(String msg) {
    	if(getDebug()) {
    		getLogger().info("DEBUG: " + msg);
    	}
    }
    
    void severe(String msg) {
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
