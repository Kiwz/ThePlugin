package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class FillWorld {
	private HashMap<Integer, FillWorld> chunks = new HashMap<Integer, FillWorld>();
	private String world;
	private int x;
	private int z;
	
	public void generateChunks(CommandSender sender, String worldName, String radius) {
		if (Bukkit.getServer().getWorld(worldName) == null) {
			sender.sendMessage(ThePlugin.c2 + worldName + " finnes ikke");
			return;
		}

		int r = Integer.parseInt(radius);
		World world = Bukkit.getServer().getWorld(worldName);
    	int X = r;
    	int Z = r;
        int x = 0;
        int z = 0;
        int dx = 0;
        int dy = -1;
        int t = Math.max(X, Z);
        int maxI = t * t;
        
        for (int i = 0; i < maxI; i++) {
            if ((-X / 2 <= x) && (x <= X / 2) && (-Z / 2 <= z) && (z <= Z / 2)) {
            	buildMap(i, world.getName(), x, z);
            }
            if ((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1-z))) {
                t=dx;
                dx=-dy;
                dy=t;
            }   
            x += dx;
            z += dy;
        }
        executeMap();
    }
	
	private void buildMap(int key, String world, int x, int z) {
		FillWorld value = new FillWorld();
		value.world = world;
		value.x = x;
		value.z = z;
		chunks.put(key, value);
	}
	
	private void executeMap() {
		int delay = 20;
		int key = 0;
		while (key < chunks.size()) {
			scheduleGeneration(key, delay);
			key++;
			delay++;
		}
		int id = scheduleWorldSave(600);
		scheduleKillWorldSave(id, delay + 800);
	}
	
	private void scheduleGeneration(final int key, int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ThePlugin"), new Runnable() {
			public void run() {
				generateChunk(key);
			}
        }, delay);
	}
	
	private int scheduleWorldSave(int delay) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("ThePlugin"), new Runnable() {
			public void run() {
				for (World world : Bukkit.getServer().getWorlds()) {
					System.out.println("Saving world: " + world.getName());
					world.save();
				}
			}
        }, delay, delay);
	}
	
	private int scheduleKillWorldSave(final int id, int delay) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ThePlugin"), new Runnable() {
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
				System.out.println("World Saving is DONE!");
			}
        }, delay);
	}
	
	private void generateChunk(int key) {
		World world = Bukkit.getServer().getWorld(chunks.get(key).world);
		int x = chunks.get(key).x;
		int z = chunks.get(key).z;
		
    	int minX = x - 1;
    	while (minX <= x + 1) {
        	int minZ = z - 1;
    		while (minZ <= z + 1) {
    			if (world.loadChunk(minX, minZ, false)) {
					System.out.println("Safety Chunk " + minX + " " + minZ + " is loaded");
    			}
    			else {
					System.out.println("Safety Chunk " + minX + " " + minZ + " isnt generated yet");
    			}
    			minZ++;
    		}
    		minX++;
    	}
		
		if (!world.isChunkLoaded(x, z)) {
			if (!world.loadChunk(x, z, false)) {
				if (world.loadChunk(x, z, true)) {
					System.out.println("Chunk " + x + " " + z + " is generated");
					if (world.unloadChunk(x, z)) {
						System.out.println("Chunk " + x + " " + z + " is unloaded");
					}
					else {
						System.out.println("Chunk " + x + " " + z + " couldnt be unloaded");
					}
					
			    	minX = x - 1;
			    	while (minX <= x + 1) {
			        	int minZ = z - 1;
			    		while (minZ <= z + 1) {
			    			if (world.unloadChunk(minX, minZ)) {
								System.out.println("Safety Chunk " + minX + " " + minZ + " is unloaded");
			    			}
							else {
								System.out.println("Safety Chunk " + minX + " " + minZ + " couldnt be unloaded");
							}
			    			minZ++;
			    		}
			    		minX++;
			    	}
				}
				else {
					System.out.println("Chunk " + x + " " + z + " couldnt be generated");
				}
			}
			else {
				System.out.println("Chunk " + x + " " + z + " was allready generated");
			}
		}
		else {
			System.out.println("Chunk " + x + " " + z + " is allready loaded");
		}
	}
}
