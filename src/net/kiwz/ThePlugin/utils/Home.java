package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Home {
	private static HashMap<String, Home> homes = new HashMap<String, Home>();
	private static HashMap<String, Home> removedHomes = new HashMap<String, Home>();
	
	private String key;
	private String uuid;
	private Location loc;
	
	public Home(MyPlayer myPlayer) {
		this.key = myPlayer.getUUID() + " " + myPlayer.getOnlinePlayer().getWorld().getName();
		this.uuid = myPlayer.getUUID();
		this.loc = myPlayer.getOnlinePlayer().getLocation();
	}
	
	public Home(String uuid, String worldName, String coords, String direction) {
		World world = Bukkit.getServer().getWorld(worldName);
		Location loc = Util.parseLocation(world, coords, direction);
		
		this.key = uuid + " " + worldName;
		this.uuid = uuid;
		this.loc = loc;
	}
	
	public static Home getHome(MyPlayer myPlayer) {
		return homes.get(myPlayer.getUUID() + " " + myPlayer.getOnlinePlayer().getWorld().getName());
	}
	
	public static Home getHome(MyPlayer myPlayer, World world) {
		return homes.get(myPlayer.getUUID() + " " + world.getName());
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
		if (this.loc.getWorld() == null) return;
		homes.put(this.key, this);
	}
}
