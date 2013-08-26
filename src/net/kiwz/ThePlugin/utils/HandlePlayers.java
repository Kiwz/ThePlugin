package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Players;

public class HandlePlayers {
	private HashMap<String, Players> players = ThePlugin.getPlayers;
	
	/**
	 * 
	 * @param playerName to check
	 * @return {@code true} if given player has played before,
	 * or {@code false} if given player has not played before.
	 */
	public boolean hasPlayedBefore(String playerName) {
		return players.containsKey(playerName.toUpperCase());
	}
	
	/**
	 * 
	 * @param playerName
	 * @return {@code int} time played for given player in seconds,
	 * or {@code null} if given player has not played before.
	 */
	public String getPlayerName(String playerName) {
		return players.get(playerName.toUpperCase()).player;
	}
	
	/**
	 * 
	 * @param playerName
	 * @return {@code int} time played for given player in seconds,
	 * or {@code null} if given player has not played before.
	 */
	public int getPlayerTimePlayed(String playerName) {
		return players.get(playerName.toUpperCase()).timePlayed;
	}
	
	/**
	 * <p>This method will add a given player to the Players HashMap. Should
	 * only be used once per player.</p>
	 * @param playerName
	 */
	public void addPlayer(String playerName) {
		
		Players player = new Players();
		player.player = playerName;
		player.lastLogin = (int) (System.currentTimeMillis() / 1000);
		player.timePlayed = 0;
		player.mute = 0;
		player.ip = Bukkit.getPlayer(playerName.toUpperCase()).getAddress().toString();
		players.put(playerName.toUpperCase(), player);
	}
	
	/**
	 * <p>This method will set <b>this</b> time (in seconds) as the Last Login time for given player.
	 * @param playerName
	 */
	public void setLastLogin(String playerName) {
		players.get(playerName.toUpperCase()).lastLogin = (int) (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * <p>This method will set the time played (in seconds) for given player.
	 * @param playerName
	 */
	public void setTimePlayed(String playerName) {
		players.get(playerName.toUpperCase()).timePlayed = (int) (System.currentTimeMillis() / 1000)
				- players.get(playerName.toUpperCase()).lastLogin+ players.get(playerName.toUpperCase()).timePlayed;
	}
	
	/**
	 * <p>This method will set the "mute" status for given player.
	 * @param playerName
	 */
	public void setMute(String playerName, int mute) {
		players.get(playerName.toUpperCase()).mute = mute;
	}
	
	/**
	 * <p>This method will set the IP-adress for given player.
	 * @param playerName
	 */
	public void setIP(String playerName) {
		players.get(playerName.toUpperCase()).ip = Bukkit.getPlayer(playerName).getAddress().toString();
	}
}
