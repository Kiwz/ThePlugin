package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;

public class Worlds {
	
	public String world;
	public String coords;
	public String pitch;
	public int border;
	public int claimable;
	public int fireSpread;
	public int explosions;
	public int monsterGrief;
	public int trample;
	public int pvp;
	public int monsters;
	public int animals;
	public int keepSpawn;
	public String environment;
	public String type;
	public String seed;
	
	public HashMap<String, Worlds> getTableWorlds(Connection conn) {
		HashMap<String, Worlds> worlds = new HashMap<String, Worlds>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM worlds;");
			while (res.next()) {
				Worlds world = new Worlds();
				world.world = res.getString("World");
				world.coords = res.getString("Coords");
				world.pitch = res.getString("Pitch");
				world.border = res.getInt("Border");
				world.claimable = res.getInt("Claimable");
				world.fireSpread = res.getInt("FireSpread");
				world.explosions = res.getInt("Explosions");
				world.monsterGrief = res.getInt("MonsterGrief");
				world.trample = res.getInt("Trample");
				world.pvp = res.getInt("PvP");
				world.monsters = res.getInt("Monsters");
				world.animals = res.getInt("Animals");
				world.keepSpawn = res.getInt("KeepSpawn");
				world.environment = res.getString("Environment");
				world.type = res.getString("Type");
				world.seed = res.getString("Seed");
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
			int border = worlds.get(key).border;
			int claimable = worlds.get(key).claimable;
			int fireSpread = worlds.get(key).fireSpread;
			int explosions = worlds.get(key).explosions;
			int monsterGrief = worlds.get(key).monsterGrief;
			int trample = worlds.get(key).trample;
			int pvp = worlds.get(key).pvp;
			int monsters = worlds.get(key).monsters;
			int animals = worlds.get(key).animals;
			int keepSpawn = worlds.get(key).keepSpawn;
			String environment = worlds.get(key).environment;
			String type = worlds.get(key).type;
			String seed = worlds.get(key).seed;
			String queryString = "INSERT INTO worlds (World, Coords, Pitch, Border, Claimable, FireSpread, Explosions, "
					+ "MonsterGrief, Trample, PvP, Monsters, Animals, KeepSpawn, Environment, Type, Seed) "
					+ "VALUES ('" + world + "', '" + coords + "', '" + pitch + "', '" + border + "', '" + claimable + "', '"
					+ fireSpread + "', '" + explosions + "', '" + monsterGrief + "', '" + trample + "', '" + pvp + "', '"
					+ monsters + "', '" + animals + "', '" + keepSpawn + "', '" + environment + "', '" + type + "', '" + seed + "') "
					+ "ON DUPLICATE KEY UPDATE Coords='" + coords + "', Pitch='" + pitch + "', Border='" + border + "', "
					+ "Claimable='" + claimable + "', FireSpread='" + fireSpread + "', Explosions='" + explosions + "', "
					+ "MonsterGrief='" + monsterGrief + "', Trample='" + trample + "', PvP='" + pvp + "', Monsters='" + monsters + "', "
					+ "Animals='" + animals + "', KeepSpawn='" + keepSpawn + "', Environment='" + environment + "', "
					+ "Type='" + type + "', Seed='" + seed + "';";
			try {
				conn.createStatement().executeUpdate(queryString);
				for (String worldName : ThePlugin.remWorlds) {
					conn.createStatement().executeUpdate("DELETE FROM worlds WHERE World='" + worldName + "';");
				}
				ThePlugin.remWorlds.clear();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
