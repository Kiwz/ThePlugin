package net.kiwz.ThePlugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;

public class MoveListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		int fromX = (int) event.getFrom().getX();
		int fromZ = (int) event.getFrom().getZ();
		int toX = (int) event.getTo().getX();
		int toZ = (int) event.getTo().getZ();

		if (fromX == toX && fromZ == toZ) {
			return;
		}
		int fromID = places.getIDWithCoords(fromX, fromZ);
		int toID = places.getIDWithCoords(toX, toZ);
		if (fromID == toID) {
			return;
		}
		if (toID == 0) {
			if (places.getLeave(fromID).equals("")) {
				player.sendMessage(ThePlugin.c1 + "Du forlater " + places.getName(fromID));
			}
			else {
				player.sendMessage(ThePlugin.c1 + places.getLeave(fromID));
			}
		}
		
		else if (fromID == 0) {
			if (places.getEnter(toID).equals("")) {
				player.sendMessage(ThePlugin.c1 + "Velkommen til " + places.getName(toID));
			}
			else {
				player.sendMessage(ThePlugin.c1 + places.getEnter(toID));
			}
		}
		
		else {
			if (places.getLeave(fromID).equals("")) {
				player.sendMessage(ThePlugin.c1 + "Du forlater " + places.getName(fromID));
			}
			else {
				player.sendMessage(ThePlugin.c1 + places.getLeave(fromID));
			}
			
			if (places.getEnter(toID).equals("")) {
				player.sendMessage(ThePlugin.c1 + "Velkommen til " + places.getName(toID));
			}
			else {
				player.sendMessage(ThePlugin.c1 + places.getEnter(toID));
			}
		}
	}
}
