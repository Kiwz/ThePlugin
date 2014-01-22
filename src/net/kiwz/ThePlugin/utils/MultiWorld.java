package net.kiwz.ThePlugin.utils;

import java.io.File;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class MultiWorld {
	/*
	public static void handleWorlds() {
		for (World world : Bukkit.getServer().getWorlds()) {
			saveWorld(world);
		}
		for (MyWorld myWorld : MyWorld.getWorlds()) {
			loadMyWorld(myWorld);
		}
	}
	
	private static void saveWorld(World world) {
		MyWorld myWorld = new MyWorld(world);
		if (!myWorld.save()) {
			myWorld = MyWorld.getWorld(world);
			myWorld.setEnv(world.getEnvironment());
			myWorld.setType(world.getWorldType());
			myWorld.setSeed(world.getSeed());
			myWorld.setSpawn(world.getSpawnLocation());
			myWorld.setChanged(true);
		}
	}*/
	
	public static void loadMyWorld(MyWorld myWorld) {
		World world = createWorld(myWorld);
		if (myWorld.getSpawn() == null) {
			if (myWorld.getSpawnCoords() != null && myWorld.getSpawnDirection() != null) {
				myWorld.setSpawn(Util.parseLocation(world, myWorld.getSpawnCoords(), myWorld.getSpawnDirection()));
			} else {
				myWorld.setSpawn(world.getSpawnLocation());
			}
		}
		
		for (Place place : Place.getPlaces()) {
			if (!place.isRemoved() && !place.isLoaded() && place.getWorldName().equals(world.getName())) {
				place.setLoaded(true);
				place.getCenter().setWorld(world);
				place.getSpawn().setWorld(world);
			}
		}
	}
	
	private static World createWorld(MyWorld myWorld) {
		World world = Bukkit.getServer().getWorld(myWorld.getName());
		if (world != null) {
			return world;
		} else {
			WorldCreator c = new WorldCreator(myWorld.getName());
			c.environment(myWorld.getEnv());
			c.type(myWorld.getType());
			c.seed(myWorld.getSeed());
			world = c.createWorld();
			return world;
		}
	}
	
	public static void setWorldOptions(MyWorld myWorld) {
		World world = Bukkit.getServer().getWorld(myWorld.getName());
		if (world != null) {
			world.setSpawnLocation(myWorld.getSpawn().getBlockX(), myWorld.getSpawn().getBlockY(), myWorld.getSpawn().getBlockZ());
			world.setKeepSpawnInMemory(myWorld.getKeepSpawn());
			world.setPVP(myWorld.getPvp());
			world.setSpawnFlags(myWorld.getMonsters(), myWorld.getAnimals());
			if (myWorld.getMonsterGrief()) world.setGameRuleValue("mobGriefing", "true");
			else world.setGameRuleValue("mobGriefing", "false");
			if (myWorld.getFireSpread()) world.setGameRuleValue("doFireTick", "true");
			else world.setGameRuleValue("doFireTick", "false");
		}
	}
	
	public static void unloadWorld(MyWorld myWorld) {
		Server server = Bukkit.getServer();
		World world = myWorld.getWorld();
		FillWorld fillWorld = FillWorld.getFillWorld(myWorld);
		if (fillWorld != null) fillWorld.cancelTask();
		myWorld.setLoaded(false);
		myWorld.setRemoved(true);
		
		for (Player player : world.getPlayers()) {
			player.teleport(MyWorld.getWorld(server.getWorlds().get(0)).getSpawn());
			player.sendMessage(Color.WARNING + "Verdenen du var i ble slettet, du er nå i hoved-spawnen");
		}
		
		for (Place place : Place.getPlaces()) {
			if (place.getSpawn().getWorld() == world) {
				place.setLoaded(false);
			}
		}
		
		for (Home home : Home.getHomes()) {
			if (home.getLocation().getWorld() == world) {
				home.setLoaded(false);
				home.setRemoved(true);
			}
		}

		String name = world.getName();
		server.unloadWorld(world, true);
		moveWorldFolder(name);
	}
	
	private static void moveWorldFolder(String worldName) {
		File oldFile = new File(Bukkit.getServer().getWorldContainer().getPath() + File.separatorChar + worldName);
		String to = ThePlugin.getPlugin().getDataFolder().getPath();
		to += File.separatorChar + "deleted_worlds" + File.separatorChar;
		new File(to).mkdirs();
		to += worldName + "_(" + System.currentTimeMillis() / 1000 + ")";
		oldFile.renameTo(new File(to));
	}
}
