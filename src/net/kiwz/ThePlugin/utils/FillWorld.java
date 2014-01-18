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
			ThePlugin.getPlugin().getLogger().info("(" + myWorld.getName() + ") Ferdig � generere " + generated + "/" + totChunks + " chunks");
		}
	}
}

	
	
	/**
	 * TODO Denne m� skrives p� en bedre m�te!
	 */
	/*
	public static HashMap<String, Integer> tasks = new HashMap<String, Integer>();
	public static HashMap<String, FillWorld> save = new HashMap<String, FillWorld>();
	private int x;
	private int z;
	private World world;
	private HashMap<Integer, FillWorld> chunks = new HashMap<Integer, FillWorld>();
	private String speedString;
	private int key1 = 0;
	private int speed = 1;
	private Long totTime = System.currentTimeMillis();
	private Long time = System.currentTimeMillis();
	private int gen = 0;
	private int tempGen = 0;
	
	public void cancelGeneration(CommandSender sender, World world) {
		String worldName = world.getName();
		
		if (tasks.get(worldName) == null) {
			sender.sendMessage(Color.WARNING + "Ingen p�g�ende generering for " + worldName);
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(tasks.get(worldName));
		tasks.remove(worldName);
		save.remove(worldName);
		sender.sendMessage(Color.WARNING + "Generering avbrutt for " + worldName);
	}
	
	public void generateChunks(CommandSender sender, World world, String speedString) {
		this.world = world;
		this.speedString = speedString;
		
		if (tasks.get(world.getName()) != null) {
			sender.sendMessage(Color.WARNING + "Jobber allerede med � generere " + world.getName());
			return;
		}
		
		int d = MyWorld.getWorld(world).getBorder() * 2;
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
        
		setSpeed();
        tasks.put(world.getName(), scheduleGenerations());
        if (sender instanceof Player) {
			sender.sendMessage(Color.INFO + "Genererer " + chunks.size() + " chunks for [" + world.getName() + "]");
			long eta = Math.round(chunks.size() / 40.0 / 60 * speed);
			sender.sendMessage(Color.INFO + "Antatt ferdig om " + eta + "min (forutsatt TPS=20)");
        }
    }
	
	private void setSpeed() {
		int speed = 1;
		try {
			speed = Integer.parseInt(speedString);
		}
		catch (NumberFormatException e) {
		}
		if (speed >= 3) {
			this.speed = 1;
		}
		else if (speed == 2) {
			this.speed = 2;
		}
		else if (speed <= 1) {
			this.speed = 4;
		}
	}
	
	private void buildMap(int key, int x, int z) {
		FillWorld value = new FillWorld();
		value.x = x;
		value.z = z;
		chunks.put(key, value);
	}
	
	private int scheduleGenerations1() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("ThePlugin"), new Runnable() {
			public void run() {
				FillWorld value = new FillWorld();
				value.speedString = speedString;
				value.key = key;
				save.put(world.getName(), value);
				generateChunk(key);
				key++;
				generateChunk(key);
				key++;
			}
        }, 1, speed);
	}
	
	private void generateChunk1(int key) {
    	status(key);
		if (chunks.get(key) == null) {
			if (tasks.get(world.getName()) == null) {
				return;
			}
			Bukkit.getServer().getScheduler().cancelTask(tasks.get(world.getName()));
			tasks.remove(world.getName());
			save.remove(world.getName());
			return;
		}
		
		int x = chunks.get(key).x;
		int z = chunks.get(key).z;
    	int nearbyX;
    	int nearbyZ;
		
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
				long eta = Math.round(chunks.size() / 40.0 / 60 * speed);
				info("Antatt ferdig om " + eta + "min (forutsatt TPS=20)");
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
		ThePlugin.getPlugin().getLogger().info(s);
	}
	
	public void continueFromSave() {
		for (World world : Bukkit.getServer().getWorlds()) {
			try {
				File file = getFile(world.getName());
				Scanner sc = new Scanner(file);
				String speedString = sc.nextLine();
				String keyString = sc.nextLine();
				sc.close();
				try {
					int key = Integer.parseInt(keyString);
					this.key = key - 2;
				}
				catch (NumberFormatException e) {
				}
				info("Fortsetter med generering av [" + world.getName() + "]");
				generateChunks(Bukkit.getServer().getConsoleSender(), world, speedString);
				file.delete();
			}
			catch (IOException e) {
			}
		}
	}
	
	public void cancelSave() {
		for (String key : save.keySet()) {
			try {
				File file = getFile(key);
				if (file.createNewFile()) {
					PrintWriter writer = new PrintWriter(file);
					writer.println(save.get(key).speedString);
					writer.println(save.get(key).key);
					writer.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File getFile(String worldName) {
		String fileName = ThePlugin.getPlugin().getDataFolder().getPath();
		fileName = fileName + File.separatorChar;
		fileName = fileName + worldName + "-chunk.txt";
		File file = new File(fileName);
		return file;
	}
}
*/