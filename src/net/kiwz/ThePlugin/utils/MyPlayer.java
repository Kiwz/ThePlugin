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
	private boolean banned;
	private long banTime;
	private long banExpire;
	private String banReason;
	private String bannedBy;
	private MyPlayer replayTo;
	private boolean spy;
	private boolean pvp;
	private Set<MyPlayer> ignored;
	private int xp;
	private boolean damaged;
	private int damagedTaskId;
	private MyPlayer woolChestOwner;
	private boolean changed;
	private boolean loaded;
	private boolean removed;
	
	public MyPlayer(Player player) {
		this.uuid = player.getUniqueId().toString().replace("-", "");
		this.name = player.getName();
		this.ip = player.getAddress().getAddress().toString().replace("/", "");
		this.lastPlayed = System.currentTimeMillis() / 1000;
		this.timePlayed = 0;
		this.muted = false;
		this.banned = false;
		this.banTime = 0;
		this.banExpire = 0;
		this.banReason = "";
		this.bannedBy = "";
		this.replayTo = null;
		this.spy = false;
		this.pvp = true;
		this.ignored = new HashSet<MyPlayer>();
		this.xp = 0;
		this.damaged = false;
		this.damagedTaskId = -1;
		this.woolChestOwner = this;
		this.changed = true;
		this.loaded = true;
		this.removed = false;
	}
	
	public MyPlayer(String uuid, String name, String ip, long lastPlayed, long timePlayed, boolean muted,
			boolean banned, long banTime, long banExpire, String banReason, String bannedBy) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.lastPlayed = lastPlayed;
		this.timePlayed = timePlayed;
		this.muted = muted;
		this.banned = banned;
		this.banTime = banTime;
		this.banExpire = banExpire;
		this.banReason = banReason;
		this.bannedBy = bannedBy;
		this.replayTo = null;
		this.spy = false;
		this.pvp = true;
		this.ignored = new HashSet<MyPlayer>();
		this.xp = 0;
		this.damaged = false;
		this.damagedTaskId = -1;
		this.woolChestOwner = this;
		this.changed = false;
		this.loaded = true;
		this.removed = false;
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
		setChanged(true);
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
		setChanged(true);
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public void setLastPlayed(long lastPlayed) {
		this.lastPlayed = lastPlayed;
		setChanged(true);
	}
	
	public long getLastPlayed() {
		return this.lastPlayed;
	}
	
	public void setTimePlayed(long timePlayed) {
		this.timePlayed = timePlayed;
		setChanged(true);
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
		setChanged(true);
	}
	
	public boolean isMuted() {
		return this.muted;
	}
	
	public void setBanned(boolean banned, long banExpire, String banReason, String bannedBy) {
		this.banned = banned;
		this.banTime = System.currentTimeMillis() / 1000;
		this.banExpire = banExpire;
		this.banReason = banReason;
		this.bannedBy = bannedBy;
		setChanged(true);
		MyServer.getMyServer().setBannedPlayer(this, banned);
	}
	
	public boolean isBanned() {
		return this.banned;
	}
	
	public long getBanTime() {
		return this.banTime;
	}
	
	public long getBanExpire() {
		return this.banExpire;
	}
	
	public String getBanReason() {
		return this.banReason;
	}
	
	public String getBannedBy() {
		return this.bannedBy;
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
	
	public void setWoolChestOwner(MyPlayer myPlayer) {
		this.woolChestOwner = myPlayer;
	}
	
	public MyPlayer getWoolChestOwner() {
		return this.woolChestOwner;
	}
	
	public boolean isOld() {
		long dayes = 30;
		long timeSinceLastPlayed = System.currentTimeMillis() / 1000 - this.lastPlayed;
		if (timeSinceLastPlayed < dayes * 24 * 60 * 60) return false;
		return true;
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
		players.remove(this.uuid);
	}
	
	public void save() {
		players.put(this.uuid, this);
	}
	
	public boolean canBeCharged(int amount) {
		return this.canBeCharged(Material.GOLD_INGOT, amount);
	}

	public boolean canBeCharged(Material material, int amount) {
		PlayerInventory inventory = getOfflinePlayer().getInventory();
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
			if (getOnlinePlayer() != null) {
				getOnlinePlayer().sendMessage(Color.WARNING + "Denne handlingen kostet "
						+ Color.VARIABLE + amount + " " + material.toString());
			}
			return true;
		}
		return false;
	}
	
	public boolean isAdmin() {
		if (Config.getConfig().getAdmins().contains(this.name)) return true;
		if (this.getOfflinePlayer().isOp()) return true;
        return false;
	}
	
	public Player getOnlinePlayer() {
		return Bukkit.getServer().getPlayer(this.name);
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
