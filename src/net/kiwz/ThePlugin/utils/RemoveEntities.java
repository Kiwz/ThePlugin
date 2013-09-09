package net.kiwz.ThePlugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class RemoveEntities {
	
	public String killAnimals() {
		int i = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity instanceof Animals) {
					entity.remove();
					i++;
				}
			}
		}
		return i + " dyr drept";
	}
	
	public String killAnimals(Player player, int distance) {
		int i = 0;
		for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
			if (entity instanceof Animals) {
				int d = (int) entity.getLocation().distance(player.getLocation());
				if (d < distance) {
					entity.remove();
					i++;
				}
			}
		}
		return i + " dyr drept";
	}
	
	public void killAnimals(World world) {
		for (Entity entity : world.getEntities()) {
			if (entity instanceof Animals) {
				entity.remove();
			}
		}
	}
	
	public String killMonsters() {
		int i = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity instanceof Monster) {
					entity.remove();
					i++;
				}
			}
		}
		if (i == 1) {
			return i + " monster drept";
		}
		else {
			return i + " monstre drept";
		}
	}
	
	public String killMonsters(Player player, int distance) {
		int i = 0;
		for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
			if (entity instanceof Monster) {
				int d = (int) entity.getLocation().distance(player.getLocation());
				if (d < distance) {
					entity.remove();
					i++;
				}
			}
		}
		if (i == 1) {
			return i + " monster drept";
		}
		else {
			return i + " monstre drept";
		}
	}
	
	public void killMonsters(World world) {
		for (Entity entity : world.getEntities()) {
			if (entity instanceof Monster) {
				entity.remove();
			}
		}
	}
	
	public String killDrops() {
		int i = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getType().equals(EntityType.DROPPED_ITEM)) {
						entity.remove();
						i++;
				}
			}
		}
		if (i == 1) {
			return i + " entity fjernet";
		}
		else {
			return i + " entities fjernet";
		}
	}
	
	public String killDrops(Player player, int distance) {
		int i = 0;
		for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
			if (entity.getType().equals(EntityType.DROPPED_ITEM)) {
				int d = (int) entity.getLocation().distance(player.getLocation());
				if (d < distance) {
					entity.remove();
					i++;
				}
			}
		}
		if (i == 1) {
			return i + " entity fjernet";
		}
		else {
			return i + " entities fjernet";
		}
	}
}
