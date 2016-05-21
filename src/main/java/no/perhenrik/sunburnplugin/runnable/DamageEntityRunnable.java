package no.perhenrik.sunburnplugin.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;

import no.perhenrik.sunburnplugin.SunburnPlugin;

public class DamageEntityRunnable extends BukkitRunnable {

	private SunburnPlugin plugin = null;
	
	public DamageEntityRunnable(SunburnPlugin plugin) {
		this.plugin = plugin;
	}
	
    public void run(){
        World w = Bukkit.getWorld(this.plugin.getWorld());
		//plugin.getLogger().info("World: " + w.getName());
        for(Entity e : w.getEntities()){
        	if(e instanceof Player && ((Player)e).getGameMode() == GameMode.CREATIVE) {
        		continue;
        	}
        	int damage = 0;
        	if(e instanceof LivingEntity && (damage = getSunburnLevel((LivingEntity)e)) > 0){
        		//getLogger().info("  Entity: " + e.getType().getEntityClass().toString() + ", Damage: " + damage + ", Health: " + ((LivingEntity)e).getHealth());
        		double actualDamage = ((LivingEntity)e).getHealth() > damage ? damage : ((LivingEntity)e).getHealth();
        		((LivingEntity)e).setHealth(((LivingEntity)e).getHealth() - actualDamage);
        		e.playEffect(EntityEffect.HURT);
        		w.playEffect(e.getLocation(), Effect.SMOKE, 0);
            }
        }
    }


	public int getSunburnLevel(LivingEntity entity) {
		int level = 0;
		int sunLevel = 0;
		int blockLevel = 0;
		if(entity != null) {
			//getLogger().info("  Class: " + entity.getName());
			sunLevel = entity.getLocation().getBlock().getLightFromSky();
			blockLevel = entity.getLocation().getBlock().getLightLevel();
			level = sunLevel + blockLevel;
			//getLogger().info("    Light Level: " + level);
			if(level >= 30) { 
				EntityEquipment eq = ((LivingEntity) entity).getEquipment();
				if(eq != null) {
					if(eq.getHelmet() != null && eq.getHelmet().getType() == Material.LEATHER_HELMET) {
						level -= 5;
					}
					//getLogger().info("    Level after helmet: " + level);
					if(eq.getChestplate() != null && eq.getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
						level -= 10;
					}
					//getLogger().info("    Level after chestplate: " + level);
					if(eq.getLeggings() != null && eq.getLeggings().getType() == Material.LEATHER_LEGGINGS) {
						level -= 10;
					}
					//getLogger().info("    Level after leggings: " + level);
					if(eq.getBoots() != null && eq.getBoots().getType() == Material.LEATHER_BOOTS) {
						level -= 5;
					}
					//getLogger().info("    Level after boots: " + level);
				}
			} else {
				level = 0;
			}
		}
		//getLogger().info("    Final Level: " + level);
		return level < 0 ? 0 : (int) Math.round(level / (float) 10);
	}
}
