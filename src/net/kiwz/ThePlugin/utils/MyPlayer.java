package net.kiwz.ThePlugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.MinecraftServer;
import net.minecraft.server.v1_7_R1.PlayerInteractManager;
import net.minecraft.server.v1_7_R1.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MyPlayer {
	private static HashMap<String, MyPlayer> players = new HashMap<String, MyPlayer>();
	
	private String uuid;
	private String name;
	private String ip;
	private long lastPlayed;
	private long timePlayed;
	private boolean muted;
	private MyPlayer replayTo;
	private boolean spy;
	private boolean pvp;
	private Set<MyPlayer> ignored;
	private int xp;
	private boolean damaged;
	private int damagedTaskId;
	
	public MyPlayer(Player player) {
		this.uuid = player.getUniqueId().toString().replace("-", "");
		this.name = player.getName();
		this.ip = player.getAddress().getAddress().toString().replace("/", "");
		this.lastPlayed = System.currentTimeMillis() / 1000;
		this.timePlayed = 0;
		this.muted = false;
		this.replayTo = null;
		this.spy = false;
		this.pvp = true;
		this.ignored = new HashSet<MyPlayer>();
		this.xp = 0;
		this.damaged = false;
		this.damagedTaskId = -1;
	}
	
	public MyPlayer(String uuid, String name, String ip, long lastPlayed, long timePlayed, boolean muted) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.lastPlayed = lastPlayed;
		this.timePlayed = timePlayed;
		this.muted = muted;
		this.replayTo = null;
		this.spy = false;
		this.pvp = true;
		this.ignored = new HashSet<MyPlayer>();
		this.xp = 0;
		this.damaged = false;
		this.damagedTaskId = -1;
	}
	
	public static MyPlayer getPlayerById(String id) {
		if (players.containsKey(id)) return players.get(id);
		return null;
	}
	
	public static MyPlayer getPlayer(Player player) {
		String id = player.getUniqueId().toString().replace("-", "");
		if (players.containsKey(id)) return players.get(id);
		return null;
	}
	
	public static MyPlayer getPlayer(CommandSender sender) {
		for (String key : players.keySet()) {
			if (players.get(key).name.equals(sender.getName())) return players.get(key);
		}
		return null;
	}
	
	public static MyPlayer getPlayer(String name) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) return getPlayer(player);
		}
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().toLowerCase().startsWith(name.toLowerCase())) return getPlayer(player);
		}
		for (String key : players.keySet()) {
			if (players.get(key).name.equalsIgnoreCase(name)) return players.get(key);
		}
		for (String key : players.keySet()) {
			if (players.get(key).name.toLowerCase().startsWith(name.toLowerCase())) return players.get(key);
		}
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
	
	public static String getColorName(MyPlayer myPlayer) {
		if (myPlayer == null) return Color.SERVER + "SERVER" + Color.INFO;
		else return myPlayer.getColorName();
	}
	
	private String getColorName() {
		if (this.isAdmin()) return Color.ADMIN + this.name + Color.INFO;
		return Color.PLAYER + this.name + Color.INFO;
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
	
	public void setReplayTo(MyPlayer replayTo) {
		this.replayTo = replayTo;
	}
	
	public MyPlayer getReplayTo() {
		return this.replayTo;
	}
	
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
	public boolean isMuted() {
		return this.muted;
	}
	
	public void setSpy(boolean spy) {
		this.spy = spy;
	}
	
	public boolean isSpy() {
		return this.spy;
	}
	
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}
	
	public boolean isPvp() {
		return this.pvp;
	}
	
	public boolean addIgnored(MyPlayer myIgnored) {
		return this.ignored.add(myIgnored);
	}
	
	public boolean remIgnored(MyPlayer myIgnored) {
		return this.ignored.remove(myIgnored);
	}
	
	public Set<MyPlayer> getIgnored() {
		return this.ignored;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public int getXP() {
		return this.xp;
	}
	
	public void setDamaged(boolean damaged, int taskId) {
		this.damaged = damaged;
		this.damagedTaskId = taskId;
	}
	
	public boolean isDamaged() {
		return this.damaged;
	}
	
	public int getDamagedTaskId() {
		return this.damagedTaskId;
	}
	
	public boolean isAdmin() {
		if (Config.getAdmins().contains(this.name)) return true;
		if (this.getOfflinePlayer().isOp()) return true;
        return false;
	}
	
	public Player getOnlinePlayer() {
		return Bukkit.getServer().getPlayer(this.name);
	}
	
	public boolean save() {
		if (players.containsKey(this.uuid)) return false;
		players.put(this.uuid, this);
		return true;
	}
	
	public boolean canBeCharged(int amount) {
		return this.canBeCharged(Material.GOLD_INGOT, amount);
	}

	public boolean canBeCharged(Material material, int amount) {
		PlayerInventory inventory = this.getOfflinePlayer().getInventory();
		if (inventory.containsAtLeast(new ItemStack(material), amount)) return true;
		return false;
	}
	
	public boolean charge(int amount) {
		return this.charge(Material.GOLD_INGOT, amount);
	}

	public boolean charge(Material material, int amount) {
		PlayerInventory inventory = this.getOfflinePlayer().getInventory();
		if (inventory.containsAtLeast(new ItemStack(material), amount)) {
			inventory.removeItem(new ItemStack(material, amount));
			if (this.getOnlinePlayer() != null) {
				this.getOnlinePlayer().sendMessage(Color.WARNING + "Denne handlingen kostet "
						+ Color.VARIABLE + amount + " " + material.toString());
			}
			return true;
		}
		return false;
	}
	
    public Player getOfflinePlayer() {
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
}
