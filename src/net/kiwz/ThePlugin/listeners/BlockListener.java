package net.kiwz.ThePlugin.listeners;

import java.util.List;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;
import net.kiwz.ThePlugin.utils.Log;
import net.kiwz.ThePlugin.utils.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandleWorlds worlds = new HandleWorlds();
    private Permissions perm = new Permissions();
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
		if (mat.equals(Material.DIAMOND_ORE) || mat.equals(Material.EMERALD_ORE)) {
			for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
				if (perm.isAdmin(thisPlayer) || thisPlayer.isOp()) {
					thisPlayer.sendMessage(ThePlugin.c1 + player.getName() + ThePlugin.c3 + " fant en " + mat);
				}
			}
			String world = loc.getWorld().getName();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			Log log = new Log();
			log.logString(" [MINING] " + player.getName() + ": " + mat + " ([" + world + "] " + x + ", " + y + ", " + z + ")");
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
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPistonRetract(BlockPistonRetractEvent event) {
		Block block = event.getBlock();
		if (block.getType() != Material.PISTON_STICKY_BASE) {
			return;
		}
		block = block.getRelative(event.getDirection());
		
		if (!block.getRelative(event.getDirection()).getType().equals(Material.AIR) && !block.isLiquid()) {
			Location fromLoc = block.getRelative(event.getDirection()).getLocation();
			Location toLoc = block.getLocation();
			int fromID = places.getIDWithCoords(fromLoc);
			int toID = places.getIDWithCoords(toLoc);
			String fromOwner = "";
			String toOwner = "";
			if (fromID != toID) {
				if (fromID != 0) {
					fromOwner = places.getOwner(fromID);
				}
				if (toID != 0) {
					toOwner = places.getOwner(toID);
				}
				if (!fromOwner.equals(toOwner)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPistonExtend(BlockPistonExtendEvent event) {
		List<Block> blocks = event.getBlocks();
		if (!blocks.isEmpty()) {
			for (Block block : blocks) {
				Location fromLoc = block.getLocation();
				Location toLoc = block.getRelative(event.getDirection()).getLocation();
				int fromID = places.getIDWithCoords(fromLoc);
				int toID = places.getIDWithCoords(toLoc);
				String fromOwner = "";
				String toOwner = "";
				if (fromID != toID) {
					if (fromID != 0) {
						fromOwner = places.getOwner(fromID);
					}
					if (toID != 0) {
						toOwner = places.getOwner(toID);
					}
					if (!fromOwner.equals(toOwner)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockMove(BlockFromToEvent event) {
		Location fromLoc = event.getBlock().getLocation();
		Location toLoc = event.getToBlock().getLocation();
		int fromID = places.getIDWithCoords(fromLoc);
		int toID = places.getIDWithCoords(toLoc);
		String fromOwner = "";
		String toOwner = "";
		if (fromID != toID) {
			if (fromID != 0) {
				fromOwner = places.getOwner(fromID);
			}
			if (toID != 0) {
				toOwner = places.getOwner(toID);
			}
			if (!fromOwner.equals(toOwner)) {
				event.setCancelled(true);
			}
		}
	}
}
