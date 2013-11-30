package net.kiwz.ThePlugin.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;

public class MoveListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandleWorlds worlds = new HandleWorlds();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location fromLoc = event.getFrom();
		int fromX = (int) event.getFrom().getX();
		int fromZ = (int) event.getFrom().getZ();
		int toX = (int) event.getTo().getX();
		int toZ = (int) event.getTo().getZ();
		if (fromX == toX && fromZ == toZ) {
			return;
		}
		int fromID = places.getIDWithCoords(player.getLocation().getWorld().getName(), fromX, fromZ);
		int toID = places.getIDWithCoords(player.getLocation().getWorld().getName(), toX, toZ);
		places.sendEnterLeave(player, fromID, toID);
		int border = worlds.getBorder(event.getPlayer().getWorld());
		if (border < toX || -border + 1 > toX || border < toZ || -border + 1 > toZ) {
			player.teleport(fromLoc);
			player.sendMessage(ThePlugin.c2 + "Du har nådd enden av denne verden");
		}
	}
}
