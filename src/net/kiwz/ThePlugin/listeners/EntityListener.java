package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;

public class EntityListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private String denyString = ThePlugin.c2 + "Du har ingen tilgang her";
	private String pvpDenyString = ThePlugin.c2 + "PvP er deaktivert på denne plassen";
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		
		if (event.getCause() == RemoveCause.ENTITY) {
			Player player = (Player) event.getRemover();
			if (!places.hasAccess(player, event.getEntity().getLocation())) {
				event.setCancelled(true);
				player.sendMessage(denyString);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getDamager();
		Location loc = event.getEntity().getLocation();
		int id = places.getIDWithCoords(loc.getX(), loc.getZ());
		if (id == 0) {
			return;
		}
		
		if (event.getEntity() instanceof Player) {
			if (places.getPvP(id).equals("DEAKTIVERT")) {
				event.setCancelled(true);
				player.sendMessage(pvpDenyString);
			}
		}
		
		if (event.getEntity() instanceof Animals) {
			if (!places.hasAccess(player, loc)) {
				event.setCancelled(true);
				player.sendMessage(denyString);
			}
		}
	}
}
