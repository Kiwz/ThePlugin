package net.kiwz.ThePlugin.utils;

import java.sql.SQLException;

import net.kiwz.ThePlugin.mysql.MySQLQuery;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HandlePlaces {
	
	public boolean addPlace(String name, Player player, String radius) {
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
		return false;
	}
}
