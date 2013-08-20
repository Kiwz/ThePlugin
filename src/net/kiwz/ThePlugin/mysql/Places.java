package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Places {

	public int id;
	public int time;
	public String name;
	public String owner;
	public String members;
	public String world;
	public int x;
	public int z;
	public int size;
	public String spawnCoords;
	public String spawnPitch;
	public int pvp;
	public int monsters;
	public int animals;
	
	public HashMap<Integer, Places> getTablePlaces(Connection conn) {
		Places place = new Places();
		HashMap<Integer, Places> places = new HashMap<Integer, Places>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM places;");
			while (res.next()) {
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
		List<Integer> oldKeys = new ArrayList<Integer>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM places;");
			while (res.next()) {
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
					
					if (id == res.getInt("PlaceID")) {
						String queryString = ("UPDATE places SET Time='" + time + "', Name='" + name + "', Owner='" + owner +
								"', Members='" + members + "', World='" + world + "', X='" + x + "', Z='" + z +
								"', Size='" + size + "', SpawnCoords='" + spawnCoords + "', SpawnPitch='" + spawnPitch +
								"', PvP='" + pvp + "', Monsters='" + monsters + "', Animals='" + animals +
								"' WHERE PlaceID LIKE '" + id + "';");
						conn.createStatement().executeUpdate(queryString);
						oldKeys.add(key);
					}
				}
			}
			for (int key : oldKeys) {
				places.remove(key);
			}
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
						+ spawnCoords + "', '" + spawnPitch + "', '" + pvp + "', '" + monsters + "', '" + animals + "');";
				conn.createStatement().executeUpdate(queryString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
