package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
		HashMap<String, Worlds> worlds = new HashMap<String, Worlds>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM worlds;");
			while (res.next()) {
				Worlds world = new Worlds();
				world.world = res.getString("World");
				world.coords = res.getString("Coords");
				world.pitch = res.getString("Pitch");
				world.pvp = res.getInt("PvP");
				world.claimable = res.getInt("Claimable");
				world.firespread = res.getInt("FireSpread");
				world.explosions = res.getInt("Explosions");
				world.endermen = res.getInt("Endermen");
				world.trample = res.getInt("Trample");
				world.monsters = res.getInt("Monsters");
				world.animals = res.getInt("Animals");
				
				worlds.put(world.world, world);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return worlds;
	}
	
	public void setTableWorlds(Connection conn, HashMap<String, Worlds> worlds) {
		for (String key : worlds.keySet()) {
			String world = worlds.get(key).world;
			String coords = worlds.get(key).coords;
			String pitch = worlds.get(key).pitch;
			int pvp = worlds.get(key).pvp;
			int claimable = worlds.get(key).claimable;
			int firespread = worlds.get(key).firespread;
			int explosions = worlds.get(key).explosions;
			int endermen = worlds.get(key).endermen;
			int trample = worlds.get(key).trample;
			int monsters = worlds.get(key).monsters;
			int animals = worlds.get(key).animals;
			String queryString = "INSERT INTO worlds (World, Coords, Pitch, PvP, Claimable, "
					+ "FireSpread, Explosions, Endermen, Trample, Monsters, Animals) "
					+ "VALUES ('" + world + "', '" + coords + "', '" + pitch + "', '" + pvp + "', '"
					+ claimable + "', '" + firespread + "', '" + explosions + "', '" + endermen + "', '"
					+ trample + "', '" + monsters + "', '" + animals + "') "
					+ "ON DUPLICATE KEY UPDATE Coords='" + coords + "', Pitch='" + pitch + "', "
					+ "PvP='" + pvp + "', Claimable='" + claimable + "', FireSpread='" + firespread + "', "
					+ "Explosions='" + explosions + "', Endermen='" + endermen + "', "
					+ "Trample='" + trample + "', Monsters='" + monsters + "', Animals='" + animals + "';";
			try {
				conn.createStatement().executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
