package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import net.kiwz.ThePlugin.ThePlugin;

public class MakeTables {
	
	private Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
	
	private String createPlayers = "CREATE TABLE IF NOT EXISTS players "
			+ "(PlayerID INT NOT NULL AUTO_INCREMENT, Player VARCHAR(255), UNIQUE (PlayerID), PRIMARY KEY (Player));";
	
	private String createHomes = "CREATE TABLE IF NOT EXISTS homes "
			+ "(PlayerID INT, World VARCHAR(255), Coords VARCHAR(255), Pitch VARCHAR(255));";
	
	private String createWorlds = "CREATE TABLE IF NOT EXISTS worlds "
			+ "(World VARCHAR(255), Coords VARCHAR(255), Pitch VARCHAR(255), PvP BOOLEAN, Claimable BOOLEAN, "
			+ "FireSpread BOOLEAN, Explosions BOOLEAN, Endermen BOOLEAN, Trample BOOLEAN, Monsters BOOLEAN, "
			+ "Animals BOOLEAN, PRIMARY KEY (World));";
	
	private String createPlaces = "CREATE TABLE IF NOT EXISTS places "
			+ "(PlaceID INT NOT NULL AUTO_INCREMENT, Time INT, Name VARCHAR(255), Owner VARCHAR(255), Members MEDIUMTEXT, "
			+ "X INT, Z INT, Size INT, SpawnCoords VARCHAR(255), SpawnPitch VARCHAR(255), PvP BOOLEAN, "
			+ "Monsters BOOLEAN, Animals BOOLEAN, PRIMARY KEY (PlaceID), UNIQUE (Name));";
	
	public void createTables() {
		
		Connection conn = ThePlugin.conn;
		
		try {
			Statement stmtCreatePlayers = conn.createStatement();
			stmtCreatePlayers.executeUpdate(createPlayers);
			stmtCreatePlayers.close();
			
			Statement stmtCreateHomes = conn.createStatement();
			stmtCreateHomes.executeUpdate(createHomes);
			stmtCreateHomes.close();
	
			Statement stmtCreateWorlds = conn.createStatement();
			stmtCreateWorlds.executeUpdate(createWorlds);
			stmtCreateWorlds.close();
	
			Statement stmtCreatePlaces = conn.createStatement();
			stmtCreatePlaces.executeUpdate(createPlaces);
			stmtCreatePlaces.close();
			
		} catch (SQLException e) {
			pl.getLogger().severe("Create table(s) failed");
			e.printStackTrace();
		}
	}
}
