package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;

public class Homes {
	
	public String player;
	public String world;
	public String coords;
	public String pitch;
	
	public HashMap<String, Homes> getTableHomes(Connection conn) {
		HashMap<String, Homes> homes = new HashMap<String, Homes>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM homes;");
			while (res.next()) {
				Homes home = new Homes();
				home.player = res.getString("Player");
				home.world = res.getString("World");
				home.coords = res.getString("Coords");
				home.pitch = res.getString("Pitch");
				
				homes.put(res.getString("Player") + res.getString("World"), home);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return homes;
	}
	
	public void setTableHomes(Connection conn, HashMap<String, Homes> homes) {
		for (String key : homes.keySet()) {
			String homePlayer = homes.get(key).player;
			String homeWorld = homes.get(key).world;
			String homeCoords = homes.get(key).coords;
			String homePitch = homes.get(key).pitch;
			String queryString = "INSERT INTO homes (Player, World, Coords, Pitch) "
					+ "VALUES ('" + homePlayer + "', '" + homeWorld + "', '" + homeCoords + "', '" + homePitch + "') "
					+ "ON DUPLICATE KEY UPDATE Coords='" + homeCoords + "', Pitch='" + homePitch + "';";
			try {
				conn.createStatement().executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (String key : ThePlugin.remHomes) {
			String[] string = key.split(" ");
			String playerName = string[0];
			String worldName = string[1];
			try {
				conn.createStatement().executeUpdate("DELETE FROM homes WHERE Player='" + playerName + "' AND World='" + worldName + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ThePlugin.remHomes.clear();
	}
}
