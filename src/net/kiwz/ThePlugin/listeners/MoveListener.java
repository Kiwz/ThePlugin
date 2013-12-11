package net.kiwz.ThePlugin.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;
import net.kiwz.ThePlugin.utils.TPRune;
import net.kiwz.ThePlugin.utils.WarnPvP;

public class MoveListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandleWorlds worlds = new HandleWorlds();
	private WarnPvP warnPvP = new WarnPvP();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location fromLoc = event.getFrom();
		Block block = event.getTo().getBlock();
		int fromX = (int) event.getFrom().getX();
		int fromY = (int) event.getFrom().getY();
		int fromZ = (int) event.getFrom().getZ();
		int toX = (int) event.getTo().getX();
		int toY = (int) event.getTo().getY();
		int toZ = (int) event.getTo().getZ();
		if (fromX == toX && fromY == toY && fromZ == toZ) {
			return;
		}
		int fromID = places.getIDWithCoords(player.getWorld().getName(), fromX, fromZ);
		int toID = places.getIDWithCoords(player.getWorld().getName(), toX, toZ);
		places.sendEnterLeave(player, fromID, toID);
		warnPvP.warnPvP(player, player.getWorld(), player.getWorld(), fromID, toID);
		int border = worlds.getBorder(event.getPlayer().getWorld());
		if (border < toX || -border + 1 > toX || border < toZ || -border + 1 > toZ) {
			player.teleport(fromLoc);
			player.sendMessage(ThePlugin.c2 + "Du har nådd enden av denne verden");
		}
		if (block.getRelative(0, -2, 0).getType().equals(Material.DIAMOND_BLOCK)) {
			TPRune tpRune = new TPRune();
			tpRune.teleportRune1(player, block.getRelative(0, -2, 0), Material.GOLD_BLOCK);
		}
		if (block.getRelative(0, -3, 0).getType().equals(Material.DIAMOND_BLOCK)) {
			TPRune tpRune = new TPRune();
			tpRune.teleportRune2(player, block.getRelative(0, -3, 0), Material.GOLD_BLOCK);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		World fromWorld = event.getFrom().getWorld();
		World toWorld = event.getTo().getWorld();
		int fromX = (int) event.getFrom().getX();
		int fromZ = (int) event.getFrom().getZ();
		int toX = (int) event.getTo().getX();
		int toZ = (int) event.getTo().getZ();
		int fromID = places.getIDWithCoords(fromWorld.getName(), fromX, fromZ);
		int toID = places.getIDWithCoords(toWorld.getName(), toX, toZ);
		places.sendEnterLeave(player, fromID, toID);
		warnPvP.warnPvP(player, fromWorld, toWorld, fromID, toID);
	}
}
