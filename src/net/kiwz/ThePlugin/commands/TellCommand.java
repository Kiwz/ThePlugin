package net.kiwz.ThePlugin.commands;

import net.kiwz.ThePlugin.ThePlugin;
import net.kiwz.ThePlugin.utils.OnlinePlayer;

import org.bukkit.command.CommandSender;

public class TellCommand {
	
	public void tell(CommandSender sender, String[] args) {
		OnlinePlayer player = new OnlinePlayer();

		if (args.length == 0) {
			sender.sendMessage(ThePlugin.c2 + "Du må skrive navnet på en spiller");
		}
		
		else if (args.length == 1) {
			if (player.getPlayer(args[0]) != null) {
				sender.sendMessage(ThePlugin.c2 + "Du må skrive en melding til " + args[0]);
			}
			else {
				sender.sendMessage(ThePlugin.c2 + args[0] + " er ikke online");
			}
		}
		
		else {
			String destination = args[0];
			String message = "";
			args[0] = "";
			if (player.getPlayer(destination) != null) {
				for (String s : args) {
					message = message + s + " ";
				}
				message = ThePlugin.c5 + sender.getName() + " -> " + player.getPlayer(destination).getName() + ": " + message;
				sender.sendMessage(message);
				player.getPlayer(destination).sendMessage(message);
			}
			else {
				sender.sendMessage(ThePlugin.c2 + destination + " er ikke online");
			}
		}
	}
}
