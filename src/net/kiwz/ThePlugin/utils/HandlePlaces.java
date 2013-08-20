package net.kiwz.ThePlugin.utils;

import java.util.HashMap;
import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Places;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HandlePlaces {
	
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

		
		/*for (int key : places.keySet()) {
			if (places.get(key).name.equals(name)) {
				return "Dette navnet er opptatt";
			}
			id = places.get(key).id;
			int time = places.get(key).time;
			String name = places.get(key).name;
			String owner = places.get(key).owner;
			String members = places.get(key).members;
			int x = places.get(key).x;
			int z = places.get(key).z;
			int size = places.get(key).size;
			String spawnCoords = places.get(key).spawnCoords;
			String spawnPitch = places.get(key).spawnPitch;
			int pvp = places.get(key).pvp;
			int monsters = places.get(key).monsters;
			int animals = places.get(key).animals;
			
			if (homePlayer.equals(playerName) && homeWorld.equals(worldName)) {
				homes.get(key).coords = coords;
				homes.get(key).pitch = pitch;
				return "";
			}
		}*/
		/*
		Location loc = player.getLocation();
		int time = (int) (System.currentTimeMillis() / 1000);
		String owner = player.getName();
		int x = (int) loc.getX();
		int z = (int) loc.getZ();
		int size = Integer.parseInt(radius);
		String spawnX = Double.toString(loc.getX());
		String spawnY = Double.toString(loc.getY());
		String spawnZ = Double.toString(loc.getZ());
		String spawnCoords = spawnX + " " + spawnY + " " + spawnZ;
		String spawnP = Double.toString(loc.getPitch());
		String spawnYa = Double.toString(loc.getYaw());
		String spawnPitch = spawnP + " " + spawnYa;
		int pvp = 0;
		int monsters = 0;
		int animals = 1;
		String queryString = "INSERT INTO places (Time, Name, Owner, X, Z, Size, SpawnCoords, SpawnPitch, PvP, Monsters, Animals) "
				+ "VALUES ('" + time + "', '" + name + "', '" + owner + "', '" + x + "', '" + z + "', '" + size + "', '"
				+ spawnCoords + "', '" + spawnPitch + "', '" + pvp + "', '" + monsters + "', '" + animals + "');";
		
		try {
			new MySQLQuery().update(queryString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;*/