package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MsgToOthers {
	
	/**
	 * 
	 * @param player as Object
	 * @param msg that will be sent to all other players
	 * 
	 * <p>This will send given message to all other players
	 */
	public void sendMessage(Player player, String msg) {
		String playerName = player.getName();
		for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
			if (!onlinePlayer.getName().equals(playerName)) {
				onlinePlayer.sendMessage(msg);
			}
		}
	}
}
