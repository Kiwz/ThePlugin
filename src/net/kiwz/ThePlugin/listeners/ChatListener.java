package net.kiwz.ThePlugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event) {
		if (event.getPlayer().isOp()) {
			event.setFormat(ChatColor.RED + "%s: " + ChatColor.WHITE + "%s");
		}
		else {
			event.setFormat("%s: %s");
		}
	}
}
