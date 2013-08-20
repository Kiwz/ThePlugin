package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class DeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		event.setKeepLevel(true);
		event.setDeathMessage("");
		
		Location loc = event.getEntity().getLocation();
		String world = loc.getWorld().getName();
		String x = Double.toString(loc.getX()).replaceAll("\\..*","");
		String y = Double.toString(loc.getY()).replaceAll("\\..*","");
		String z = Double.toString(loc.getZ()).replaceAll("\\..*","");
		
		event.getEntity().getPlayer().sendMessage(ChatColor.RED + "Du døde i " + world
				+ " x:" + x + " y:" + y + " z:" + z);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ThePlugin");
		HandleWorlds spawn = new HandleWorlds();
		final Location loc = spawn.getSpawn(event.getPlayer().getWorld().getName());
		final Player player = event.getPlayer();
		
		if (player.getBedSpawnLocation() == null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					@Override
	                public void run() {
					player.teleport(loc);
				}
			}, 1);
		}
	}
}
