package net.kiwz.ThePlugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import net.kiwz.ThePlugin.utils.Home;
import net.kiwz.ThePlugin.utils.MultiWorld;
import net.kiwz.ThePlugin.utils.MyPlayer;
import net.kiwz.ThePlugin.utils.MyWorld;
import net.kiwz.ThePlugin.utils.Place;
import net.kiwz.ThePlugin.utils.Util;
import net.kiwz.ThePlugin.utils.WoolChest;

public class SqlQuery {
	Connection conn;
	
	public SqlQuery(Connection conn) {
		this.conn = conn;
	}
	
	public void loadWorlds() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM worlds;");
			while (res.next()) {
				String name = res.getString("World");
				String environment = res.getString("Environment");
				String type = res.getString("Type");
				Long seed = res.getLong("Seed");
				String coords = res.getString("Coords");
				String direction = res.getString("Direction");
				boolean keepSpawn = res.getBoolean("KeepSpawn");
				boolean pvp = res.getBoolean("PvP");
				boolean monsters = res.getBoolean("Monsters");
				boolean animals = res.getBoolean("Animals");
				boolean monsterGrief = res.getBoolean("MonsterGrief");
				boolean fireSpread = res.getBoolean("FireSpread");
				boolean claimable = res.getBoolean("Claimable");
				boolean explosions = res.getBoolean("Explosions");
				boolean trample = res.getBoolean("Trample");
				int border = res.getInt("Border");
				int fill = res.getInt("Fill");
				
				MyWorld myWorld = new MyWorld(name, environment, type, seed, coords, direction, keepSpawn, pvp,
						monsters, animals, monsterGrief, fireSpread, claimable, explosions, trample, border, fill);
				myWorld.save();
				MultiWorld.loadMyWorld(myWorld);
				MultiWorld.setWorldOptions(myWorld);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateWorld(MyWorld myWorld) {
		if (myWorld.isRemoved()) {
			String query = "DELETE FROM worlds WHERE World='" + myWorld.getName() + "';";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.execute();
				prep.close();
				myWorld.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (myWorld.isChanged()) {
			String query = "INSERT INTO worlds VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Environment=values(Environment), "
					+ "Type=values(Type), "
					+ "Seed=values(Seed), "
					+ "Coords=values(Coords), "
					+ "Direction=values(Direction), "
					+ "KeepSpawn=values(KeepSpawn), "
					+ "PvP=values(PvP), "
					+ "Monsters=values(Monsters), "
					+ "Animals=values(Animals), "
					+ "MonsterGrief=values(MonsterGrief), "
					+ "FireSpread=values(FireSpread), "
					+ "Claimable=values(Claimable), "
					+ "Explosions=values(Explosions), "
					+ "Trample=values(Trample), "
					+ "Border=values(Border), "
					+ "Fill=values(Fill);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, myWorld.getName());
				prep.setString(2, myWorld.getEnv().toString());
				prep.setString(3, myWorld.getType().toString());
				prep.setLong(4, myWorld.getSeed());
				String[] spawn = Util.convertLocation(myWorld.getSpawn());
				prep.setString(5, spawn[1]);
				prep.setString(6, spawn[2]);
				prep.setBoolean(7, myWorld.getKeepSpawn());
				prep.setBoolean(8, myWorld.getPvp());
				prep.setBoolean(9, myWorld.getMonsters());
				prep.setBoolean(10, myWorld.getAnimals());
				prep.setBoolean(11, myWorld.getMonsterGrief());
				prep.setBoolean(12, myWorld.getFireSpread());
				prep.setBoolean(13, myWorld.getClaimable());
				prep.setBoolean(14, myWorld.getExplosions());
				prep.setBoolean(15, myWorld.getTrample());
				prep.setInt(16, myWorld.getBorder());
				prep.setInt(17, myWorld.getFill());
				prep.execute();
				prep.close();
				myWorld.setChanged(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadPlayers() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM players;");
			while (res.next()) {
				String uuid = res.getString("ID");
				String name = res.getString("Player");
				String ip = res.getString("IP");
				long lastPlayed = res.getLong("LastPlayed");
				long timePlayed = res.getLong("TimePlayed");
				boolean muted = res.getBoolean("Mute");
				boolean banned = res.getBoolean("Banned");
				long banTime = res.getLong("BanTime");
				long banExpire = res.getLong("BanExpire");
				String banReason = res.getString("BanReason");
				String bannedBy = res.getString("BannedBy");
				
				MyPlayer myPlayer = new MyPlayer(uuid, name, ip, lastPlayed, timePlayed, muted,
						banned, banTime, banExpire, banReason, bannedBy);
				myPlayer.save();
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlayer(MyPlayer myPlayer) {
		if (myPlayer.isRemoved()) {
			String query = "DELETE FROM players WHERE ID='" + myPlayer.getUUID() + "';";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.execute();
				prep.close();
				myPlayer.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (myPlayer.isChanged()) {
			String query = "INSERT INTO players VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Player=values(Player), "
					+ "IP=values(IP), "
					+ "LastPlayed=values(LastPlayed), "
					+ "TimePlayed=values(TimePlayed), "
					+ "Mute=values(Mute), "
					+ "Banned=values(Banned), "
					+ "BanTime=values(BanTime), "
					+ "BanExpire=values(BanExpire), "
					+ "BanReason=values(BanReason), "
					+ "BannedBy=values(BannedBy);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, myPlayer.getUUID());
				prep.setString(2, myPlayer.getName());
				prep.setString(3, myPlayer.getIp());
				prep.setLong(4, myPlayer.getLastPlayed());
				prep.setLong(5, myPlayer.getTimePlayed());
				prep.setBoolean(6, myPlayer.isMuted());
				prep.setBoolean(7, myPlayer.isBanned());
				prep.setLong(8, myPlayer.getBanTime());
				prep.setLong(9, myPlayer.getBanExpire());
				prep.setString(10, myPlayer.getBanReason());
				prep.setString(11, myPlayer.getBannedBy());
				prep.execute();
				prep.close();
				myPlayer.setChanged(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadPlaces() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM places;");
			while (res.next()) {
				int id = res.getInt("PlaceID");
				int time = res.getInt("Time");
				String name = res.getString("Name");
				String owner = res.getString("Owner");
				String members = res.getString("Members");
				String world = res.getString("World");
				int x = res.getInt("X");
				int z = res.getInt("Z");
				int radius = res.getInt("Radius");
				String spawnCoords = res.getString("SpawnCoords");
				String spawnDirection = res.getString("SpawnDirection");
				boolean priv = res.getBoolean("Priv");
				boolean pvp = res.getBoolean("PvP");
				boolean monsters = res.getBoolean("Monsters");
				boolean animals = res.getBoolean("Animals");
				String enter = res.getString("Enter");
				String leave = res.getString("Leave_");
				
				Place place = new Place(id, time, name, owner, members, world, x, z, radius,
						spawnCoords, spawnDirection, priv, pvp, monsters, animals, enter, leave);
				place.save();
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlace(Place place) {
		if (place.isRemoved()) {
			String query = "DELETE FROM places WHERE PlaceID='" + place.getId() + "';";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.execute();
				prep.close();
				place.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (place.isChanged()) {
			String query = "INSERT INTO places VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Time=values(Time), "
					+ "Name=values(Name), "
					+ "Owner=values(Owner), "
					+ "Members=values(Members), "
					+ "World=values(World), "
					+ "X=values(X), "
					+ "Z=values(Z), "
					+ "Radius=values(Radius), "
					+ "SpawnCoords=values(SpawnCoords), "
					+ "SpawnDirection=values(SpawnDirection), "
					+ "Priv=values(Priv), "
					+ "PvP=values(PvP), "
					+ "Monsters=values(Monsters), "
					+ "Animals=values(Animals), "
					+ "Enter=values(Enter), "
					+ "Leave_=values(Leave_);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setInt(1, place.getId());
				prep.setInt(2, place.getTime());
				prep.setString(3, place.getName());
				prep.setString(4, place.getOwner());
				String members = "";
				for (String member : place.getMembers()) {
					members = members + member + " ";
				}
				prep.setString(5, members.trim());
				prep.setString(6, place.getCenter().getWorld().getName());
				prep.setInt(7, place.getCenter().getBlockX());
				prep.setInt(8, place.getCenter().getBlockZ());
				prep.setInt(9, place.getRadius());
				String[] spawn = Util.convertLocation(place.getSpawn());
				prep.setString(10, spawn[1]);
				prep.setString(11, spawn[2]);
				prep.setBoolean(12, place.getPriv());
				prep.setBoolean(13, place.getPvP());
				prep.setBoolean(14, place.getMonsters());
				prep.setBoolean(15, place.getAnimals());
				prep.setString(16, place.getEnter());
				prep.setString(17, place.getLeave());
				prep.execute();
				prep.close();
				place.setChanged(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadHomes() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM homes;");
			while (res.next()) {
				String uuid = res.getString("Player");
				String worldName = res.getString("World");
				String coords = res.getString("Coords");
				String direction = res.getString("Direction");
				
				Home home = new Home(uuid, worldName, coords, direction);
				home.save();
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateHome(Home home) {
		if (home.isRemoved()) {
			String uuid = home.getUUID();
			String worldName = home.getLocation().getWorld().getName();
			String query = "DELETE FROM homes WHERE Player='" + uuid + "' AND World='" + worldName + "';";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.execute();
				prep.close();
				home.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (home.isChanged()) {
			String query = "INSERT INTO homes VALUES (?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "Coords=values(Coords), "
					+ "Direction=values(Direction);";
			try {
				PreparedStatement prep = conn.prepareStatement(query);
				prep.setString(1, home.getUUID());
				String[] loc = Util.convertLocation(home.getLocation());
				prep.setString(2, loc[0]);
				prep.setString(3, loc[1]);
				prep.setString(4, loc[2]);
				prep.execute();
				prep.close();
				home.setChanged(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectWoolChests() {
		try {
			ResultSet res = conn.createStatement().executeQuery("SELECT * FROM woolchests;");
			while (res.next()) {
				MyPlayer myPlayer = MyPlayer.getPlayerById(res.getString("Owner"));
				short chest = res.getShort("Chest");
				int index = res.getInt("Index_");
				String material = res.getString("Material");
				int amount = res.getInt("Amount");
				short damage = res.getShort("Damage");
				String enchants = res.getString("Enchants");
				
				WoolChest woolChest = WoolChest.getWoolChest(myPlayer, chest);
				if (woolChest == null) {
					woolChest = new WoolChest(myPlayer, chest);
				}
				woolChest.setItem(index, material, amount, damage, enchants);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertWoolChests() {
		for (WoolChest woolChest : WoolChest.getWoolChests()) {
			Inventory inventory = woolChest.getInventory();
			for (int index = 0; inventory.getContents().length > index; index++) {
				ItemStack itemStack = inventory.getItem(index);
				if (itemStack == null) {
					try {
						conn.createStatement().executeUpdate("DELETE FROM woolchests "
								+ "WHERE Owner='" + woolChest.getUUID() + "' "
								+ "AND Chest='" + woolChest.getChest() + "' "
								+ "AND Index_='" + index + "';");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					String query = "INSERT INTO woolchests VALUES (?, ?, ?, ?, ?, ?, ?) "
							+ "ON DUPLICATE KEY UPDATE "
							+ "Material=values(Material), "
							+ "Amount=values(Amount), "
							+ "Damage=values(Damage), "
							+ "Enchants=values(Enchants);";
					try {
						PreparedStatement prep = conn.prepareStatement(query);
						prep.setString(1, woolChest.getUUID());
						prep.setShort(2, woolChest.getChest());
						prep.setInt(3, index);
						prep.setString(4, itemStack.getType().name());
						prep.setInt(5, itemStack.getAmount());
						prep.setShort(6, itemStack.getDurability());
						String enchants = "";
						for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
							enchants += enchantment.getName() + " ";
							enchants += itemStack.getEnchantments().get(enchantment) + " ";
						}
						if (itemStack.getType() == Material.ENCHANTED_BOOK) {
							EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
							for (Enchantment enchantment : meta.getStoredEnchants().keySet()) {
								enchants += enchantment.getName() + " ";
								enchants += meta.getStoredEnchants().get(enchantment) + " ";
							}
						}
						enchants = enchants.trim();
						prep.setString(7, enchants);
						prep.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
