package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Home {
	private static HashMap<String, Home> homes = new HashMap<String, Home>();
	private static HashMap<String, Home> removedHomes = new HashMap<String, Home>();
	
	private String key;
	private String uuid;
	private Location loc;
	
	public Home(Player player) {
		this.key = player.getUniqueId().toString().replace("-", "") + " " + player.getWorld().getName();
		this.uuid = player.getUniqueId().toString().replace("-", "");
		this.loc = player.getLocation();
	}
	
	public Home(String uuid, String worldName, String coords, String direction) {
		World world = Bukkit.getServer().getWorld(worldName);
		Location loc = Util.parseLocation(world, coords, direction);
		
		this.key = uuid + " " + loc.getWorld().getName();
		this.uuid = uuid;
		this.loc = loc;
	}
	
	public static Home getHome(Player player) {
		return homes.get(player.getUniqueId().toString().replace("-", "") + " " + player.getWorld().getName());
	}
	
	public static Home getHome(Player player, World world) {
		return homes.get(player.getUniqueId().toString().replace("-", "") + " " + world.getName());
	}
	
	public static List<Home> getHomes() {
		List<Home> list = new ArrayList<Home>();
		for (String key : homes.keySet()) {
			list.add(homes.get(key));
		}
		return list;
	}
	
	public static List<Home> getRemovedHomes() {
		List<Home> list = new ArrayList<Home>();
		for (String key : removedHomes.keySet()) {
			list.add(removedHomes.get(key));
		}
		return list;
	}
	
	public static void clearRemovedHomes() {
		removedHomes.clear();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public void delete() {
		removedHomes.put(this.key, homes.remove(this.key));
	}
	
	public void save() {
		homes.put(this.key, this);
	}
}
