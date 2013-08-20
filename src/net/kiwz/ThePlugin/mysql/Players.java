package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Players {
	
	public String player;
	public int lastLogin;
	public int timePlayed;
	
	public HashMap<String, Players> getTablePlayers(Connection conn) {
		Players player = new Players();
		HashMap<String, Players> players = new HashMap<String, Players>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM players;");
			while (res.next()) {
				player.player = res.getString("Player");
				player.lastLogin = res.getInt("LastLogin");
				player.timePlayed = res.getInt("TimePlayed");
				
				players.put(player.player, player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	public void setTablePlayers(Connection conn, HashMap<String, Players> players) {
		List<String> oldKeys = new ArrayList<String>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM players;");
			while (res.next()) {
				for (String key : players.keySet()) {
					String player = players.get(key).player;
					int lastLogin = players.get(key).lastLogin;
					int timePlayed = players.get(key).timePlayed;
					
					if (player.equals(res.getString("Player"))) {
						conn.createStatement().executeUpdate("UPDATE players SET LastLogin='" + lastLogin + "', TimePlayed='" + timePlayed + "' WHERE Player LIKE '" + player + "';");
						oldKeys.add(key);
					}
				}
			}
			for (String key : oldKeys) {
				players.remove(key);
			}
			for (String key : players.keySet()) {
				String player = players.get(key).player;
				int lastLogin = players.get(key).lastLogin;
				int timePlayed = players.get(key).timePlayed;
				
				conn.createStatement().executeUpdate("INSERT INTO players (Player, LastLogin, TimePlayed) "
						+ "VALUES ('" + player + "', '" + lastLogin + "', '" + timePlayed + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
