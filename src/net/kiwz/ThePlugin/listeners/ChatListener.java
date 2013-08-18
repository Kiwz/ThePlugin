package net.kiwz.ThePlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event) {
		//event.setFormat("%s: %s");
		Player player = event.getPlayer();
		String msg = player.getName() + ": " + event.getMessage();
		
		if (event.getPlayer().isOp()) {
			//event.setFormat(ChatColor.RED + "%s: " + ChatColor.WHITE + "%s");
			msg = ChatColor.RED + player.getName() + ": " + ChatColor.WHITE + event.getMessage();
		}
		for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
			thisPlayer.sendMessage(msg);
		}
		event.setCancelled(true);
	}
}
