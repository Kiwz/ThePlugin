package net.kiwz.ThePlugin.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.kiwz.ThePlugin.mysql.MySQLQuery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {
	
	public boolean spawn(CommandSender sender, Command cmd, String[] args) {
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		Player playerA = server.getPlayer(sender.getName());
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/spawn kan bare brukes av spillere");
			return true;
		}
		else {
			playerA.teleport(getSpawn(playerA.getLocation().getWorld()));
		}
		return true;
	}
	
	public boolean setSpawn(CommandSender sender, Command cmd, String[] args) {
		ChatColor gold = ChatColor.GOLD;
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		Player playerA = server.getPlayer(sender.getName());
		World world = playerA.getWorld();
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/setspawn kan bare brukes av spillere");
			return true;
		}
		else {
			world.setSpawnLocation(playerA.getLocation().getBlockX(),
					playerA.getLocation().getBlockY(), playerA.getLocation().getBlockZ());
			String worldName = world.getName();
			String coords = Double.toString(playerA.getLocation().getX()) + " "
					+ Double.toString(playerA.getLocation().getY()) + " "
					+ Double.toString(playerA.getLocation().getZ());
			String pitch = Float.toString(playerA.getLocation().getPitch()) + " "
					+ Float.toString(playerA.getLocation().getYaw());
			MySQLQuery query = new MySQLQuery();
			try {
				ResultSet res = query.query("SELECT * FROM worlds WHERE World LIKE '" + worldName + "';");
				if (res.next()) {
					query.update("UPDATE worlds SET Coords='" + coords + "', Pitch='" + pitch + "' WHERE World LIKE '" + worldName + "';");
				}
				else {
					query.update("INSERT INTO worlds (World, Coords, Pitch) "
							+ "VALUES ('" + worldName + "', '" + coords + "', '" + pitch + "');");
				}
				sender.sendMessage(gold + "Du har satt spawnen her");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public Location getSpawn(World world) {
		Location loc = null;
		MySQLQuery query = new MySQLQuery();
		try {
			ResultSet res = query.query("SELECT * FROM worlds WHERE World LIKE '" + world.getName() + "';");
			if (res.next()) {
				String[] spawnCoords = res.getString("Coords").split(" ");
				String[] spawnPitch = res.getString("Pitch").split(" ");
				double x = Double.parseDouble(spawnCoords[0]);
				double y = Double.parseDouble(spawnCoords[1]);
				double z = Double.parseDouble(spawnCoords[2]);
				float pitch = Float.parseFloat(spawnPitch[0]);
				float yaw = Float.parseFloat(spawnPitch[1]);
				loc = new Location(world, x, y, z);
				loc.setPitch(pitch);
				loc.setYaw(yaw);
			}
			else {
				loc = world.getSpawnLocation();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loc;
	}
}
