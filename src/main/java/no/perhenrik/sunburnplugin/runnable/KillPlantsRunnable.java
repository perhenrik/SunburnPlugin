package no.perhenrik.sunburnplugin.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import no.perhenrik.sunburnplugin.SunburnPlugin;

import java.util.List;

public class KillPlantsRunnable extends BukkitRunnable {

	private SunburnPlugin plugin = null;
	private List<World> worlds = null;
	private Chunk[] chunks = null;
	private int worldCounter = 0;
	private int chunkCounter = 0;
	private boolean debug = false;
	
	public KillPlantsRunnable(SunburnPlugin plugin) {
		this.plugin = plugin;
		this.debug = plugin.getDebug();
	}
	
    public void run(){
		debug = plugin.getDebug();
    	if(worlds == null || worlds.isEmpty() || worldCounter >= worlds.size()) {
    		worlds = Bukkit.getWorlds();
    		worldCounter = 0;
    	}
    	World w = worlds.get(worldCounter);

    	if(debug) {
    		//plugin.getLogger().info(this.getClass() + ": World: " + w.getName());
    	}
    		
    	if(w.getName().equals(plugin.getWorld())) {
    		if(chunks == null || chunks.length < 1 || chunkCounter >= chunks.length) {
    			chunks = worlds.get(worldCounter++).getLoadedChunks();
    			chunkCounter = 0;
    		}
        	if(debug) {
        		plugin.getLogger().info(w.getName() + ": Handling chunk " + chunkCounter + " of " + chunks.length);
        	}
        		
			handleChunk(chunks[chunkCounter++]);
    	} else {
    		worldCounter++;
    	}
    }

    private void handleChunk(Chunk c) {
    	if(!c.isLoaded()) {
    		return;
    	}
    	
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				Block b = null;
				int lightLevel = 0;
				for(int y = 255; y > 0; y--) {
					b = c.getBlock(x, y, z);
					switch(b.getType()) { // sun shines through branches, snow, glass and fences
					case LOG:
					case FENCE:
					case FENCE_GATE:
					case SNOW:
					case GLASS:
					case THIN_GLASS:
						continue;
					default:
						break;
					}
					if(b.getType() != Material.AIR) {
						break;
					} else {
						int l = getSunburnLevel(b);
						lightLevel = (l > lightLevel ? l : lightLevel); 
					}
				}
				if(b != null && lightLevel > 0) {
					switch(b.getType()) {
					case BROWN_MUSHROOM:
					case CARROT:
					case CHORUS_FLOWER:
					case CHORUS_PLANT:
					case COCOA:
					case CROPS:
					case DOUBLE_PLANT:
					case LEAVES:
					case LEAVES_2:
					case LONG_GRASS:
					case MELON_BLOCK:
					case POTATO:
					case PUMPKIN:
					case RED_MUSHROOM:
					case RED_ROSE:
					case SAPLING:
					case SUGAR_CANE:
					case SUGAR_CANE_BLOCK:
					case VINE:
					case WATER_LILY:
					case WHEAT:
					case YELLOW_FLOWER:
						b.breakNaturally();
						break;
					case MYCEL:
					case GRASS:
						b.setType(Material.DIRT);
						break;
					default:
						break;	
					}
				}
			}
		}
    }

	public int getSunburnLevel(Block block) {
		int level = 0;
		int sunLevel = 0;
		int blockLevel = 0;
		if(block != null) {
			sunLevel = block.getLightFromSky();
			blockLevel = block.getLightLevel();
			level = sunLevel + blockLevel;
			if(level < 30) { 
				level = 0;
			}
		}
		return level;
	}
}
