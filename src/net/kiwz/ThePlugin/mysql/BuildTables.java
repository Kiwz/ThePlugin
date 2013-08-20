package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildTables {
	
	private String homes = "CREATE TABLE IF NOT EXISTS homes "
			+ "(Player VARCHAR(255), World VARCHAR(255), Coords VARCHAR(255), Pitch VARCHAR(255));";
	
	private String places = "CREATE TABLE IF NOT EXISTS places "
			+ "(PlaceID INT, Time INT, Name VARCHAR(255), Owner VARCHAR(255), Members MEDIUMTEXT, "
			+ "World VARCHAR(255), X INT, Z INT, Size INT, SpawnCoords VARCHAR(255), SpawnPitch VARCHAR(255), "
			+ "PvP BOOLEAN, Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (PlaceID), UNIQUE (Name));";
	
	private String players = "CREATE TABLE IF NOT EXISTS players "
			+ "(Player VARCHAR(255), LastLogin INT, TimePlayed INT, PRIMARY KEY (Player));";
	
	private String worlds = "CREATE TABLE IF NOT EXISTS worlds "
			+ "(World VARCHAR(255), Coords VARCHAR(255), Pitch VARCHAR(255), PvP BOOLEAN, Claimable BOOLEAN, "
			+ "FireSpread BOOLEAN, Explosions BOOLEAN, Endermen BOOLEAN, Trample BOOLEAN, Monsters BOOLEAN, "
			+ "Animals BOOLEAN, PRIMARY KEY (World));";
	
	public void createTables(Connection conn) {
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
	
			Statement stmtWorlds = conn.createStatement();
			stmtWorlds.executeUpdate(worlds);
			stmtWorlds.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
