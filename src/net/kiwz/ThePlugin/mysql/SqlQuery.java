package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.kiwz.ThePlugin.utils.Home;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;

public class SqlQuery {
	Connection conn;
	
	public SqlQuery(Connection conn) {
		this.conn = conn;
	}
	
	public void selectHomes() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM homes;");
			while (res.next()) {
				String uuid = res.getString("Player");
				String worldName = res.getString("World");
				String coords = res.getString("Coords");
				String direction = res.getString("Direction");
				
				Home home = new Home(uuid, worldName, coords, direction);
				home.save();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertHomes() {
		for (Home home : Home.getRemovedHomes()) {
			String uuid = home.getUUID();
			String worldName = home.getLocation().getWorld().getName();
			try {
				conn.createStatement().executeUpdate("DELETE FROM homes WHERE Player='" + uuid + "' AND World='" + worldName + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Home.clearRemovedHomes();
		
		for (Home home : Home.getHomes()) {
			String query = "INSERT INTO homes VALUES (?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Coords=values(Coords), "
					+ "Direction=values(Direction);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, home.getUUID());
				String[] loc = Util.convertLocation(home.getLocation());
				prep.setString(2, loc[0]);
				prep.setString(3, loc[1]);
				prep.setString(4, loc[2]);
				prep.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectPlayers() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM players;");
			while (res.next()) {
				String uuid = res.getString("ID");
				String name = res.getString("Player");
				String ip = res.getString("IP");
				long lastPlayed = res.getLong("LastPlayed");
				long timePlayed = res.getLong("TimePlayed");
				boolean muted = res.getBoolean("Mute");
				
				MyPlayer myPlayer = new MyPlayer(uuid, name, ip, lastPlayed, timePlayed, muted);
				myPlayer.save();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertPlayers() {
		for (MyPlayer myPlayer : MyPlayer.getPlayers()) {
			String query = "INSERT INTO players VALUES (?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Player=values(Player), "
					+ "IP=values(IP), "
					+ "LastPlayed=values(LastPlayed), "
					+ "TimePlayed=values(TimePlayed), "
					+ "Mute=values(Mute);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, myPlayer.getUUID());
				prep.setString(2, myPlayer.getName());
				prep.setString(3, myPlayer.getIp());
				prep.setLong(4, myPlayer.getLastPlayed());
				prep.setLong(5, myPlayer.getTimePlayed());
				prep.setBoolean(6, myPlayer.isMuted());
				prep.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectPlaces() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM places;");
			while (res.next()) {
				int id = res.getInt("PlaceID");
				int time = res.getInt("Time");
				String name = res.getString("Name");
				String owner = res.getString("Owner");
				String members = res.getString("Members");
				String world = res.getString("World");
				int x = res.getInt("X");
				int z = res.getInt("Z");
				int radius = res.getInt("Radius");
				String spawnCoords = res.getString("SpawnCoords");
				String spawnDirection = res.getString("SpawnDirection");
				boolean priv = res.getBoolean("Priv");
				boolean pvp = res.getBoolean("PvP");
				boolean monsters = res.getBoolean("Monsters");
				boolean animals = res.getBoolean("Animals");
				String enter = res.getString("Enter");
				String leave = res.getString("Leave_");
				
				Place place = new Place(id, time, name, owner, members, world, x, z, radius,
						spawnCoords, spawnDirection, priv, pvp, monsters, animals, enter, leave);
				place.savePlace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertPlaces() {
		for (Place place : Place.getRemovedPlaces()) {
			try {
				conn.createStatement().executeUpdate("DELETE FROM places WHERE PlaceID='" + place.getId() + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Place.clearRemovedPlaces();
		
		for (Place place : Place.getPlaces()) {
			String query = "INSERT INTO places VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Time=values(Time), "
					+ "Name=values(Name), "
					+ "Owner=values(Owner), "
					+ "Members=values(Members), "
					+ "World=values(World), "
					+ "X=values(X), "
					+ "Z=values(Z), "
					+ "Radius=values(Radius), "
					+ "SpawnCoords=values(SpawnCoords), "
					+ "SpawnDirection=values(SpawnDirection), "
					+ "Priv=values(Priv), "
					+ "PvP=values(PvP), "
					+ "Monsters=values(Monsters), "
					+ "Animals=values(Animals), "
					+ "Enter=values(Enter), "
					+ "Leave_=values(Leave_);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setInt(1, place.getId());
				prep.setInt(2, place.getTime());
				prep.setString(3, place.getName());
				prep.setString(4, place.getOwner());
				String members = "";
				for (String member : place.getMembers()) {
					members = members + member + " ";
				}
				prep.setString(5, members.trim());
				prep.setString(6, place.getCenter().getWorld().getName());
				prep.setInt(7, place.getCenter().getBlockX());
				prep.setInt(8, place.getCenter().getBlockZ());
				prep.setInt(9, place.getRadius());
				String[] spawn = Util.convertLocation(place.getSpawn());
				prep.setString(10, spawn[1]);
				prep.setString(11, spawn[2]);
				prep.setBoolean(12, place.getPriv());
				prep.setBoolean(13, place.getPvP());
				prep.setBoolean(14, place.getMonsters());
				prep.setBoolean(15, place.getAnimals());
				prep.setString(16, place.getEnter());
				prep.setString(17, place.getLeave());
				prep.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectWorlds() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM worlds;");
			while (res.next()) {
				String name = res.getString("World");
				String environment = res.getString("Environment");
				String type = res.getString("Type");
				String seed = res.getString("Seed");
				String coords = res.getString("Coords");
				String direction = res.getString("Direction");
				boolean keepSpawn = res.getBoolean("KeepSpawn");
				boolean pvp = res.getBoolean("PvP");
				boolean monsters = res.getBoolean("Monsters");
				boolean animals = res.getBoolean("Animals");
				boolean monsterGrief = res.getBoolean("MonsterGrief");
				boolean fireSpread = res.getBoolean("FireSpread");
				boolean claimable = res.getBoolean("Claimable");
				boolean explosions = res.getBoolean("Explosions");
				boolean trample = res.getBoolean("Trample");
				int border = res.getInt("Border");
				
				MyWorld myWorld = new MyWorld(name, environment, type, seed, coords, direction, keepSpawn, pvp,
						monsters, animals, monsterGrief, fireSpread, claimable, explosions, trample, border);
				myWorld.save();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertWorlds() {
		for (MyWorld myWorld : MyWorld.getRemovedWorlds()) {
			try {
				conn.createStatement().executeUpdate("DELETE FROM worlds WHERE World='" + myWorld.getName() + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		MyWorld.clearRemovedWorlds();
		
		for (MyWorld myWorld : MyWorld.getWorlds()) {
			String query = "INSERT INTO worlds VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Environment=values(Environment), "
					+ "Type=values(Type), "
					+ "Seed=values(Seed), "
					+ "Coords=values(Coords), "
					+ "Direction=values(Direction), "
					+ "KeepSpawn=values(KeepSpawn), "
					+ "PvP=values(PvP), "
					+ "Monsters=values(Monsters), "
					+ "Animals=values(Animals), "
					+ "MonsterGrief=values(MonsterGrief), "
					+ "FireSpread=values(FireSpread), "
					+ "Claimable=values(Claimable), "
					+ "Explosions=values(Explosions), "
					+ "Trample=values(Trample), "
					+ "Border=values(Border);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, myWorld.getName());
				prep.setString(2, myWorld.getEnv().toString());
				prep.setString(3, myWorld.getType().toString());
				prep.setString(4, myWorld.getSeed() + "");
				String[] spawn = Util.convertLocation(myWorld.getSpawn());
				prep.setString(5, spawn[1]);
				prep.setString(6, spawn[2]);
				prep.setBoolean(7, myWorld.getKeepSpawn());
				prep.setBoolean(8, myWorld.getPvp());
				prep.setBoolean(9, myWorld.getMonsters());
				prep.setBoolean(10, myWorld.getAnimals());
				prep.setBoolean(11, myWorld.getMonsterGrief());
				prep.setBoolean(12, myWorld.getFireSpread());
				prep.setBoolean(13, myWorld.getClaimable());
				prep.setBoolean(14, myWorld.getExplosions());
				prep.setBoolean(15, myWorld.getTrample());
				prep.setInt(16, myWorld.getBorder());
				prep.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
