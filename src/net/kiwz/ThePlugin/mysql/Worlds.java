package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Worlds {
	
	public String world;
	public String coords;
	public String pitch;
	public int pvp;
	public int claimable;
	public int firespread;
	public int explosions;
	public int endermen;
	public int trample;
	public int monsters;
	public int animals;
	
	public HashMap<String, Worlds> getTableWorlds(Connection conn) {
		MySQLQuery query = new MySQLQuery();
		Worlds world = new Worlds();
		HashMap<String, Worlds> worlds = new HashMap<String, Worlds>();
		try {
			ResultSet res = query.query(conn, "SELECT * FROM worlds;");
			while (res.next()) {
				world.world = res.getString("World");
				
				worlds.put(world.world, world);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return worlds;
	}
	
	public void setTableWorlds(Connection conn, HashMap<String, Worlds> worlds) {
		MySQLQuery query = new MySQLQuery();
		List<Integer> oldKeys = new ArrayList<Integer>();
		try {
			ResultSet res = query.query(conn, "SELECT * FROM worlds;");
			while (res.next()) {
				for (String key : worlds.keySet()) {
					String world = worlds.get(key).world;
					
					if (world.equals(res.getString("World"))) {
						query.update(conn, "UPDATE worlds SET Coords='" + homeCoords + "', Pitch='" + homePitch +
								"' WHERE Player LIKE '" + homePlayer + "' AND World LIKE '" + homeWorld + "';");
					}
				}
			}
			for (int key : oldKeys) {
				worlds.remove(key);
			}
			for (String key : worlds.keySet()) {
				String world = worlds.get(key).world;
				
				query.update(conn, "INSERT INTO worlds (World, World, Coords, Pitch) "
						+ "VALUES ('" + homePlayer + "', '" + homeWorld + "', '" + homeCoords + "', '" + homePitch + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
