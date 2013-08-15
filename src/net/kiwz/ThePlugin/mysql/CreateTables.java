package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.PreparedStatement;

public class CreateTables {

	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
	private MySQL MySQL = new MySQL(plugin, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
	private Connection conn = null;
	
	private String createPlayers = "CREATE TABLE IF NOT EXISTS Players "
			+ "(PlayerID INT NOT NULL AUTO_INCREMENT, Player VARCHAR(255), UNIQUE (PlayerID), PRIMARY KEY (Player));";
	
	private String createHomes = "CREATE TABLE IF NOT EXISTS Homes "
			+ "(PlayerID INT, World VARCHAR(255), Coords VARCHAR(255), Yaw VARCHAR(255));";
	
	private String createWorlds = "CREATE TABLE IF NOT EXISTS Worlds "
			+ "(World VARCHAR(255), PvP BOOLEAN, Claimable BOOLEAN, FireSpread BOOLEAN, Explosions BOOLEAN,"
			+ "Endermen BOOLEAN, Trample BOOLEAN, Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (World));";
	
	private String createPlaces = "CREATE TABLE IF NOT EXISTS Places "
			+ "(PlaceID INT NOT NULL AUTO_INCREMENT, Time INT, Name VARCHAR(255), Owner VARCHAR(255), Members MEDIUMTEXT, "
			+ "Coords VARCHAR(255), SpawnCoords VARCHAR(255), SpawnYaw VARCHAR(255), PvP BOOLEAN, "
			+ "Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (PlaceID), UNIQUE (Name));";
	
	public void createTables() throws SQLException {
		conn = MySQL.openConnection();
		
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
		
		conn.close();
	}
}
