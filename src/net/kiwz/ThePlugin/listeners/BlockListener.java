package net.kiwz.ThePlugin.listeners;

import java.util.List;

import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.ServerManager;

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
	private String denyString = Color.WARNING + "Du har ingen tilgang her";

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		Material mat = event.getBlock().getType();
		MyPlayer myPlayer = MyPlayer.getPlayer(player);
		Place place = Place.getPlace(loc);
		
		if (place != null) {
			if (!place.hasAccess(myPlayer)) {
				event.setCancelled(true);
				player.sendMessage(denyString);
			}
		} else if (!myPlayer.isAdmin() && MyWorld.getWorld(loc.getWorld()).getClaimable() && loc.getBlockY() > 40) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
		
		if (mat.equals(Material.DIAMOND_ORE) || mat.equals(Material.EMERALD_ORE)) {
			for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
				if (MyPlayer.getPlayer(thisPlayer).isAdmin()) {
					thisPlayer.sendMessage(MyPlayer.getColorName(myPlayer) + " fant en " + Color.VARIABLE + mat);
				}
			}
			String world = loc.getWorld().getName();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			ServerManager.logString("[MINING] " + player.getName() + ": " + mat + " (" + world + " " + x + ", " + y + ", " + z + ")");
		}
    }

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		MyPlayer myPlayer = MyPlayer.getPlayer(player);
		Place place = Place.getPlace(loc);
		
		if (place != null) {
			if (!place.hasAccess(myPlayer)) {
				event.setCancelled(true);
				player.sendMessage(denyString);
			}
		} else if (!myPlayer.isAdmin() && MyWorld.getWorld(loc.getWorld()).getClaimable() && loc.getBlockY() > 40) {
			event.setCancelled(true);
			player.sendMessage(denyString);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event) {
		
		if (event.getCause().equals(IgniteCause.LAVA)) {
			Location fromLoc = event.getIgnitingBlock().getLocation();
			Location toLoc = event.getBlock().getLocation();
			Place fromPlace = Place.getPlace(fromLoc);
			Place toPlace = Place.getPlace(toLoc);
			String fromOwner = "";
			String toOwner = "";
			if (fromPlace != null) fromOwner = fromPlace.getOwner();
			if (toPlace != null) toOwner = toPlace.getOwner();
			if (!fromOwner.equals(toOwner)) {
				event.setCancelled(true);
			}
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
			Place fromPlace = Place.getPlace(fromLoc);
			Place toPlace = Place.getPlace(toLoc);
			String fromOwner = "";
			String toOwner = "";
			if (fromPlace != null) fromOwner = fromPlace.getOwner();
			if (toPlace != null) toOwner = toPlace.getOwner();
			if (!fromOwner.equals(toOwner)) {
				event.setCancelled(true);
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
				Place fromPlace = Place.getPlace(fromLoc);
				Place toPlace = Place.getPlace(toLoc);
				String fromOwner = "";
				String toOwner = "";
				if (fromPlace != null) fromOwner = fromPlace.getOwner();
				if (toPlace != null) toOwner = toPlace.getOwner();
				if (!fromOwner.equals(toOwner)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockMove(BlockFromToEvent event) {
		Location fromLoc = event.getBlock().getLocation();
		Location toLoc = event.getToBlock().getLocation();
		Place fromPlace = Place.getPlace(fromLoc);
		Place toPlace = Place.getPlace(toLoc);
		String fromOwner = "";
		String toOwner = "";
		if (fromPlace != null) fromOwner = fromPlace.getOwner();
		if (toPlace != null) toOwner = toPlace.getOwner();
		if (!fromOwner.equals(toOwner)) {
			event.setCancelled(true);
		}
	}
}
