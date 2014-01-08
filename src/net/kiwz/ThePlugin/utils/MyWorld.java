package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;

public class MyWorld {
	private static HashMap<String, MyWorld> worlds = new HashMap<String, MyWorld>();
	private static HashMap<String, MyWorld> removedWorlds = new HashMap<String, MyWorld>();
	
	private String name;
	private Environment env;
	private WorldType type;
	private long seed;
	private Location spawn;
	private boolean keepSpawn;
	private boolean pvp;
	private boolean monsters;
	private boolean animals;
	private boolean monsterGrief;
	private boolean fireSpread;
	private boolean claimable;
	private boolean explosions;
	private boolean trample;
	private int border;
	private String spawnCoords;
	private String spawnDirection;
	
	public MyWorld(String name) {
		this(name, "");
	}
	
	public MyWorld(String name, String env) {
		this(name, "", "");
	}
	
	public MyWorld(String name, String env, String type) {
		this(name, "", "", "");
	}
	
	public MyWorld(String name, String env, String type, String seed) {
		this.name = name.toLowerCase();
		this.env = getEnvironment(env);
		this.type = getWorldType(type);
		this.seed = getSeed(seed);
		this.spawn = null;
		this.keepSpawn = true;
		this.pvp = false;
		this.monsters = true;
		this.animals = true;
		this.monsterGrief = false;
		this.fireSpread = false;
		this.claimable = false;
		this.explosions = false;
		this.trample = false;
		this.border = 1000;
	}
	
	public MyWorld(World world) {
		this.name = world.getName();
		this.env = world.getEnvironment();
		this.type = world.getWorldType();
		this.seed = world.getSeed();
		this.spawn = world.getSpawnLocation();
		this.keepSpawn = world.getKeepSpawnInMemory();
		this.pvp = world.getPVP();
		this.monsters = world.getAllowMonsters();
		this.animals = world.getAllowAnimals();
		this.monsterGrief = false;
		this.fireSpread = false;
		this.claimable = false;
		this.explosions = false;
		this.trample = false;
		this.border = 1000;
	}
	
	public MyWorld(String name, String env, String type, long seed, String coords, String direction, boolean keepSpawn, boolean pvp,
			boolean monsters, boolean animals, boolean monsterGrief, boolean fireSpread, boolean claimable, boolean explosions, boolean trample, int border) {
		this.name = name;
		this.env = getEnvironment(env);
		this.type = getWorldType(type);
		this.seed = seed;
		this.spawn = null;
		this.keepSpawn = keepSpawn;
		this.pvp = pvp;
		this.monsters = monsters;
		this.animals = animals;
		this.monsterGrief = monsterGrief;
		this.fireSpread = fireSpread;
		this.claimable = claimable;
		this.explosions = explosions;
		this.trample = trample;
		this.border = border;
		this.spawnCoords = coords;
		this.spawnDirection = direction;
	}
	
	public static MyWorld getWorld(String name) {
		if (worlds.get(name.toLowerCase()) != null) return worlds.get(name.toLowerCase());
		for (String key : worlds.keySet()) if (key.startsWith(name.toLowerCase())) return worlds.get(key);
		return null;
	}
	
	public static MyWorld getWorld(World world) {
		return worlds.get(world.getName());
	}
	
	public static List<MyWorld> getWorlds() {
		List<MyWorld> list = new ArrayList<MyWorld>();
		for (String key : worlds.keySet()) {
			list.add(worlds.get(key));
		}
		return list;
	}
	
	public static List<MyWorld> getRemovedWorlds() {
		List<MyWorld> list = new ArrayList<MyWorld>();
		for (String key : removedWorlds.keySet()) {
			list.add(removedWorlds.get(key));
		}
		return list;
	}
	
	public static void clearRemovedWorlds() {
		removedWorlds.clear();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Environment getEnv() {
		return this.env;
	}
	
	public WorldType getType() {
		return this.type;
	}
	
	public long getSeed() {
		return this.seed;
	}
	
	public void setSpawn(Location loc) {
		this.spawn = loc;
	}
	
	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setKeepSpawn(boolean keepSpawn) {
		this.keepSpawn = keepSpawn;
	}
	
	public boolean getKeepSpawn() {
		return this.keepSpawn;
	}
	
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}
	
	public boolean getPvp() {
		return this.pvp;
	}
	
	public void setMonsters(boolean monsters) {
		this.monsters = monsters;
	}
	
	public boolean getMonsters() {
		return this.monsters;
	}
	
	public void setAnimals(boolean animals) {
		this.animals = animals;
	}
	
	public boolean getAnimals() {
		return this.animals;
	}
	
	public void setMonsterGrief(boolean monsterGrief) {
		this.monsterGrief = monsterGrief;
	}
	
	public boolean getMonsterGrief() {
		return this.monsterGrief;
	}
	
	public void setFireSpread(boolean fireSpread) {
		this.fireSpread = fireSpread;
	}
	
	public boolean getFireSpread() {
		return this.fireSpread;
	}
	
	public void setClaimable(boolean claimable) {
		this.claimable = claimable;
	}
	
	public boolean getClaimable() {
		return this.claimable;
	}
	
	public void setExplosions(boolean explosions) {
		this.explosions = explosions;
	}
	
	public boolean getExplosions() {
		return this.explosions;
	}
	
	public void setTrample(boolean trample) {
		this.trample = trample;
	}
	
	public boolean getTrample() {
		return this.trample;
	}
	
	public void setBorder(int border) {
		this.border = border;
	}
	
	public int getBorder() {
		return this.border;
	}
	
	public String getSpawnCoords() {
		return this.spawnCoords;
	}
	
	public String getSpawnDirection() {
		return this.spawnDirection;
	}
	
	public boolean reachedBorder(Location loc) {
		if (this.border < loc.getBlockX() || -this.border + 1 > loc.getBlockX()
				|| this.border < loc.getBlockZ() || -this.border + 1 > loc.getBlockZ()) {
			return true;
		}
		return false;
	}
	
	public World getBukkitWorld() {
		return Bukkit.getServer().getWorld(this.name);
	}
	
	public boolean delete() {
		if (!MultiWorld.unloadWorld(getBukkitWorld())) return false;
		removedWorlds.put(this.name, worlds.remove(this.name));
		return true;
	}
	
	public boolean save() {
		if (worlds.containsKey(this.name)) return false;
		worlds.put(name, this);
		return true;
	}
	
	private Environment getEnvironment(String envString) {
		Environment env = Environment.NORMAL;
		if (envString.equalsIgnoreCase("NETHER")) env = Environment.NETHER;
		if (envString.equalsIgnoreCase("THE_END")) env = Environment.THE_END;
		return env;
	}
	
	private WorldType getWorldType(String typeString) {
		WorldType type = WorldType.NORMAL;
		if (typeString.equalsIgnoreCase("AMPLIFIED")) type = WorldType.AMPLIFIED;
		if (typeString.equalsIgnoreCase("FLAT")) type = WorldType.FLAT;
		if (typeString.equalsIgnoreCase("LARGE_BIOMES")) type = WorldType.LARGE_BIOMES;
		if (typeString.equalsIgnoreCase("VERSION_1_1")) type = WorldType.VERSION_1_1;
		return type;
	}
	
	private long getSeed(String seedString) {
		if (seedString == "") seedString = "a" + System.currentTimeMillis() + "z";
		long seed = 0;
		try {
			seed = Long.parseLong(seedString);
		} catch (NumberFormatException e) {
			seed = (long) seedString.hashCode();
		}
		return seed;
	}
	
	
}
