package net.kiwz.ThePlugin.listeners;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandlePlaces;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;

public class EntityListener implements Listener {
	private HandlePlaces places = new HandlePlaces();
	private HandleWorlds worlds = new HandleWorlds();
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
		int id = places.getIDWithCoords(loc.getWorld().getName(), loc.getX(), loc.getZ());
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
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityInteract(EntityInteractEvent event) {
		Block block = event.getBlock();

		if (!worlds.isTrample(block.getWorld())) {
			if ((block.getType() == Material.SOIL) || (block.getType() == Material.CROPS)) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		World world = event.getLocation().getWorld();
		EntityType type = event.getEntity().getType();
		
		if (type == EntityType.CREEPER || type == EntityType.FIREBALL) {
			if (!worlds.isMonsterGrief(world)) {
				event.setCancelled(true);
				return;
			}
		}
		else {
			if (!worlds.isExplosions(world)) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
		World world = event.getBlock().getWorld();
		switch (event.getEntity().getType()) {
		case ENDERMAN:
			if (!worlds.isMonsterGrief(world)) {
				event.setCancelled(true);
				return;
			}
			break;
		case WITHER:
			if (!worlds.isMonsterGrief(world)) {
				event.setCancelled(true);
				return;
			}
			break;
		default:
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		switch (event.getSpawnReason()) {
		case BUILD_WITHER:
			if (event.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
				event.setCancelled(true);
			}
			break;
		default:
		}
	}
}
