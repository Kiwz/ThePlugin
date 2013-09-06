package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Location;
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
		
		if (!places.hasAccess(player, loc)) {
			event.setCancelled(true);
			player.sendMessage(denyString);
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
