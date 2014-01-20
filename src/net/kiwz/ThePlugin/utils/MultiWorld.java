package net.kiwz.ThePlugin.utils;

import java.io.File;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class MultiWorld {

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
		}
	}
	
	public static void loadMyWorld(MyWorld myWorld) {
		World world = createWorld(myWorld);
		if (myWorld.getSpawn() == null) {
			if (myWorld.getSpawnCoords() != null && myWorld.getSpawnDirection() != null) {
				myWorld.setSpawn(Util.parseLocation(world, myWorld.getSpawnCoords(), myWorld.getSpawnDirection()));
			} else {
				myWorld.setSpawn(world.getSpawnLocation());
			}
		}
		
		for (Place place : Place.getUnloadedPlaces()) {
			if (place.getSpawn().getWorld().getName().equals(world.getName())) {
				place.load();
				place = Place.getPlace(place.getId());
				place.getCenter().setWorld(world);
				place.getSpawn().setWorld(world);
			}
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
	
	public static boolean unloadWorld(World world) {
		Server server = Bukkit.getServer();
		String name = world.getName();
		for (Player player : world.getPlayers()) {
			player.teleport(MyWorld.getWorld(server.getWorlds().get(0)).getSpawn());
			player.sendMessage(Color.WARNING + "Verdenen du var i ble slettet, du er nå i hoved-spawnen");
		}
		
		for (Place place : Place.getPlaces()) {
			if (place.getSpawn().getWorld() == world) {
				place.unload();
			}
		}
		
		if (!server.unloadWorld(world, true)) {
			for (Place place : Place.getUnloadedPlaces()) {
				if (place.getSpawn().getWorld().getName().equals(world.getName())) {
					place.load();
					place = Place.getPlace(place.getId());
					place.getCenter().setWorld(world);
					place.getSpawn().setWorld(world);
				}
			}
			return false;
		}
		
		for (Home home : Home.getHomes()) {
			if (home.getLocation().getWorld().getName().equals(name)) home.delete();
		}
		moveWorldFolder(name);
		return true;
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
