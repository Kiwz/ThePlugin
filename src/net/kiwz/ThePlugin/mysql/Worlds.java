package net.kiwz.ThePlugin.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Worlds {

	public int id;
	public int time;
	public String name;
	public String owner;
	public String members;
	public int x;
	public int z;
	public int size;
	public String spawnCoords;
	public String spawnPitch;
	public int pvp;
	public int monsters;
	public int animals;
	
	public void place(int id) {
		try {
			ResultSet res = new MySQLQuery().query("SELECT * FROM places WHERE PlaceID LIKE '" + id + "';");
			res.next();
			this.id = res.getInt("PlaceID");
			this.time = res.getInt("Time");
			this.name = res.getString("Name");
			this.owner = res.getString("Owner");
			this.members = res.getString("Members");
			this.x = res.getInt("X");
			this.z = res.getInt("Z");
			this.size = res.getInt("Size");
			this.spawnCoords = res.getString("SpawnCoords");
			this.spawnPitch = res.getString("SpawnPitch");
			this.pvp = res.getInt("PvP");
			this.monsters = res.getInt("Monsters");
			this.animals = res.getInt("Animals");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, Worlds> tablePlaces() {
		Worlds place = new Worlds();
		HashMap<Integer, Worlds> places = new HashMap<Integer, Worlds>();
		try {
			ResultSet res = new MySQLQuery().query("SELECT * FROM places;");
			while (res.next()) {
				int id = res.getInt("PlaceID");
				place.place(id);
				places.put(id, place);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return places;
	}
}
