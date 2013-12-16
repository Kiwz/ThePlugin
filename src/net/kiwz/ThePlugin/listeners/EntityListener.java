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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
		Entity attacker = event.getDamager();
		Player playerAttacker;
		Entity victim = event.getEntity();
		Player playerVictim;
		Location victimLoc = victim.getLocation();
		int id = places.getIDWithCoords(victimLoc);
		
		if (attacker instanceof Player && victim instanceof Player) {
			playerAttacker = (Player) attacker;
			playerVictim = (Player) victim;
			if (ThePlugin.pvpPlayers.contains(playerVictim.getName())) {
				event.setCancelled(true);
				playerAttacker.sendMessage(ThePlugin.c2 + "Du kan ikke skade en Admin");
			}
			else if (ThePlugin.pvpPlayers.contains(playerAttacker.getName())) {
				event.setCancelled(true);
				playerAttacker.sendMessage(ThePlugin.c2 + "Du kan ikke skade andre når du har skrudd av pvp");
			}
			else if (id == 0) {
				return;
			}
			else if (!places.isPvP(id)) {
				event.setCancelled(true);
				playerAttacker.sendMessage(pvpDenyString);
			}
		}
		
		if (attacker instanceof Projectile && victim instanceof Player) {
			LivingEntity shooter = ((Projectile) attacker).getShooter();
			if (shooter instanceof Player) {
				playerAttacker = (Player) shooter;
				playerVictim = (Player) victim;
				if (ThePlugin.pvpPlayers.contains(playerVictim.getName())) {
					event.setCancelled(true);
					playerAttacker.sendMessage(ThePlugin.c2 + "Du kan ikke skade en Admin");
				}
				else if (ThePlugin.pvpPlayers.contains(playerAttacker.getName())) {
					event.setCancelled(true);
					playerAttacker.sendMessage(ThePlugin.c2 + "Du kan ikke skade andre når du har skrudd av pvp");
				}
				else if (id == 0) {
					return;
				}
				if (!places.isPvP(id)) {
					event.setCancelled(true);
					playerAttacker.sendMessage(pvpDenyString);
				}
			}
		}

		if (id == 0) {
			return;
		}
		
		if (attacker instanceof Player && victim instanceof Animals) {
			playerAttacker = (Player) attacker;
			if (!places.hasAccess(playerAttacker, victimLoc)) {
				event.setCancelled(true);
				playerAttacker.sendMessage(denyString);
			}
		}
		
		if (attacker instanceof Projectile && victim instanceof Animals) {
			LivingEntity shooter = ((Projectile) attacker).getShooter();
			if (shooter instanceof Player) {
				playerAttacker = (Player) shooter;
				if (!places.hasAccess(playerAttacker, victimLoc)) {
					event.setCancelled(true);
					playerAttacker.sendMessage(denyString);
				}
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
		int id = places.getIDWithCoords(event.getEntity().getLocation());
		
		switch (event.getSpawnReason()) {
		case BUILD_WITHER:
			if (event.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
				event.setCancelled(true);
			}
		break;
		/*case BREEDING:
			if (false) {
				event.setCancelled(true);
			}
		break;*/
		default:
		}

		if (id == 0) {
			return;
		}
		if (event.getEntity() instanceof Monster) {
			event.setCancelled(!places.isMonsters(id));
		}
		
		if (event.getEntity() instanceof Animals) {
			event.setCancelled(!places.isAnimals(id));
		}
	}
}
