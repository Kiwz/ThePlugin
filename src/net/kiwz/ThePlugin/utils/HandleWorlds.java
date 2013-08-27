package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HandleWorlds {
	Worlds world = new Worlds();
	private HashMap<String, Worlds> worlds = ThePlugin.getWorlds;
	
	/**
	 * 
	 * @param player as Object
	 * @param worldName as String
	 * @return spawn location for given worldName as Location
	 */
	public Location getSpawn(Player player, String worldName) {
		World world = null;
		Location loc = null;
		
		for (World worlds : Bukkit.getServer().getWorlds()) {
			if (worlds.getName().toUpperCase().contains(worldName.toUpperCase())) {
				world = Bukkit.getServer().getWorld(worlds.getName());
				break;
			}
		}
		
		for (World worlds : Bukkit.getServer().getWorlds()) {
			if (worlds.getName().equalsIgnoreCase(worldName)) {
				world = Bukkit.getServer().getWorld(worlds.getName());
				break;
			}
		}
		
		if (world == null) {
			player.sendMessage(ThePlugin.c2 + worldName + " finnes ikke");
			return loc;
		}
		
		if (worlds.get(world.getName()) == null) {
			return world.getSpawnLocation();
		}
		
		else {
			String[] stringCoords = worlds.get(world.getName()).coords.split(" ");
			String[] stringPitch = worlds.get(world.getName()).pitch.split(" ");
			double x = Double.parseDouble(stringCoords[0]);
			double y = Double.parseDouble(stringCoords[1]);
			double z = Double.parseDouble(stringCoords[2]);
			float pitch = Float.parseFloat(stringPitch[0]);
			float yaw = Float.parseFloat(stringPitch[1]);
			loc = new Location(world, x, y, z);
			loc.setPitch(pitch);
			loc.setYaw(yaw);
			return loc;
		}
	}
	
	/**
	 * 
	 * @param newWorld
	 * 
	 * <p>Adds a new world with given newWorld
	 */
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
		worlds.put(world.world, world);
	}
	
	/**
	 * 
	 * @param loc as Location where the new spawn will be
	 * @return String describing the result
	 */
	public String setSpawn(Location loc) {
		String worldName = loc.getWorld().getName();
		Double x = loc.getX();
		Double y = loc.getY();
		Double z = loc.getZ();
		Float pitch = loc.getPitch();
		Float yaw = loc.getYaw();
		String stringCoords = x + " " + y + " " + z;
		String stringPitch = pitch + " " + yaw;
		worlds.get(worldName).coords = stringCoords;
		worlds.get(worldName).pitch = stringPitch;
		return ThePlugin.c1 + "Du har satt spawnen her";
	}
}
