package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;
import net.kiwz.ThePlugin.utils.Log;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandleWorlds worlds = new HandleWorlds();
	private String denyString = ThePlugin.c2 + "Du har ingen tilgang her";

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		Material mat = event.getBlock().getType();
		
		if (!places.hasAccess(player, loc)) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
		else {
			if (mat.equals(Material.DIAMOND_ORE) || mat.equals(Material.EMERALD_ORE)) {
				for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
					thisPlayer.sendMessage(ThePlugin.c1 + player.getName() + ThePlugin.c3 + " fant en " + mat);
				}
				String world = loc.getWorld().getName();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				Log log = new Log();
				log.logString(" [MINING] " + player.getName() + ": " + mat + " ([" + world + "] " + x + ", " + y + ", " + z + ")");
			}
		}
    }

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		
		if (!places.hasAccess(player, loc)) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
    }
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event) {
		Block block = event.getBlock();
		
		if (!worlds.isFireSpread(block.getWorld()) && event.getCause() == IgniteCause.SPREAD) {
			event.setCancelled(true);
			return;
		}
	}
}
