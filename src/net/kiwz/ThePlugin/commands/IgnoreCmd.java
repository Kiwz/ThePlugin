package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.utils.ChatIgnore;
import net.kiwz.ThePlugin.utils.Color;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCmd {
	private Server server = Bukkit.getServer();
	
	public static boolean exec(CommandSender sender, String[] args) {
		return new IgnoreCmd().ignore(sender, args);
	}
	
	public boolean ignore(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = server.getPlayer(sender.getName());
		}
		
		if (player == null) {
			sender.sendMessage(Color.WARNING + "Denne kommandoen er bare for spillere");
		} else if (args.length == 0) {
			if (ChatIgnore.getPlayers(player) != null) {
				sender.sendMessage(Color.INFO + "Du har ignorert følgende spiller(e):");
				for (String s : ChatIgnore.getPlayers(player)) {
					sender.sendMessage(s);
				}
			} else {
				sender.sendMessage(Color.WARNING + "Du har ikke ignorert noen spillere");
			}
		} else if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
			sender.sendMessage(Color.WARNING + "Du kan ikke ignorere deg selv");
		} else {
			if (!ChatIgnore.remPlayer(player, args[0])) {
				ChatIgnore.addPlayer(player, args[0]);
			}
		}
		return true;
	}
}
