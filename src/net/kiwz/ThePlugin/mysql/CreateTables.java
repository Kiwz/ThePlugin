package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.PreparedStatement;

public class CreateTables {
	
	private String createPlayers = "CREATE TABLE IF NOT EXISTS Players "
			+ "(PlayerID int, Player varchar(255), UNIQUE (PlayerID));";
	
	private String createHomes = "CREATE TABLE IF NOT EXISTS Homes "
			+ "(PlayerID int, World varchar(255), Coords varchar(255), Yaw varchar(255));";
	
	private String createWorlds = "CREATE TABLE IF NOT EXISTS Worlds "
			+ "(World varchar(255), PvP boolean, Claimable boolean, FireSpread boolean, Explosions boolean,"
			+ "Endermen boolean, Trample boolean, Monsters boolean, Animals boolean, UNIQUE (World));";
	
	private String createPlaces = "CREATE TABLE IF NOT EXISTS Places "
			+ "(PlaceID int, Time int, Name varchar(255), Owner varchar(255), Members mediumtext, "
			+ "Coords varchar(255), SpawnCoords varchar(255), SpawnYaw varchar(255), PvP boolean, "
			+ "Monsters boolean, Animals boolean, UNIQUE (PlaceID, Name));";

	private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
	MySQL MySQL = new MySQL(plugin, "109.247.37.74", "3306", "theplugin", "kiwz", "test");
	Connection conn = null;
	
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
