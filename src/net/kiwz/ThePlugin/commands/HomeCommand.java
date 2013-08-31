package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.HandleHomes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand {
	HandleHomes homes = new HandleHomes();
	
	public boolean home(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		Location loc;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/home kan bare brukes av spillere");
			return true;
		}
		else if (args.length > 0) {
			loc = homes.getHome(player, args[0]);
		}
		else {
			loc = homes.getHome(player, player.getWorld().getName());
		}
		if (loc != null) {
			player.teleport(loc);
			return true;
		}
		else {
			return true;
		}
	}
	
	public boolean setHome(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/sethome kan bare brukes av spillere");
			return true;
		}
		else {
			sender.sendMessage(homes.setHome(player));
			return true;
		}
	}
}
