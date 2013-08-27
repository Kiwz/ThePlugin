package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnlinePlayer {
	
	/**
	 * 
	 * @param playerName as String
	 * @return searched playerName as a Player object
	 */
	public Player getPlayer(String playerName) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().toLowerCase().startsWith(playerName.toLowerCase())) {
				return Bukkit.getServer().getPlayer(player.getName());
			}
		}
		return null;
	}
}
