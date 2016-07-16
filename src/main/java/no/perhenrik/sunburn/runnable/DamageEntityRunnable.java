package no.perhenrik.sunburn.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;

import no.perhenrik.sunburn.SunburnPlugin;

public class DamageEntityRunnable extends BukkitRunnable {

	private SunburnPlugin plugin = null;
	
	public DamageEntityRunnable(SunburnPlugin plugin) {
		this.plugin = plugin;
	}
	
    public void run(){
        World w = Bukkit.getWorld(this.plugin.getWorld());
        for(Entity e : w.getEntities()){
        	if(e instanceof Player && ((Player)e).getGameMode() == GameMode.CREATIVE) {
        		continue;
        	}
        	int damage;
        	if(e instanceof LivingEntity && (damage = getSunburnLevel((LivingEntity)e)) > 0){
        		//getLogger().info("  Entity: " + e.getType().getEntityClass().toString() + ", Damage: " + damage + ", Health: " + ((LivingEntity)e).getHealth());
        		double actualDamage = ((LivingEntity)e).getHealth() > damage ? damage : ((LivingEntity)e).getHealth();
        		((LivingEntity)e).setHealth(((LivingEntity)e).getHealth() - actualDamage);
        		e.playEffect(EntityEffect.HURT);
        		w.playEffect(e.getLocation(), Effect.SMOKE, 0);
            }
        }
    }


	private int getSunburnLevel(LivingEntity entity) {
		int level = 0;
		if(entity != null) {
			int sunLevel = entity.getLocation().getBlock().getLightFromSky();
			int blockLevel = entity.getLocation().getBlock().getLightLevel();
			level = sunLevel + blockLevel;
			if(level >= 30 && nothingAbove(entity.getLocation())) { 
				EntityEquipment eq = entity.getEquipment();
				if(eq != null) {
					if(eq.getHelmet() != null && eq.getHelmet().getType() == Material.LEATHER_HELMET) {
						level -= 5;
					}
					if(eq.getChestplate() != null && eq.getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
						level -= 10;
					}
					if(eq.getLeggings() != null && eq.getLeggings().getType() == Material.LEATHER_LEGGINGS) {
						level -= 10;
					}
					if(eq.getBoots() != null && eq.getBoots().getType() == Material.LEATHER_BOOTS) {
						level -= 5;
					}
				}
			} else {
				level = 0;
			}
		}
		return level < 0 ? 0 : Math.round(level / (float) 10);
	}

	private boolean nothingAbove(Location location) {
		boolean nothingAbove = true;
		int x = location.getBlockX();
		int z = location.getBlockZ();
		for(int y = location.getBlockY(); y < 256 && nothingAbove; y++) {
			Block block = location.getWorld().getBlockAt(x, y, z);
			plugin.debug("Checking block at " + x + "," + y + "," + z + ": " + block.getType());
			if(block.getType() == Material.STAINED_GLASS) {
				nothingAbove = false;
			}
		}
		return nothingAbove;
	}
}
