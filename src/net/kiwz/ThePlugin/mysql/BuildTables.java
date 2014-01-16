package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildTables {
	
	private final static String homes = "CREATE TABLE IF NOT EXISTS homes "
			+ "(Player VARCHAR(255), "
			+ "World VARCHAR(255), "
			+ "Coords VARCHAR(255), "
			+ "Direction VARCHAR(255), "
			+ "PRIMARY KEY (Player, World));";
	
	private final static String places = "CREATE TABLE IF NOT EXISTS places "
			+ "(PlaceID INT, "
			+ "Time INT, "
			+ "Name VARCHAR(255), "
			+ "Owner VARCHAR(255), "
			+ "Members MEDIUMTEXT, "
			+ "World VARCHAR(255), "
			+ "X INT, "
			+ "Z INT, "
			+ "Radius INT, "
			+ "SpawnCoords VARCHAR(255), "
			+ "SpawnDirection VARCHAR(255), "
			+ "Priv BOOLEAN, "
			+ "PvP BOOLEAN, "
			+ "Monsters BOOLEAN, "
			+ "Animals BOOLEAN, "
			+ "Enter VARCHAR(255), "
			+ "Leave_ VARCHAR(255), "
			+ "PRIMARY KEY (PlaceID), UNIQUE (Name));";
	
	private final static String players = "CREATE TABLE IF NOT EXISTS players "
			+ "(ID VARCHAR(255), "
			+ "Player VARCHAR(255), "
			+ "IP VARCHAR(255), "
			+ "LastPlayed BIGINT, "
			+ "TimePlayed BIGINT, "
			+ "Mute BOOLEAN, "
			+ "Banned BOOLEAN, "
			+ "BanTime BIGINT, "
			+ "BanExpire BIGINT, "
			+ "BanReason VARCHAR(255), "
			+ "BannedBy VARCHAR(255), "
			+ "PRIMARY KEY (ID));";
	
	private final static String woolChest = "CREATE TABLE IF NOT EXISTS woolchests "
			+ "(Owner VARCHAR(255), "
			+ "Chest SMALLINT, "
			+ "Content MEDIUMTEXT, "
			+ "PRIMARY KEY (Owner, Chest));";
	
	private final static String worlds = "CREATE TABLE IF NOT EXISTS worlds "
			+ "(World VARCHAR(255), "
			+ "Environment VARCHAR(255), "
			+ "Type VARCHAR(255), "
			+ "Seed BIGINT, "
			+ "Coords VARCHAR(255), "
			+ "Direction VARCHAR(255), "
			+ "KeepSpawn BOOLEAN, "
			+ "PvP BOOLEAN, "
			+ "Monsters BOOLEAN, "
			+ "Animals BOOLEAN, "
			+ "MonsterGrief BOOLEAN, "
			+ "FireSpread BOOLEAN, "
			+ "Claimable BOOLEAN, "
			+ "Explosions BOOLEAN, "
			+ "Trample BOOLEAN, "
			+ "Border INT, "
			+ "PRIMARY KEY (World));";
	
	public static void createTables(Connection conn) {
		try {
			Statement stmtHomes = conn.createStatement();
			stmtHomes.executeUpdate(homes);
			stmtHomes.close();
	
			Statement stmtPlaces = conn.createStatement();
			stmtPlaces.executeUpdate(places);
			stmtPlaces.close();
			
			Statement stmtPlayers = conn.createStatement();
			stmtPlayers.executeUpdate(players);
			stmtPlayers.close();
	
			Statement stmtWoolChest = conn.createStatement();
			stmtWoolChest.executeUpdate(woolChest);
			stmtWoolChest.close();
	
			Statement stmtWorlds = conn.createStatement();
			stmtWorlds.executeUpdate(worlds);
			stmtWorlds.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
