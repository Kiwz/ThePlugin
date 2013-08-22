package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Places;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HandlePlaces {
	private ChatColor gold = ChatColor.GOLD;
	private ChatColor red = ChatColor.RED;
	private HashMap<Integer, Places> places = ThePlugin.getPlaces;
	
	public int getID(String name) {
		int id = 0;
		for (int key : places.keySet()) {
			if (places.get(key).name.equalsIgnoreCase(name)) {
				id = places.get(key).id;
			}
		}
		return id;
	}
	public String addPlace(String name, Player player, String radius) {
		String playerName = player.getName();
		World world = player.getWorld();
		String worldName = world.getName();
		
		Location loc = player.getLocation();
		String spawnCoords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
		String spawnPitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
		
		for (int key : places.keySet()) {
			if (places.get(key).name.equalsIgnoreCase(name)) {
				return red + "Dette navnet finnes fra før";
			}
		}
		
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
		place.world = worldName;
		place.x = (int) loc.getX();
		place.z = (int) loc.getZ();
		place.size = Integer.parseInt(radius);
		place.spawnCoords = spawnCoords;
		place.spawnPitch = spawnPitch;
		place.pvp = 0;
		place.monsters = 0;
		place.animals = 1;
		places.put(id, place);
		int size = (Integer.parseInt(radius) * 2) + 1;
		return gold + "Din nye plass heter \"" + name + "\" og er " + size + " x " + size + " blokker stor";
	}
	
	public String setName(Player player, int id, String name) {
		if (places.get(id).owner.equals(player.getName())) {
			Places place = new Places();
			place.name = name;
			place.getRemainingValues(id, places);
			places.put(id, place);
			return gold + "Du har byttet navn på plassen din til: " + name;
		}
		else {
			return red + "Dette navnet er allerede brukt";
		}
	}
	
	public String setPvP(Player player, int id) {
		if (places.get(id).owner.equals(player.getName())) {
			String returnString;
			Places place = new Places();
			if (places.get(id).pvp == 0) {
				place.pvp = 1;
				returnString = gold + "PvP er AKTIVERT";
			}
			else {
				place.pvp = 0;
				returnString = gold + "PvP er DEAKTIVERT";
			}
			place.getRemainingValues(id, places);
			places.put(id, place);
			return returnString;
		}
		else {
			return red + "Du eier ikke denne plassen";
		}
	}
}
