package net.kiwz.ThePlugin.utils;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HandleHomes {
	private ChatColor gold = ChatColor.GOLD;
	private ChatColor red = ChatColor.RED;
	private HashMap<Integer, Homes> homes = ThePlugin.getHomes;
	
	public Location getHome(Player player, String worldName) {
		World world = null;
		Location loc = null;
		
		for (World worlds : Bukkit.getServer().getWorlds()) {
			if (worlds.getName().toUpperCase().contains(worldName.toUpperCase())) {
				world = Bukkit.getServer().getWorld(worlds.getName());
				break;
			}
		}
		
		for (World worlds : Bukkit.getServer().getWorlds()) {
			if (worlds.getName().equalsIgnoreCase(worldName)) {
				world = Bukkit.getServer().getWorld(worlds.getName());
				break;
			}
		}
		
		if (world == null) {
			player.sendMessage(red + "Denne verdenen finnes ikke lengre");
			return loc;
		}
		
		for (int key : homes.keySet()) {
			String homePlayer = homes.get(key).player;
			String homeWorld = homes.get(key).world;
			
			if (homePlayer.equals(player.getName()) && homeWorld.equals(world.getName())) {
				String[] homeCoords = homes.get(key).coords.split(" ");
				String[] homePitch = homes.get(key).pitch.split(" ");
				
				double x = Double.parseDouble(homeCoords[0]);
				double y = Double.parseDouble(homeCoords[1]);
				double z = Double.parseDouble(homeCoords[2]);
				float pitch = Float.parseFloat(homePitch[0]);
				float yaw = Float.parseFloat(homePitch[1]);
				
				loc = new Location(world, x, y, z);
				loc.setPitch(pitch);
				loc.setYaw(yaw);
				return loc;
			}
		}
		if (loc == null) {
			player.sendMessage(red + "Du har ikke satt ett hjem i denne verdenen, bruk /setthjem");
		}
		return loc;
	}
	
	public String setHome(Player player) {
		String playerName = player.getName();
		String worldName = player.getWorld().getName();
		
		Location loc = player.getLocation();
		String coords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
		String pitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
		
		for (int key : homes.keySet()) {
			String homePlayer = homes.get(key).player;
			String homeWorld = homes.get(key).world;
			
			if (homePlayer.equals(playerName) && homeWorld.equals(worldName)) {
				homes.get(key).coords = coords;
				homes.get(key).pitch = pitch;
				return gold + "Du har flyttet ditt hjem hit";
			}
		}
		
		int key = 0;
		while (homes.containsKey(key)) {
			key++;
		}
		
		Homes home = new Homes();
		home.player = playerName;
		home.world = worldName;
		home.coords = coords;
		home.pitch = pitch;
		homes.put(key, home);
		return "Du har satt nytt hjem her";
	}
}
