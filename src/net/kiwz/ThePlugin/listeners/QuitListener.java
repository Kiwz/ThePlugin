package net.kiwz.ThePlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
	
	@EventHandler
	public void OnPlayerQuit(PlayerQuitEvent event) {
		Server server = Bukkit.getServer();
		event.setQuitMessage(ChatColor.RED + event.getPlayer().getName() + " logget ut");
		for (World worlds : server.getWorlds()) {
			for (Player players : server.getWorld(worlds.getName()).getPlayers()) {
				server.getWorld("world").strikeLightningEffect(players.getLocation());
			}
		}
	}
}
