package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class CreateTables {
	
	private String createPlayers = "CREATE TABLE IF NOT EXISTS players "
			+ "(PlayerID INT NOT NULL AUTO_INCREMENT, Player VARCHAR(255), UNIQUE (PlayerID), PRIMARY KEY (Player));";
	
	private String createHomes = "CREATE TABLE IF NOT EXISTS homes "
			+ "(PlayerID INT, World VARCHAR(255), Coords VARCHAR(255), Yaw VARCHAR(255));";
	
	private String createWorlds = "CREATE TABLE IF NOT EXISTS worlds "
			+ "(World VARCHAR(255), PvP BOOLEAN, Claimable BOOLEAN, FireSpread BOOLEAN, Explosions BOOLEAN,"
			+ "Endermen BOOLEAN, Trample BOOLEAN, Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (World));";
	
	private String createPlaces = "CREATE TABLE IF NOT EXISTS places "
			+ "(PlaceID INT NOT NULL AUTO_INCREMENT, Time INT, Name VARCHAR(255), Owner VARCHAR(255), Members MEDIUMTEXT, "
			+ "Coords VARCHAR(255), SpawnCoords VARCHAR(255), SpawnYaw VARCHAR(255), PvP BOOLEAN, "
			+ "Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (PlaceID), UNIQUE (Name));";
	
	public void createTables(Connection conn) throws SQLException {
		
		PreparedStatement execCreatePlayers = (PreparedStatement) conn.prepareStatement(createPlayers);
		execCreatePlayers.executeUpdate();
		execCreatePlayers.close();
		
		PreparedStatement execCreateHomes = (PreparedStatement) conn.prepareStatement(createHomes);
		execCreateHomes.executeUpdate();
		execCreateHomes.close();
		
		PreparedStatement execCreateWorlds = (PreparedStatement) conn.prepareStatement(createWorlds);
		execCreateWorlds.executeUpdate();
		execCreateWorlds.close();
		
		PreparedStatement execCreatePlaces = (PreparedStatement) conn.prepareStatement(createPlaces);
		execCreatePlaces.executeUpdate();
		execCreatePlaces.close();
		
	}
}
