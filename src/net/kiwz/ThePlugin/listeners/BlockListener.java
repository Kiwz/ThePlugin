package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.utils.HandlePlaces;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
	
	private String denyString = ChatColor.RED + "Du har ingen tilgang her";
	private HandlePlaces hPlaces = new HandlePlaces();
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		
		if (!hPlaces.hasAccess(player, loc)) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
    }
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		
		if (!hPlaces.hasAccess(player, loc)) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
    }
}
