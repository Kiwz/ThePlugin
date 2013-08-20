package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class HandleWorlds {
	Worlds world = new Worlds();
	private HashMap<String, Worlds> worlds = ThePlugin.getWorlds;
	
	public Location getSpawn(String worldName) {
		String[] stringCoords = worlds.get(worldName).coords.split(" ");
		String[] stringPitch = worlds.get(worldName).pitch.split(" ");
		double x = Double.parseDouble(stringCoords[0]);
		double y = Double.parseDouble(stringCoords[1]);
		double z = Double.parseDouble(stringCoords[2]);
		float pitch = Float.parseFloat(stringPitch[0]);
		float yaw = Float.parseFloat(stringPitch[1]);
		Location loc = new Location(Bukkit.getServer().getWorld(worldName), x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		return loc;
	}
	
	public void addWorld(World newWorld) {
		String coords = newWorld.getSpawnLocation().getX() + " " + newWorld.getSpawnLocation().getY() + " " + newWorld.getSpawnLocation().getZ();
		String pitch = newWorld.getSpawnLocation().getPitch() + " " + newWorld.getSpawnLocation().getYaw();
		int pvp;
		int monsters;
		int animals;
		
		if (newWorld.getPVP()) pvp = 1;
		else pvp = 0;
		if (newWorld.getAllowMonsters()) monsters = 1;
		else monsters = 0;
		if (newWorld.getAllowAnimals()) animals = 1;
		else animals = 0;
		
		world.world = newWorld.getName();
		world.coords = coords;
		world.pitch = pitch;
		world.pvp = pvp;
		world.claimable = 0;
		world.firespread = 0;
		world.explosions = 0;
		world.endermen = 0;
		world.trample = 0;
		world.monsters = monsters;
		world.animals = animals;
		worlds.put(newWorld.getName(), world);
	}
	
	public void setSpawn(String worldName, Double x, Double y, Double z, Float pitch, Float yaw) {
		String stringCoords = x + " " + y + " " + z;
		String stringPitch = pitch + " " + yaw;
		world.world = worlds.get(worldName).world;
		world.coords = stringCoords;
		world.pitch = stringPitch;
		world.pvp = worlds.get(worldName).pvp;
		world.claimable = worlds.get(worldName).claimable;
		world.firespread = worlds.get(worldName).firespread;
		world.explosions = worlds.get(worldName).explosions;
		world.endermen = worlds.get(worldName).endermen;
		world.trample = worlds.get(worldName).trample;
		world.monsters = worlds.get(worldName).monsters;
		world.animals = worlds.get(worldName).animals;
		worlds.put(worldName, world);
	}
}
