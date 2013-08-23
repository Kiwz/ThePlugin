package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Players {
	
	public String player;
	public int lastLogin;
	public int timePlayed;
	public int mute;
	public String ip;
	
	public HashMap<String, Players> getTablePlayers(Connection conn) {
		HashMap<String, Players> players = new HashMap<String, Players>();
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM players;");
			while (res.next()) {
				Players player = new Players();
				player.player = res.getString("Player");
				player.lastLogin = res.getInt("LastLogin");
				player.timePlayed = res.getInt("TimePlayed");
				player.mute = res.getInt("Mute");
				player.ip = res.getString("IP");
				
				players.put(player.player.toUpperCase(), player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	public void setTablePlayers(Connection conn, HashMap<String, Players> players) {
		for (String key : players.keySet()) {
			String player = players.get(key).player;
			int lastLogin = players.get(key).lastLogin;
			int timePlayed = players.get(key).timePlayed;
			int mute = players.get(key).mute;
			String ip = players.get(key).ip;
			String queryString = "INSERT INTO players (Player, LastLogin, TimePlayed, Mute, IP) "
					+ "VALUES ('" + player + "', '" + lastLogin + "', '" + timePlayed + "', '"
					+ mute + "', '" + ip + "') "
					+ "ON DUPLICATE KEY UPDATE LastLogin='" + lastLogin + "', TimePlayed='" + timePlayed + "', "
					+ "Mute='" + mute + "', IP='" + ip + "';";
			try {
				conn.createStatement().executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
