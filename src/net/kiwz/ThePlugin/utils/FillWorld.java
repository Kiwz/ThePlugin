package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class FillWorld {
	public int x;
	public int z;
	private World world;
	private HandleWorlds hWorlds = new HandleWorlds();
	private HashMap<Integer, FillWorld> chunks = new HashMap<Integer, FillWorld>();
	private Long totTime = System.currentTimeMillis();
	private Long time = System.currentTimeMillis();
	private int gen = 0;
	private int tempGen = 0;
	
	public void generateChunks(CommandSender sender, String worldName) {
		if (Bukkit.getServer().getWorld(worldName) == null) {
			sender.sendMessage(ThePlugin.c2 + worldName + " finnes ikke");
			return;
		}
		
		world = Bukkit.getServer().getWorld(worldName);
		int d = hWorlds.getBorder(world) * 2;
		d = (d / 16) + 1 + 12 + 12;
        int x = 0;
        int z = 0;
        int dx = 0;
        int dz = -1;
        int t;
        
        for (int i = 0; i < d * d; i++) {
            if ((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1 - z))) {
                t = dx;
                dx = -dz;
                dz = t;
            }
        	buildMap(i, x, z);
            x += dx;
            z += dz;
        }
        executeMap();
    }
	
	private void buildMap(int key, int x, int z) {
		FillWorld value = new FillWorld();
		value.x = x;
		value.z = z;
		chunks.put(key, value);
	}
	
	private void executeMap() {
		int delay = 10;
		int key = 0;
		while (key < chunks.size()) {
			scheduleGeneration(key, delay);
			key++;
			delay++;
		}
	}
	
	private void scheduleGeneration(final int key, int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ThePlugin"), new Runnable() {
			public void run() {
				generateChunk(key);
			}
        }, delay);
	}
	
	private void generateChunk(int key) {
		int x = chunks.get(key).x;
		int z = chunks.get(key).z;
    	int nearbyX;
    	int nearbyZ;
    	
    	status(key);
		
		if (world.isChunkLoaded(x, z)) {
			return;
		}
		if (world.loadChunk(x, z, false)) {
			return;
		}
		
		nearbyX = x - 2;
    	while (nearbyX <= x + 2) {
    		nearbyZ = z - 2;
    		while (nearbyZ <= z + 2) {
    			world.loadChunk(nearbyX, nearbyZ, false);
    			nearbyZ++;
    		}
    		nearbyX++;
    	}
    	
		world.loadChunk(x, z);
    	gen++;
    	tempGen++;
		world.unloadChunk(x, z);
		
		nearbyX = x - 2;
    	while (nearbyX <= x + 2) {
    		nearbyZ = z - 2;
    		while (nearbyZ <= z + 2) {
    			world.unloadChunk(nearbyX, nearbyZ);
    			nearbyZ++;
    		}
    		nearbyX++;
		}
	}
	
	private void status(int key) {
    	int printEvery = 1200;
		if (key % printEvery == 0) {
			if (key == 0) {
				time = System.currentTimeMillis();
				info("Genererer " + chunks.size() + " chunks for [" + world.getName() + "]");
				info("Antatt ferdig om " + chunks.size() / 20 / 60 + "min");
			}
			else {
				world.save();
				String percent = String.format("%.2f", (key * 100.0) / chunks.size());
				time = System.currentTimeMillis() - time;
				info("[" + world.getName() + "] [" + percent + "%] Generert " + tempGen + "/" + printEvery + " chunks (" + time / 1000 + "s)");
				tempGen = 0;
				time = System.currentTimeMillis();
			}
		}
		
    	if (key == chunks.size() - 1) {
			world.save();
			totTime = System.currentTimeMillis() - totTime;
			time = (Long) (totTime / 1000 / 60);
			info("[" + world.getName() + "] [100,00%] Generert " + gen + "/" + chunks.size() + " chunks (" + time + "min)");
    	}
	}
	
	private void info(String s) {
		s = "[ThePlugin] " + s;
		ThePlugin.log.info(s);
	}
}
