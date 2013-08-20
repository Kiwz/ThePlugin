package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Players {
	
	public String player;
	public int timePlayed;
	
	public HashMap<String, Players> getTablePlayers(Connection conn) {
		MySQLQuery query = new MySQLQuery();
		Players player = new Players();
		HashMap<String, Players> players = new HashMap<String, Players>();
		try {
			ResultSet res = query.query(conn, "SELECT * FROM players;");
			while (res.next()) {
				player.player = res.getString("Player");
				player.timePlayed = res.getInt("TimePlayed");
				
				players.put(player.player, player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	public void setTablePlayers(Connection conn, HashMap<String, Players> players) {
		MySQLQuery query = new MySQLQuery();
		List<Integer> oldKeys = new ArrayList<Integer>();
		try {
			ResultSet res = query.query(conn, "SELECT * FROM players;");
			while (res.next()) {
				for (String key : players.keySet()) {
					String player = players.get(key).player;
					int timePlayed = players.get(key).timePlayed;
					
					if (player.equals(res.getString("Player"))) {
						query.update(conn, "UPDATE players SET TimePlayed='" + timePlayed + "' WHERE Player LIKE '" + player + "';");
					}
				}
			}
			for (int key : oldKeys) {
				players.remove(key);
			}
			for (String key : players.keySet()) {
				String player = players.get(key).player;
				int timePlayed = players.get(key).timePlayed;
				
				query.update(conn, "INSERT INTO players (Player, TimePlayed) "
						+ "VALUES ('" + player + "', '" + timePlayed + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
