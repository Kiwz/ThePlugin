package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnlinePlayer {
	
	public Player getPlayer(String playerName) {
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().toLowerCase().startsWith(playerName.toLowerCase())) {
				return Bukkit.getServer().getPlayer(player.getName());
			}
		}
		return null;
	}
}
