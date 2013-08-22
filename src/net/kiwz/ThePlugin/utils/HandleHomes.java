package net.kiwz.ThePlugin.utils;

import java.util.HashMap;
import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Places;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HandleHomes {
	
	public String addPlace(String name, Player player, String radius) {
		String playerName = player.getName();
		World world = player.getWorld();
		String worldName = world.getName();
		
		Location loc = player.getLocation();
		String spawnCoords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
		String spawnPitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
		
		HashMap<Integer, Places> places = ThePlugin.getPlaces;
		
		int id = 1;
		while (places.containsKey(id)) {
			id++;
		}
		Places place = new Places();
		place.id = id;
		place.time = (int) (System.currentTimeMillis() / 1000);
		place.name = name;
		place.owner = playerName;
		place.members = "";
		place.members = worldName;
		place.x = (int) loc.getX();
		place.z = (int) loc.getZ();
		place.size = Integer.parseInt(radius);
		place.spawnCoords = spawnCoords;
		place.spawnPitch = spawnPitch;
		place.pvp = 0;
		place.monsters = 0;
		place.animals = 1;
		places.put(id, place);
		return ""+id;
	}
}
