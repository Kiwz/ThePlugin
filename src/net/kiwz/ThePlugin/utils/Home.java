package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Home {
	private static HashMap<String, Home> homes = new HashMap<String, Home>();

	private String uuid;
	private String worldName;
	private String key;
	private Location loc;
	private boolean changed;
	private boolean loaded;
	private boolean removed;
	
	public Home(MyPlayer myPlayer) {
		this.uuid = myPlayer.getUUID();
		this.worldName = myPlayer.getOnlinePlayer().getWorld().getName();
		this.key = getUUID() + " " + getWorldName();
		this.loc = myPlayer.getOnlinePlayer().getLocation();
		this.changed = true;
		this.loaded = true;
		this.removed = false;
	}
	
	public Home(String uuid, String worldName, String coords, String direction) {
		World world = Bukkit.getServer().getWorld(worldName);
		Location loc = Util.parseLocation(world, coords, direction);
		
		this.uuid = uuid;
		this.worldName = worldName;
		this.key = getUUID() + " " + getWorldName();
		this.loc = loc;
		this.changed = false;
		this.loaded = true;
		this.removed = false;
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
	
	public String getWorldName() {
		return this.worldName;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public boolean isChanged() {
		return this.changed;
	}
	
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	public boolean isRemoved() {
		return this.removed;
	}
	
	public void remove() {
		homes.remove(this.key);
	}
	
	public void save() {
		if (getLocation() == null) setLoaded(false);
		homes.put(this.key, this);
	}
}
