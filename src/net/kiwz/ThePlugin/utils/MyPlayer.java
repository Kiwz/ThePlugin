package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.MinecraftServer;
import net.minecraft.server.v1_7_R1.PlayerInteractManager;
import net.minecraft.server.v1_7_R1.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R1.CraftServer;
import org.bukkit.entity.Player;

public class MyPlayer {
	private static HashMap<String, MyPlayer> players = new HashMap<String, MyPlayer>();
	
	private String uuid;
	private String name;
	private String ip;
	private long lastPlayed;
	private long timePlayed;
	private boolean muted;
	
	public MyPlayer(Player player) {
		this.uuid = player.getUniqueId().toString().replace("-", "");
		this.name = player.getName();
		this.ip = player.getAddress().getAddress().toString().replace("/", "");
		this.lastPlayed = System.currentTimeMillis() / 1000;
		this.timePlayed = 0;
		this.muted = false;
	}
	
	public MyPlayer(String uuid, String name, String ip, long lastPlayed, long timePlayed, boolean muted) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.lastPlayed = lastPlayed;
		this.timePlayed = timePlayed;
		this.muted = muted;
	}
	
	public static MyPlayer getPlayer(String name) {
		for (String key : players.keySet()) {
			if (players.get(key).name.equalsIgnoreCase(name)) return players.get(key);
		}
		for (String key : players.keySet()) {
			if (players.get(key).name.toLowerCase().startsWith(name.toLowerCase())) return players.get(key);
		}
		return null;
	}
	
	public static MyPlayer getPlayer(Player player) {
		String id = player.getUniqueId().toString().replace("-", "");
		if (players.containsKey(id)) return players.get(id);
		return null;
	}
	
	public static MyPlayer getPlayerById(String id) {
		if (players.containsKey(id)) return players.get(id);
		return null;
	}
	
	public static List<MyPlayer> getPlayers() {
		List<MyPlayer> list = new ArrayList<MyPlayer>();
		for (String key : players.keySet()) {
			list.add(players.get(key));
		}
		return list;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public void setLastPlayed(long lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
	
	public long getLastPlayed() {
		return this.lastPlayed;
	}
	
	public void setTimePlayed(long timePlayed) {
		this.timePlayed = timePlayed;
	}
	
	public long getTimePlayed() {
		return this.timePlayed;
	}
	
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
	public boolean isMuted() {
		return this.muted;
	}
	
	public boolean save() {
		if (players.containsKey(this.uuid)) return false;
		players.put(this.uuid, this);
		return true;
	}
	
    public Player getBukkitPlayer() {
        if (Bukkit.getServer().getPlayer(this.name) != null) {
        	return Bukkit.getServer().getPlayer(this.name);
        }

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = server.getWorldServer(0);
        GameProfile gameProfile = new GameProfile(this.uuid, this.name);
        PlayerInteractManager interactManager = new PlayerInteractManager(worldServer);
        EntityPlayer entity = new EntityPlayer(server, worldServer, gameProfile, interactManager);
        
        Player player = null;
        if (entity != null) {
        	player = (Player) entity.getBukkitEntity();
        	if (player != null) {
        		player.loadData();
        	}
        }
        return player;
    }
	
	public static Player getOnlinePlayer(String playerName) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().toLowerCase().startsWith(playerName.toLowerCase())) {
				return Bukkit.getServer().getPlayer(player.getName());
			}
		}
		return null;
	}
    
    public static void broadcastMsg(String string) {
    	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
    		player.sendMessage(string);
    	}
    }
}
