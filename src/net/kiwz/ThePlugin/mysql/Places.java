package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Places {

	public int id;
	public int time;
	public String name;
	public String owner;
	public String members;
	public String world;
	public int x = 999999999;
	public int z = 999999999;
	public int size = 999999999;
	public String spawnCoords;
	public String spawnPitch;
	public int pvp = 2;
	public int monsters = 2;
	public int animals = 2;
	
	public void getRemainingValues(int id, HashMap<Integer, Places> places) {
		this.id = id;
		if (time == 0) time = places.get(id).time;
		if (name == null) name = places.get(id).name;
		if (owner == null) owner = places.get(id).owner;
		if (members == null) members = places.get(id).members;
		if (world == null) world = places.get(id).world;
		if (x == 999999999) x = places.get(id).x;
		if (z == 999999999) z = places.get(id).z;
		if (size == 999999999) size = places.get(id).size;
		if (spawnCoords == null) spawnCoords = places.get(id).spawnCoords;
		if (spawnPitch == null) spawnPitch = places.get(id).spawnPitch;
		if (pvp == 2) pvp = places.get(id).pvp;
		if (monsters == 2) monsters = places.get(id).monsters;
		if (animals == 2) animals = places.get(id).animals;
	}
	
	public HashMap<Integer, Places> getTablePlaces(Connection conn) {
		HashMap<Integer, Places> places = new HashMap<Integer, Places>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM places;");
			while (res.next()) {
				Places place = new Places();
				place.id = res.getInt("PlaceID");
				place.time = res.getInt("Time");
				place.name = res.getString("Name");
				place.owner = res.getString("Owner");
				place.members = res.getString("Members");
				place.world = res.getString("World");
				place.x = res.getInt("X");
				place.z = res.getInt("Z");
				place.size = res.getInt("Size");
				place.spawnCoords = res.getString("SpawnCoords");
				place.spawnPitch = res.getString("SpawnPitch");
				place.pvp = res.getInt("PvP");
				place.monsters = res.getInt("Monsters");
				place.animals = res.getInt("Animals");
				
				places.put(place.id, place);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return places;
	}
	
	public void setTablePlaces(Connection conn, HashMap<Integer, Places> places) {
		for (int key : places.keySet()) {
			int id = places.get(key).id;
			int time = places.get(key).time;
			String name = places.get(key).name;
			String owner = places.get(key).owner;
			String members = places.get(key).members;
			String world = places.get(key).world;
			int x = places.get(key).x;
			int z = places.get(key).z;
			int size = places.get(key).size;
			String spawnCoords = places.get(key).spawnCoords;
			String spawnPitch = places.get(key).spawnPitch;
			int pvp = places.get(key).pvp;
			int monsters = places.get(key).monsters;
			int animals = places.get(key).animals;
			String queryString = "INSERT INTO places (PlaceID, Time, Name, Owner, Members, World, "
					+ "X, Z, Size, SpawnCoords, SpawnPitch, PvP, Monsters, Animals) "
					+ "VALUES ('" + id + "', '" + time + "', '" + name + "', '" + owner + "', '"
					+ members + "', '" + world + "', '" + x + "', '" + z + "', '" + size + "', '"
					+ spawnCoords + "', '" + spawnPitch + "', '" + pvp + "', '" + monsters + "', '" + animals + "') "
					+ "ON DUPLICATE KEY UPDATE Time='" + time + "', Name='" + name + "', Owner='" + owner + "', "
					+ "Members='" + members + "', World='" + world + "', X='" + x + "', Z='" + z + "', "
					+ "Size='" + size + "', SpawnCoords='" + spawnCoords + "', SpawnPitch='" + spawnPitch + "', "
					+ "PvP='" + pvp + "', Monsters='" + monsters + "', Animals='" + animals + "';";
			try {
				conn.createStatement().executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
