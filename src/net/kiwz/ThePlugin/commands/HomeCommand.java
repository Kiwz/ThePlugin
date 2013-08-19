package net.kiwz.ThePlugin.commands;

import java.util.HashMap;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.mysql.Homes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand {
	ChatColor gold = ChatColor.GOLD;
	ChatColor red = ChatColor.RED;
	
	public boolean home(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		World world = player.getWorld();
		
		if (sender instanceof Player) {
			HashMap<String, Homes> homes = ThePlugin.getHomes;
			
			for (String key : homes.keySet()) {
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
					
					Location loc = new Location(world, x, y, z);
					loc.setPitch(pitch);
					loc.setYaw(yaw);
					
					player.teleport(loc);
					sender.sendMessage(gold + "Velkommen til ditt hjem");
					return true;
				}
			}
			sender.sendMessage(red + "Du har ikke satt ett hjem i denne verdenen, bruk /setthjem");
			return true;
		}
		else {
			sender.sendMessage(red + "/home kan bare brukes av spillere");
			return true;
		}
	}
	
	public boolean homeSet(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		String playerName = player.getName();
		World world = player.getWorld();
		String worldName = world.getName();
		
		Location loc = player.getLocation();
		String coords = Double.toString(loc.getX()) + " " + Double.toString(loc.getY()) + " " + Double.toString(loc.getZ());
		String pitch = Float.toString(loc.getPitch()) + " " + Float.toString(loc.getYaw());
		
		if (sender instanceof Player) {
			HashMap<String, Homes> homes = ThePlugin.getHomes;
			
			for (String key : homes.keySet()) {
				String homePlayer = homes.get(key).player;
				String homeWorld = homes.get(key).world;
				
				if (homePlayer.equals(playerName) && homeWorld.equals(worldName)) {
					homes.get(key).coords = coords;
					homes.get(key).pitch = pitch;
					sender.sendMessage(gold + "Du har flyttet ditt hjem hit");
					return true;
				}
			}
			Homes home = new Homes();
			home.player = playerName;
			home.world = worldName;
			home.coords = coords;
			home.pitch = pitch;
			homes.put(playerName, home);
			sender.sendMessage(gold + "Du har satt ditt hjem her");
			return true;
		}
		else {
			sender.sendMessage(red + "/sethome kan bare brukes av spillere");
			return true;
		}
	}
}
