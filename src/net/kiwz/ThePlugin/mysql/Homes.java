package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Homes {
	
	public String player;
	public String world;
	public String coords;
	public String pitch;
	
	public HashMap<String, Homes> getTableHomes(Connection conn) {
		Homes home = new Homes();
		HashMap<String, Homes> homes = new HashMap<String, Homes>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM homes;");
			while (res.next()) {
				home.player = res.getString("Player");
				home.world = res.getString("World");
				home.coords = res.getString("Coords");
				home.pitch = res.getString("Pitch");
				
				homes.put(home.player, home);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return homes;
	}
	
	public void setTableHomes(Connection conn, HashMap<String, Homes> homes) {
		List<String> oldKeys = new ArrayList<String>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM homes;");
			while (res.next()) {
				for (String key : homes.keySet()) {
					String homePlayer = homes.get(key).player;
					String homeWorld = homes.get(key).world;
					String homeCoords = homes.get(key).coords;
					String homePitch = homes.get(key).pitch;
					
					if (homePlayer.equals(res.getString("Player")) && homeWorld.equals(res.getString("World"))) {
						conn.createStatement().executeUpdate("UPDATE homes SET Coords='" + homeCoords + "', Pitch='" + homePitch +
								"' WHERE Player LIKE '" + homePlayer + "' AND World LIKE '" + homeWorld + "';");
						oldKeys.add(key);
					}
				}
			}
			for (String key : oldKeys) {
				homes.remove(key);
			}
			for (String key : homes.keySet()) {
				String homePlayer = homes.get(key).player;
				String homeWorld = homes.get(key).world;
				String homeCoords = homes.get(key).coords;
				String homePitch = homes.get(key).pitch;
				
				conn.createStatement().executeUpdate("INSERT INTO homes (Player, World, Coords, Pitch) "
						+ "VALUES ('" + homePlayer + "', '" + homeWorld + "', '" + homeCoords + "', '" + homePitch + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
