package net.kiwz.ThePlugin.utils;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;

public class ChunkLag {
	private World world;
	private int x;
	private int z;
	private int amount;
	private String type;

	public void sendChunks(CommandSender sender) {
		sender.sendMessage(getMsg("", "4"));
	}
	
	public void sendChunks(CommandSender sender, String typeString) {
		sender.sendMessage(getMsg(typeString, "4"));
	}
	
	public void sendChunks(CommandSender sender, String typeString, String amountString) {
		sender.sendMessage(getMsg(typeString, amountString));
	}
	
	private String[] getMsg(String typeString, String amountString) {
		TreeMap<Integer, ChunkLag> chunks = getChunks(typeString, amountString);
		String[] s = new String[chunks.size() + 1];
		s[0] = Color.WARNING + "------  Liste over ting som er loada  ------";
		for (int key : chunks.keySet()) {
			String world = chunks.get(key).world.getName();
			int x = chunks.get(key).x;
			int z = chunks.get(key).z;
			int amount = chunks.get(key).amount;
			String type = chunks.get(key).type;
			s[key + 1] = Color.INFO + "Det er " + amount + " " + type + " i " + world + "-chunk: x" + x + ", z" + z;
		}
		return s;
	}
	
	private TreeMap<Integer, ChunkLag> getChunks(String typeString, String amountString) {
		int amount = 4;
		try {
			amount = Integer.parseInt(amountString);
		}
		catch (NumberFormatException e) {
		}
		if (amount < 1) {
			amount = 1;
		}
		else if (amount > 8) {
			amount = 8;
		}
		
		if (typeString.equalsIgnoreCase("dyr")) {
			return getMostLaggyChunks(buildAnimals(), amount);
		}
		else if (typeString.equalsIgnoreCase("monstre")) {
			return getMostLaggyChunks(buildMonsters(), amount);
		}
		else if (typeString.equalsIgnoreCase("items")) {
			return getMostLaggyChunks(buildItems(), amount);
		}
		else {
			return getMostLaggyChunks(buildAll(), amount);
		}
	}
	
	private TreeMap<Integer, ChunkLag> getMostLaggyChunks(TreeMap<Integer, ChunkLag> chunks, int amount) {
		TreeMap<Integer, Integer> unsorted = new TreeMap<Integer, Integer>();
		TreeMap<Integer, ChunkLag> sortedChunks = new TreeMap<Integer, ChunkLag>();
		for (int key : chunks.keySet()) {
			unsorted.put(key, chunks.get(key).amount);
		}
		
		MyComparator comparator = new MyComparator(unsorted);
        TreeMap<Integer, Integer> sorted = new TreeMap<Integer, Integer>(comparator);
        sorted.putAll(unsorted);
        unsorted.clear();
        
        int i = 0;
    	for (int key : sorted.keySet()) {
        	ChunkLag value = new ChunkLag();
        	value.world = chunks.get(key).world;
        	value.x = chunks.get(key).x;
        	value.z = chunks.get(key).z;
        	value.amount = chunks.get(key).amount;
        	value.type = chunks.get(key).type;
        	sortedChunks.put(i, value);
        	i++;
        	if (i == amount) {
        		break;
        	}
        }
		return sortedChunks;
	}
	
	private TreeMap<Integer, ChunkLag> buildAnimals() {
		TreeMap<Integer, ChunkLag> chunks = new TreeMap<Integer, ChunkLag>();
		int key = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				int amount = 0;
				for (Entity ent : chunk.getEntities()) {
					if (ent instanceof Animals) {
						amount++;
					}
				}
				ChunkLag value = new ChunkLag();
				value.world = world;
				value.x = chunk.getX();
				value.z = chunk.getZ();
				value.amount = amount;
				value.type = "dyr";
				chunks.put(key, value);
				key++;
			}
		}
		return chunks;
	}
	
	private TreeMap<Integer, ChunkLag> buildMonsters() {
		TreeMap<Integer, ChunkLag> chunks = new TreeMap<Integer, ChunkLag>();
		int key = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				int amount = 0;
				for (Entity ent : chunk.getEntities()) {
					if (ent instanceof Monster) {
						amount++;
					}
				}
				ChunkLag value = new ChunkLag();
				value.world = world;
				value.x = chunk.getX();
				value.z = chunk.getZ();
				value.amount = amount;
				value.type = "monstre";
				chunks.put(key, value);
				key++;
			}
		}
		return chunks;
	}
	
	private TreeMap<Integer, ChunkLag> buildItems() {
		TreeMap<Integer, ChunkLag> chunks = new TreeMap<Integer, ChunkLag>();
		int key = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				int amount = 0;
				for (Entity e : chunk.getEntities()) {
					if (e instanceof Item) {
						amount++;
					}
				}
				ChunkLag value = new ChunkLag();
				value.world = world;
				value.x = chunk.getX();
				value.z = chunk.getZ();
				value.amount = amount;
				value.type = "items";
				chunks.put(key, value);
				key++;
			}
		}
		return chunks;
	}
	
	private TreeMap<Integer, ChunkLag> buildAll() {
		TreeMap<Integer, ChunkLag> chunks = new TreeMap<Integer, ChunkLag>();
		int key = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				int amount = 0;
				for (@SuppressWarnings("unused") Entity e : chunk.getEntities()) {
					amount++;
				}
				ChunkLag value = new ChunkLag();
				value.world = world;
				value.x = chunk.getX();
				value.z = chunk.getZ();
				value.amount = amount;
				value.type = "ting";
				chunks.put(key, value);
				key++;
			}
		}
		return chunks;
	}
}
