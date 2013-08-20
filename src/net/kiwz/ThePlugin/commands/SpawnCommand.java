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
		
		if (sender instanceof Player) {
			player.teleport(hWorlds.getSpawn(player.getWorld().getName()));
			return true;
		}
		else {
			sender.sendMessage(red + "/spawn kan bare brukes av spillere");
			return true;
		}
	}
	
	public boolean spawnSet(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Location loc = player.getLocation();
		
		if (sender instanceof Player) {
			hWorlds.setSpawn(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
			sender.sendMessage(gold + "Du har satt spawnen her");
			return true;
		}
		else {
			sender.sendMessage(red + "/setspawn kan bare brukes av spillere");
			return true;
		}
	}
}
