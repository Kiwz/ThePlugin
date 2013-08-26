package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.HandleHomes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand {
	ChatColor gold = ChatColor.GOLD;
	ChatColor red = ChatColor.RED;
	HandleHomes hHomes = new HandleHomes();
	
	public boolean home(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());

		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/home kan bare brukes av spillere");
			return true;
		}
		else {
			Location loc;
			if (args.length > 0) {
				loc = hHomes.getHome(player, args[0]);
			}
			else {
				String worldName = player.getWorld().getName();
				loc = hHomes.getHome(player, worldName);
			}
			if (loc != null) {
				player.teleport(loc);
				return true;
			}
			return true;
		}
	}
	
	public boolean homeSet(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/sethome kan bare brukes av spillere");
			return true;
		}
		else {
			sender.sendMessage(hHomes.setHome(player));
			return true;
		}
	}
}
