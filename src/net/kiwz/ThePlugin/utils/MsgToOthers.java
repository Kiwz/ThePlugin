package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MsgToOthers {
	
	public void sendMessage(Player player, String msg) {
		String playerName = player.getName();
		for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
			if (!onlinePlayer.getName().equals(playerName)) {
				onlinePlayer.sendMessage(msg);
			}
		}
	}
}
