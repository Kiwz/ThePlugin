package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {
	private ChatColor gold = ChatColor.GOLD;
	private ChatColor red = ChatColor.RED;
	private HandleWorlds hWorlds = new HandleWorlds();
	
	public boolean spawn(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/spawn kan bare brukes av spillere");
			return true;
		}
		else {
			Location loc;
			if (args.length > 0) {
				loc = hWorlds.getSpawn(player, args[0]);
			}
			else {
				String worldName = player.getWorld().getName();
				loc = hWorlds.getSpawn(player, worldName);
			}
			if (loc != null) {
				player.teleport(loc);
				return true;
			}
			return true;
		}
	}
	
	public boolean spawnSet(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(red + "/setspawn kan bare brukes av spillere");
			return true;
		}
		else {
			hWorlds.setSpawn(player.getLocation());
			sender.sendMessage(gold + "Du har satt spawnen her");
			return true;
		}
	}
}
