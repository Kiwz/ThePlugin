package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand {
	
	public boolean help(CommandSender sender, Command cmd, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ThePlugin.c2 + "/hjelp er bare for spillere, bruk /help");
			return true;
		}
		else {
			Player player = Bukkit.getServer().getPlayer(sender.getName());
			if (args.length == 0) player.performCommand("help");
			if (args.length == 1) player.performCommand("help " + args[0]);
			if (args.length == 2) player.performCommand("help " + args[0] + " " + args[1]);
			if (args.length == 3) player.performCommand("help " + args[0] + " " + args[1] + " " + args[2]);
			if (args.length == 4) player.performCommand("help " + args[0] + " " + args[1] + " " + args[2] + " " + args[3]);
			return true;
		}
	}
}
