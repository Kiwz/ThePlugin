package net.kiwz.ThePlugin.listeners;

import java.util.List;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.Color;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.MyWorld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.projectiles.ProjectileSource;

public class EntityListener implements Listener {
	private String denyString = Color.WARNING + "Du har ingen tilgang her";
	private String pvpDenyString = Color.WARNING + "PvP er deaktivert på denne plassen";
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		
		if (event.getCause() == RemoveCause.ENTITY) {
			if (event.getRemover() instanceof Player) {
				Player player = (Player) event.getRemover();
				Location loc = event.getEntity().getLocation();
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
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityInteract(EntityInteractEvent event) {
		Block block = event.getBlock();

		if (!MyWorld.getWorld(block.getWorld()).getTrample()) {
			if ((block.getType() == Material.SOIL) || (block.getType() == Material.CROPS)) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHangingPlace(HangingPlaceEvent event) {
		
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity attacker = event.getDamager();
		Player playerAttacker;
		Entity victim = event.getEntity();
		Player playerVictim;
		Location victimLoc = victim.getLocation();
		Place place = Place.getPlace(victimLoc);
		
		if (attacker instanceof Player && victim instanceof Player) {
			playerAttacker = (Player) attacker;
			playerVictim = (Player) victim;
			if (!MyPlayer.getPlayer(playerVictim).isPvp()) {
				event.setCancelled(true);
				playerAttacker.sendMessage(Color.WARNING + "Du kan ikke skade en Admin");
			} else if (!MyPlayer.getPlayer(playerAttacker).isPvp()) {
				event.setCancelled(true);
				playerAttacker.sendMessage(Color.WARNING + "Du kan ikke skade andre når du har skrudd av pvp");
			} else if (place != null) {
				if (!place.getPvP()) {
					event.setCancelled(true);
					playerAttacker.sendMessage(pvpDenyString);
				}
			}
			
			if (!event.isCancelled()) {
				final MyPlayer myVictim = MyPlayer.getPlayer(playerVictim);
				if (myVictim.isDamaged()) {
					Bukkit.getServer().getScheduler().cancelTask(myVictim.getDamagedTaskId());
					myVictim.setDamaged(false, -1);
				}
				int taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(), new Runnable() {
					public void run() {
						myVictim.setDamaged(false, -1);;
					}
				}, 100);
				myVictim.setDamaged(true, taskId);
			}
		}
		
		if (attacker instanceof Projectile && victim instanceof Player) {
			ProjectileSource shooter = ((Projectile) attacker).getShooter();
			if (shooter instanceof Player) {
				playerAttacker = (Player) shooter;
				playerVictim = (Player) victim;
				if (!MyPlayer.getPlayer(playerVictim).isPvp()) {
					event.setCancelled(true);
					playerAttacker.sendMessage(Color.WARNING + "Du kan ikke skade en Admin");
				} else if (!MyPlayer.getPlayer(playerAttacker).isPvp()) {
					event.setCancelled(true);
					playerAttacker.sendMessage(Color.WARNING + "Du kan ikke skade andre når du har skrudd av pvp");
				} else if (place != null) {
					if (!place.getPvP()) {
						event.setCancelled(true);
						playerAttacker.sendMessage(pvpDenyString);
					}
				}
				
				if (!event.isCancelled()) {
					final MyPlayer myVictim = MyPlayer.getPlayer(playerVictim);
					if (myVictim.isDamaged()) {
						Bukkit.getServer().getScheduler().cancelTask(myVictim.getDamagedTaskId());
						myVictim.setDamaged(false, -1);
					}
					int taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThePlugin.getPlugin(), new Runnable() {
						public void run() {
							myVictim.setDamaged(false, -1);;
						}
					}, 100);
					myVictim.setDamaged(true, taskId);
				}
			}
		}
		
		if (place != null) {
			if (attacker instanceof Player && victim instanceof Ageable) {
				playerAttacker = (Player) attacker;
				MyPlayer myPlayer = MyPlayer.getPlayer(playerAttacker);
				if (!place.hasAccess(myPlayer)) {
					event.setCancelled(true);
					playerAttacker.sendMessage(denyString);
				}
			}
			
			if (attacker instanceof Player && victim instanceof Hanging) {
				playerAttacker = (Player) attacker;
				MyPlayer myPlayer = MyPlayer.getPlayer(playerAttacker);
				if (!place.hasAccess(myPlayer)) {
					event.setCancelled(true);
					playerAttacker.sendMessage(denyString);
				}
			}
			
			if (attacker instanceof Projectile && victim instanceof Ageable) {
				ProjectileSource shooter = ((Projectile) attacker).getShooter();
				if (shooter instanceof Player) {
					playerAttacker = (Player) shooter;
					MyPlayer myPlayer = MyPlayer.getPlayer(playerAttacker);
					if (!place.hasAccess(myPlayer)) {
						event.setCancelled(true);
						playerAttacker.sendMessage(denyString);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (!MyWorld.getWorld(event.getLocation().getWorld()).getExplosions()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Place place = Place.getPlace(event.getEntity().getLocation());
		
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
		
		if (place != null) {
			if (event.getEntity() instanceof Monster) {
				event.setCancelled(!place.getMonsters());
			} else if (event.getEntity() instanceof Ageable) {
				event.setCancelled(!place.getAnimals());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPotionSplashEvent(PotionSplashEvent event) {
		List<LivingEntity> affectedEntities = (List<LivingEntity>) event.getAffectedEntities();
		ThrownPotion potion = event.getPotion();
		ProjectileSource attacker = potion.getShooter();
		/*for (LivingEntity victim : affectedEntities) {
			if (attacker != victim) {
				event.setIntensity(victim, -1.0);
			}
		}*/
	}
}
