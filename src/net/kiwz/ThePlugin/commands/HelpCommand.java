package net.kiwz.ThePlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand {
	
	public void help(CommandSender sender, Command cmd, String[] args) {
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		if (args.length == 0) player.performCommand("help");
		if (args.length == 1) player.performCommand("help " + args[0]);
		if (args.length == 2) player.performCommand("help " + args[0] + " " + args[1]);
		if (args.length == 3) player.performCommand("help " + args[0] + " " + args[1] + " " + args[2]);
		if (args.length == 4) player.performCommand("help " + args[0] + " " + args[1] + " " + args[2] + " " + args[3]);
	}
}
