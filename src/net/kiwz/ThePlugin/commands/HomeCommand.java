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

public class HomeCommand {
	
	public boolean home(CommandSender sender, Command cmd, String[] args) {
		ChatColor gold = ChatColor.GOLD;
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		Player playerA = server.getPlayer(sender.getName());
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/home kan bare brukes av spillere");
			return true;
		}
		else {
			MySQLQuery query = new MySQLQuery();
			try {
				ResultSet res = query.query("SELECT * FROM homes, players "
						+ "WHERE homes.PlayerID LIKE players.PlayerID "
						+ "AND players.Player LIKE '" + sender.getName() + "' "
						+ "AND homes.World LIKE '" + playerA.getWorld().getName() + "';");
				if (!res.next()) {
					sender.sendMessage(red + "Du har ikke satt ett hjem i denne verdenen, bruk /setthjem");
				}
				else {
					String homeWorld = res.getString("World");
					String[] homeCoords = res.getString("Coords").split(" ");
					String[] homePitch = res.getString("Pitch").split(" ");
					World world = server.getWorld(homeWorld);
					double x = Double.parseDouble(homeCoords[0]);
					double y = Double.parseDouble(homeCoords[1]);
					double z = Double.parseDouble(homeCoords[2]);
					float pitch = Float.parseFloat(homePitch[0]);
					float yaw = Float.parseFloat(homePitch[1]);
					Location loc = new Location(world, x, y, z);
					loc.setPitch(pitch);
					loc.setYaw(yaw);
					playerA.teleport(loc);
					sender.sendMessage(gold + "Velkommen til ditt hjem");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean homeSet(CommandSender sender, Command cmd, String[] args) {
		ChatColor gold = ChatColor.GOLD;
		ChatColor red = ChatColor.RED;
		Server server = Bukkit.getServer();
		Player playerA = server.getPlayer(sender.getName());
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/sethome kan bare brukes av spillere");
			return true;
		}
		else {
			String world = playerA.getWorld().getName();
			String coords = Double.toString(playerA.getLocation().getX()) + " "
					+ Double.toString(playerA.getLocation().getY()) + " "
					+ Double.toString(playerA.getLocation().getZ());
			String pitch = Float.toString(playerA.getLocation().getPitch()) + " "
					+ Float.toString(playerA.getLocation().getYaw());
			MySQLQuery query = new MySQLQuery();
			try {
				ResultSet res = query.query("SELECT * FROM players WHERE Player LIKE '" + sender.getName() + "';");
				res.next();
				String playerID = res.getString("PlayerID");
				res = query.query("SELECT * FROM homes WHERE PlayerID LIKE '" + playerID + "' AND World LIKE '" + world + "';");
				if (res.next()) {
					query.update("UPDATE homes SET Coords='" + coords + "', Pitch='" + pitch + "' WHERE PlayerID LIKE '" + playerID + "' AND World LIKE '" + world + "';");
				}
				else {
					query.update("INSERT INTO homes (PlayerID, World, Coords, Pitch) "
							+ "VALUES ('" + playerID + "', '" + world + "', '" + coords + "', '" + pitch + "');");
				}
				sender.sendMessage(gold + "Du har satt hjemmet ditt her");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
