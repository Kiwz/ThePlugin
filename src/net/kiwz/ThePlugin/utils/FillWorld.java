package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

public class FillWorld {
	private static HashMap<MyWorld, FillWorld> fillWorlds = new HashMap<MyWorld, FillWorld>();
	
	private BukkitTask task;
	private int key;
	private int generated;
	private MyWorld myWorld;
	private HashMap<Integer, Integer[]> chunkMap;
	
	
	
	public FillWorld(MyWorld myWorld) {
		this.myWorld = myWorld;
		this.key = myWorld.getFill();
	}
	
	public static FillWorld getFillWorld(MyWorld myWorld) {
		return fillWorlds.get(myWorld);
	}
	
	public static void restoreFill() {
		for (MyWorld myWorld : MyWorld.getWorlds()) {
			if (myWorld.getFill() != 0) {
				ThePlugin.getPlugin().getLogger().info("(" + myWorld.getName() + ") fortsetter med generering av chunks");
				new FillWorld(myWorld).start();
			}
		}
	}
	
	public static void pauseFill() {
		for (MyWorld myWorld : fillWorlds.keySet()) {
			FillWorld fillWorld = getFillWorld(myWorld);
			fillWorld.cancelTask();
			myWorld.setFill(fillWorld.getKey());
		}
	}
	
	public void cancelTask() {
		task.cancel();
		fillWorlds.remove(myWorld);
		myWorld.setFill(0);
	}
	
	public int getKey() {
		return this.key;
	}
	
	public void start() {
		chunkMap = new HashMap<Integer, Integer[]>();
		int d = myWorld.getBorder() * 2;
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
    		Integer[] xz = new Integer[2];
            xz[0] = x;
            xz[1] = z;
            chunkMap.put(i, xz);
            x += dx;
            z += dz;
        }
        
        this.task = Bukkit.getServer().getScheduler().runTaskTimer(ThePlugin.getPlugin(), new Runnable() {
			public void run() {
				generateChunk();
			}
        }, 40, 1);
	}
	
	private void generateChunk() {
		World world = myWorld.getBukkitWorld();
		status(world);
		if (chunkMap.get(key) == null) {
			task.cancel();
			myWorld.setFill(0);
			fillWorlds.remove(myWorld);
		} else {
			int x = chunkMap.get(key)[0];
			int z = chunkMap.get(key)[1];
			
			if (world.isChunkLoaded(x, z) || world.loadChunk(x, z, false)) {
				key++;
				if (key % 10 != 0) generateChunk();
				else fillWorlds.put(myWorld, this);
			} else {
		    	int nearbyX;
		    	int nearbyZ;
		    	
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
				world.unloadChunk(x, z);
				key++;
				generated++;
				fillWorlds.put(myWorld, this);
				
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
		}
	}
	
	private void status(World world) {
		int totChunks = chunkMap.size();
		if (key == 0) {
			ThePlugin.getPlugin().getLogger().info("(" + myWorld.getName() + ") Starter generering av " + totChunks + " chunks");
		} else if (key % 1200 == 0) {
			world.save();
			String percent = String.format("%.2f", (key * 100.0) / totChunks);
			ThePlugin.getPlugin().getLogger().info("(" + myWorld.getName() + ") " + percent + "% generert");
		}
		if (chunkMap.get(key) == null) {
			world.save();
			ThePlugin.getPlugin().getLogger().info("(" + myWorld.getName() + ") Ferdig å generere " + generated + "/" + totChunks + " chunks");
		}
	}
}
