package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandleWorlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {
	private HandleWorlds worlds = new HandleWorlds();
	
	public boolean spawn(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Location loc;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/spawn kan bare brukes av spillere");
			return true;
		}
		else if (args.length > 0) {
			loc = worlds.getSpawn(player, args[0]);
		}
		else {
			loc = worlds.getSpawn(player, player.getWorld().getName());
		}
		if (loc != null) {
			player.teleport(loc);
			return true;
		}
		else {
			return true;
		}
	}
	
	public boolean setSpawn(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/setspawn kan bare brukes av spillere");
			return true;
		}
		else {
			sender.sendMessage(worlds.setSpawn(player.getLocation()));
			return true;
		}
	}
}
